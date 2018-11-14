package com.somesame.framework.widget.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.somesame.framework.widget.refresh.footer.LoadFooter;
import com.somesame.framework.widget.refresh.header.SinaRefreshHeader;
import com.somesame.framework.widget.refresh.loadmore.LoadMoreController;
import com.somesame.framework.widget.refresh.loadmore.LoadMoreOtherListener;
import com.somesame.framework.widget.refresh.utils.ScrollingUtil;
import com.somesame.framework.widget.refresh.utils.SimpleAnimatorListener;


/**
 * Created by fuzhipeng on 2017/1/9.
 */

//自动刷新；-->  直接到 头部位置 并且是 刷新状态
//demo listView，GridView recycler imageView scrollerView,WebView
//先弄上啦加载 最后是否固定头部 已经固定底部   下拉头部高度   仅头部使用
//阻力下拉
//全局切换  头与脚,全局配置初始化
//自定义头部 与 底部；  新浪 google支持, wave映射图
//ReadMe,wiki  名字改动
public class ZRefreshLayout extends FrameLayout {

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    static Config config;

    static final int REST = 0;//等待状态-休息；
    static final int PULL = 1;//下拉
    static final int LOAD = 10;//上拉
    static final int REFRESH_ABLE = 2;//松开刷新 条件--->滑动超过头部高度
    static final int REFRESH_ABLE_FOOTER = 12;//松开刷新 条件--->滑动超过底部控件高度
    static final int REFRESHING = 3;//刷新中
    static final int COMPLETE = 4;//松开刷新
    static final int LOADMORE_ING = 5;//加载中
    static final int AUTO_PULL = 6;//自动刷新

    int state = REST;

    private int mTouchSlop;//滑动有效的最小距离
    private View headerView, footerView, content;
    private IHeaderView mIHeaderView;
    private IFooterView mIFooterView;
    private boolean isPinHeader;//头部是否固定
    private boolean isPinFooter;//底部是否固定
    private boolean isSpringback;//回弹效果，在没有上拉与下拉时是否需要这个效果

    private IScroll mIScroll, mIFooterScroll;//滚动策略
    private PullListener mPullListener;
    private RefreshAbleListener mRefreshAbleListener;
    private LoadMoreListener mLoadMoreListener;
    private IResistance mIResistance;//阻力策略
    AnimateBack mAnimateBack = AnimateBack.None;
    private boolean isCanLoadMore = true;
    private boolean isCanRefresh = true;
    //ms 设置最长时间 自动恢复; 默认时间config里的配置  -1关闭延迟功能
    private int delay_millis_auto_complete;

    public ZRefreshLayout(Context context) {
        this(context, null);
    }

    public ZRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if (config != null) {
            isSpringback = config.isSpringback;
        }
        initDelayAutoComplete();
        initPinContent();
        initPinFooterContent();
        initHeaderView();
        initFooterView();
        initResistance();
        initListener();
    }

    private void initDelayAutoComplete() {
        if (config != null)
            delay_millis_auto_complete = config.delay_millis_auto_complete;
    }

    private void initListener() {
        if (config != null && config.mPullListener != null)
            mPullListener = config.mPullListener;
        if (config != null && config.mLoadMoreListener != null)
            mLoadMoreListener = config.mLoadMoreListener;
    }

    private void initResistance() {
        if (config != null && config.resistance != null)
            mIResistance = config.resistance.clone_();
    }

    private void initPinContent() {
        if (config != null && config.isPinHeader) {
            this.isPinHeader = config.isPinHeader;
            mIScroll = new Scroll_Pin();
        } else
            mIScroll = new Scroll_PinNot();
    }

    private void initPinFooterContent() {
        if (config != null && config.isPinFooter) {
            this.isPinFooter = config.isPinFooter;
            mIFooterScroll = new ScrollFooter_Pin();
        } else
            mIFooterScroll = new ScrollFooter_PinNot();
    }

    private void initHeaderView() {
        if (mIHeaderView != null)//个人配置
            headerView = mIHeaderView.getView(this);
        else {
            if (config != null && config.headerView != null) {
                //全局配置
                mIHeaderView = config.headerView.clone_();
                if (mIHeaderView != null)
                    headerView = mIHeaderView.getView(this);
            }
            //全局配置clone为空的时候
            if (mIHeaderView == null) {
                //都为空 默认新浪
                mIHeaderView = new SinaRefreshHeader();
                headerView = mIHeaderView.getView(this);
            }
        }
        if (isSpringback && null == mPullListener && null != headerView) {
            headerView.setVisibility(View.INVISIBLE);
        }
    }

    private void initFooterView() {
        if (mIFooterView != null)//个人配置
            footerView = mIFooterView.getView(this);
        else {
            if (config != null && config.footerView != null) {
                //全局配置
                mIFooterView = config.footerView.clone_();
                if (mIFooterView != null)
                    footerView = mIFooterView.getView(this);
            }
            //全局配置clone为空的时候
            if (footerView == null) {
                //都为空 默认新浪
                mIFooterView = new LoadFooter();
                footerView = mIFooterView.getView(this);
            }
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addView(headerView);
        addView(footerView);
        content = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LayoutParams headerViewLp = (LayoutParams) headerView.getLayoutParams();
        //headerView 仅仅支持 bottomMargin
        headerView.layout((getWidth() - headerView.getWidth()) / 2, -headerView.getHeight() - headerViewLp.bottomMargin, (getWidth() + headerView.getWidth()) / 2, -headerViewLp.bottomMargin);
        if (mLoadMoreListener != null) {
            footerView.layout(0, getHeight() - footerView.getHeight(), getWidth(), getHeight());
        }
    }

    private float mTouchX, mTouchY;

    //防止down事件被吃 就是刷新完事 手指不离开继续下滑的问题;

    boolean haveDownEvent = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (state == REST) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchX = event.getX();
                    mTouchY = event.getY();
                    LogUtils.d("down!事件");
                    haveDownEvent = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!haveDownEvent)
                        return super.onInterceptTouchEvent(event);
                    float dx = event.getX() - mTouchX;
                    float dy = event.getY() - mTouchY;
                    LogUtils.d("mTouchY :" + mTouchY + "___  event.getY():" + event.getY());
                    LogUtils.d("dy :" + dy + "___ mTouchSlop:" + mTouchSlop);
//                    LogUtils.d(String.format("下拉截断判断:%b\tdy>mTouchSlop:%b \tMath.abs(dx) <= Math.abs(dy):%b\t !ScrollingUtil.canChildScrollUp(content):%b",
//                            dy > mTouchSlop && Math.abs(dx) <= Math.abs(dy)&& !ScrollingUtil.canChildScrollUp(content)
//                            ,dy>mTouchSlop,Math.abs(dx) <= Math.abs(dy),!ScrollingUtil.canChildScrollUp(content)));
                    if (isCanRefresh && dy > mTouchSlop && Math.abs(dx) <= Math.abs(dy)
                            && !ScrollingUtil.canChildScrollUp(content)) {
                        //滑动允许最大角度为45度
                        LogUtils.d("==========下拉截断！===========");
                        return true;
                    }
//                    LogUtils.d(String.format("上拉截断判断:%b\t!isInterceptInnerLoadMore:%b\t" +
//                            "isCanLoadMore:%b\tmLoadMoreListener!=null:%b\tdy < 0:%b \tMath.abs(dy) > mTouchSlop:%b\t Math.abs(dx) <= Math.abs(dy):%b\t !ScrollingUtil.canChildScrollUp(content):%b",
//                            !isInterceptInnerLoadMore&&isCanLoadMore&&mLoadMoreListener != null && dy < 0
//                                    && Math.abs(dy) > mTouchSlop
//                                    && Math.abs(dx) <= Math.abs(dy)
//                                    && !ScrollingUtil.canChildScrollDown(content),
//                            !isInterceptInnerLoadMore,isCanLoadMore&&mLoadMoreListener != null,
//                            mLoadMoreListener != null,dy < 0,Math.abs(dy) > mTouchSlop,
//                            Math.abs(dx) <= Math.abs(dy),!ScrollingUtil.canChildScrollUp(content)));
                    if (!isInterceptInnerLoadMore && isCanLoadMore && mLoadMoreListener != null && dy < 0
                            && Math.abs(dy) > mTouchSlop
                            && Math.abs(dx) <= Math.abs(dy)
                            && !ScrollingUtil.canChildScrollDown(content)) {
                        LogUtils.d("===========上啦截断！===========");
                        return true;
                    }
                    if (null == mPullListener && null == mLoadMoreListener && isSpringback) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    private int direction = 0;
    private final static int LOAD_UP = 1;
    private final static int PULL_DOWN = 2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {//被拦截状态下 不是刷新就是 加载更多

        if (state == AUTO_PULL) return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = event.getY() - mTouchY;
                if (dy > 0)
                    direction = PULL_DOWN;
                else
                    direction = LOAD_UP;

                if (mIResistance != null)//dy 需要映射不然 真实的移动 和 MotionEvent移动对不上了 也没法和getRefreshAbleHeight()[真实的]比较了
                    dy = mIResistance.getOffSetYMapValue(getRefreshAbleHeight(), (int) dy);

                if (isCanRefresh && state == REST && direction == PULL_DOWN) {
                    state = PULL;
                    LogUtils.d("PULL！");
                }
                if (isCanLoadMore && state == REST && direction == LOAD_UP) {
                    state = LOAD;
                }

                if (!isInterceptInnerLoadMore && haveDownEvent && isCanLoadMore && mLoadMoreListener != null && Math.abs(dy) > mTouchSlop && state == REST && direction == LOAD_UP && mIScroll.getScrollY() == 0) {
                    LogUtils.d("dy:" + dy + "_ mTouchY:" + mTouchY + "__event.getY():" + event.getY());
//                    loadMore();
                }

                if (direction == LOAD_UP && state == LOAD && Math.abs(dy) >= getRefreshFooterAbleHeight()) {
                    state = REFRESH_ABLE_FOOTER;
                    LogUtils.d("Math.abs(dy):" + Math.abs(dy) + "___getRefreshFooterAbleHeight():" + getRefreshFooterAbleHeight());
                    LogUtils.d("REFRESH_ABLE_FOOTER！");
                }

                if (state == REFRESH_ABLE_FOOTER && Math.abs(dy) < getRefreshFooterAbleHeight()) {
                    state = LOAD;
                    LogUtils.d("Math.abs(dy):" + Math.abs(dy) + "___getRefreshFooterAbleHeight():" + getRefreshFooterAbleHeight());
                    LogUtils.d("LOAD！");
                }

                if (null != mPullListener && direction == PULL_DOWN && state == PULL && Math.abs(dy) >= getRefreshAbleHeight()) {
                    state = REFRESH_ABLE;
                    LogUtils.d("Math.abs(dy):" + Math.abs(dy) + "___getRefreshAbleHeight():" + getRefreshAbleHeight());
                    LogUtils.d("REFRESH_ABLE！");
                    mIHeaderView.refreshAble(true);
                    if (mRefreshAbleListener != null)
                        mRefreshAbleListener.refreshAble(true);
                }
                if (null != mPullListener && state == REFRESH_ABLE && Math.abs(dy) < getRefreshAbleHeight()) {
                    state = PULL;
                    LogUtils.d("Math.abs(dy):" + Math.abs(dy) + "___getRefreshAbleHeight():" + getRefreshAbleHeight());
                    LogUtils.d("PULL！");
                    mIHeaderView.refreshAble(false);
                    if (mRefreshAbleListener != null)
                        mRefreshAbleListener.refreshAble(false);
                }

                if (state == PULL || state == REFRESH_ABLE) {
                    if ((null == mPullListener && isSpringback) || null != mPullListener) {
                        //防止别的状态 可以滚动
                        mIScroll.scrollTo_((int) (event.getY() - mTouchY));
                    }
                }
                if (state == LOAD || state == REFRESH_ABLE_FOOTER) {
                    LogUtils.d("LOAD！" + (int) (event.getY() - mTouchY));
                    LogUtils.d("LOAD！" + (int) (event.getY()));
                    LogUtils.d("LOAD！" + (int) mTouchY);
                    if ((null == mLoadMoreListener && isSpringback) || null != mLoadMoreListener) {
                        mIFooterScroll.scrollTo_((int) (mTouchY - event.getY()));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (state == PULL) {
                    //回弹
                    mAnimateBack = AnimateBack.DisRefreshAble_Back;
                    mIScroll.smoothScrollTo_(0);
                    LogUtils.d("回弹！");
                }
                if (state == REFRESH_ABLE) {
                    if (null == mPullListener && isSpringback) {
                        //回弹
                        state = PULL;
                        mAnimateBack = AnimateBack.DisRefreshAble_Back;
                        mIScroll.smoothScrollTo_(0);
                    } else {
                        if (null != mPullListener) {
                            //刷新
                            mAnimateBack = AnimateBack.RefreshAble_Back;
                            mIScroll.smoothScrollTo_(getRefreshAbleHeight());
                        }
                    }

                }

                if (state == LOAD) {
                    //回弹
                    mAnimateBack = AnimateBack.DisRefreshAble_Back;
                    mIFooterScroll.smoothScrollTo_(0);
                    LogUtils.d("回弹！");
                }
                if (state == REFRESH_ABLE_FOOTER) {
                    if (null == mLoadMoreListener && isSpringback) {
                        //回弹
                        state = LOAD;
                        mAnimateBack = AnimateBack.DisRefreshAble_Back;
                        mIFooterScroll.smoothScrollTo_(0);
                    } else {
                        if (null != mLoadMoreListener) {
                            //刷新
                            mAnimateBack = AnimateBack.RefreshAble_Back;
                            mIFooterScroll.smoothScrollTo_(getRefreshFooterAbleHeight());
                        }
                    }
                }

                break;
            default:
                break;
        }
        return true;
    }

    void loadMore() {
        state = LOADMORE_ING;
        if (!isDelegate && mIFooterView != null) {
            mIFooterView.onStart(this, footerView.getHeight(), isPinFooter);
        }
    }

    void notityLoadMoreListener() {
        state = LOADMORE_ING;
        LogUtils.d("LOADMORE_ING！");
        delayAutoComplete();
        if (null != mLoadMoreListener) {
            mLoadMoreListener.loadMore(this);
        } else {
            loadMoreComplete();
        }
    }


    private void notityRefresh() {
        if (state == PULL || state == REFRESH_ABLE) {
            state = REFRESHING;
            LogUtils.d("释放回弹 将要刷新");
            //等待回调  刷新完成
            if (mPullListener != null)
                mPullListener.refresh(this);
            if (mIHeaderView != null)
                mIHeaderView.onRefreshing(getRefreshAbleHeight(), false);
        }
        if (state == LOAD || state == REFRESH_ABLE_FOOTER) {
            loadMore();
        }
    }

    Runnable runableAutoComplete = new Runnable() {
        @Override
        public void run() {
            if (ZRefreshLayout.this != null && ZRefreshLayout.this.isRefreshOrLoadMore()) {
                LogUtils.d("延迟结束刷新/加载!");
                ZRefreshLayout.this.refresh2LoadMoreComplete();
            }
        }
    };

    public void setDelayAutoCompleteTime(int delayAutoComplete) {
        this.delay_millis_auto_complete = delayAutoComplete;
    }

    public int getDelayAutoCompleteTime() {
        return delay_millis_auto_complete;
    }

    private void delayAutoComplete() {
        if (delay_millis_auto_complete != -1) {
            LogUtils.d("add延迟完成!");
            mHandler.postDelayed(runableAutoComplete, delay_millis_auto_complete);

        }
    }

    private void removeDelayAutoComplete() {
        if (delay_millis_auto_complete != -1) {
            LogUtils.d("remove延迟完成!");
            mHandler.removeCallbacks(runableAutoComplete);
        }
    }

    int heightToRefresh;

    //因为头部设置更有效 所以就暴露了  通过AUtils类暴露接口
    void setHeaderHeightToRefresh(int heightToRefresh) {
        this.heightToRefresh = heightToRefresh;
    }

    private int getRefreshAbleHeight() {
        return heightToRefresh != 0 ? heightToRefresh : headerView.getHeight();
    }

    private int getRefreshFooterAbleHeight() {
        return Math.abs(footerView.getHeight());
    }

    public void autoRefresh(boolean haveAnimate) {
        if (state == REST) {
            //等待回调  刷新完成
            state = AUTO_PULL;
            LogUtils.d("AUTO_PULL！");
            delayAutoComplete();
//            if (mPullListener != null)
//                mPullListener.refresh(this);
            if (mIHeaderView != null) {
                mIHeaderView.onRefreshing(getRefreshAbleHeight(), true);
            }

            if (!haveAnimate)
                mIScroll.scrollTo_(getRefreshAbleHeight(), true);
            else {
                mAnimateBack = AnimateBack.Auto_Refresh;
                mIScroll.smoothScrollTo_(getRefreshAbleHeight());
            }
        }
    }

    public void refreshComplete() {
        if (!isRefresh()) {
            LogUtils.d("非刷新状态下 refreshComplete");
            return;
        }
        removeDelayAutoComplete();
        mAnimateBack = AnimateBack.Complete_Back;
        mIScroll.smoothScrollTo_(0);
    }

    public void loadMoreComplete() {
        if (!isLoadMore()) {
            LogUtils.d("非加载状态下 loadMoreComplete");
            return;
        }
        removeDelayAutoComplete();
        mAnimateBack = AnimateBack.Complete_Back;
        mIFooterScroll.smoothScrollTo_(0);
        if (!isDelegate && mIFooterView != null)
            mIFooterView.onComplete(this);
    }

    void notifyLoadMoreCompleteListener() {
        if (null != mLoadMoreListener) {
            mLoadMoreListener.loadMoreAnimationComplete(this);
        }
        state = REST;
        haveDownEvent = false;
    }

    public LoadMoreListener getLoadMoreListener() {
        return mLoadMoreListener;
    }

    boolean isInterceptInnerLoadMore;
    boolean isDelegate;
    LoadMoreOtherListener outterLoadMoreListener;

    public void setLoadMoreListener(LoadMoreListener mLoadMoreListener) {
        setLoadMoreListener(false, mLoadMoreListener);
    }

    /**
     * @param isDelegate        委托加载更多,在外边处理
     *                          外部监听请调用此方法{@link AUtils#notityLoadMoreListener(ZRefreshLayout)}
     * @param mLoadMoreListener
     */
    public void setLoadMoreListener(boolean isDelegate, LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
        this.isDelegate = isDelegate;
        if (!isDelegate) {
            outterLoadMoreListener = LoadMoreController.addLoadMoreListener(content, this);
            isInterceptInnerLoadMore = outterLoadMoreListener == null ? false : false;
//            isInterceptInnerLoadMore = true;
        } else
            isInterceptInnerLoadMore = true;
    }

    public boolean isCanLoadMore() {
        return isCanLoadMore && mLoadMoreListener != null;
    }

    public boolean isLoadMore() {
        return state == LOADMORE_ING;
    }

    public boolean isRefreshOrLoadMore() {
        return isLoadMore() || isRefresh();
    }

    public void refresh2LoadMoreComplete() {
        if (isRefresh())
            refreshComplete();
        if (isLoadMore())
            loadMoreComplete();
    }

    public boolean isRefresh() {
        return state == REFRESHING || state == AUTO_PULL;
    }

    public boolean isAutoRefresh() {
        return state == AUTO_PULL;
    }

    //必须设置加载更多监听才能 加载更多
    public void setCanLoadMore(boolean canLoadMore) {
        isCanLoadMore = canLoadMore;
        if (outterLoadMoreListener == null)
            return;
        if (canLoadMore) {
            if (!outterLoadMoreListener.haveListener())
                outterLoadMoreListener.addListener(content, this);
        } else
            outterLoadMoreListener.removeListener(content);
    }

    public boolean isCanRefresh() {
        return isCanRefresh;
    }

    public void setCanRefresh(boolean canRefresh) {
        isCanRefresh = canRefresh;
    }

    public PullListener getPullListener() {
        return mPullListener;
    }

    public void setPullListener(PullListener mPullListener) {
        this.mPullListener = mPullListener;
        if (null != headerView && null != mPullListener) {
            if (!headerView.isShown()) {
                headerView.setVisibility(View.VISIBLE);
            }
        }
    }

    public IHeaderView getIHeaderView() {
        return mIHeaderView;
    }

    public void setIHeaderView(@NonNull IHeaderView mIHeaderView) {
        this.mIHeaderView = mIHeaderView;
        removeView(headerView);
        heightToRefresh = 0;//还原刷新高度
        headerView = mIHeaderView.getView(this);
        addView(headerView);
        if (null == mPullListener) {
            headerView.setVisibility(View.INVISIBLE);
        }
    }

    public IFooterView getIFooterView() {
        return mIFooterView;
    }

    public void setIFooterView(@NonNull IFooterView mIFooterView) {
        this.mIFooterView = mIFooterView;
        removeView(footerView);
        footerView = mIFooterView.getView(this);
        addView(footerView);
    }

    public void setPinHeader(boolean isPinHeader) {
        this.isPinHeader = isPinHeader;
        if (state == REST)
            if (this.isPinHeader)
                mIScroll = new Scroll_Pin();
            else
                mIScroll = new Scroll_PinNot();
    }

    public void setPinFooter(boolean isPinFooter) {
        this.isPinFooter = isPinFooter;
        if (state == REST)
            if (this.isPinFooter)
                mIFooterScroll = new ScrollFooter_Pin();
            else
                mIFooterScroll = new ScrollFooter_PinNot();
    }

    public boolean isPinHeader() {
        return isPinHeader;
    }

    public IResistance getIResistance() {
        return mIResistance;
    }

    public void setIResistance(IResistance mIResistance) {
        this.mIResistance = mIResistance;
    }

    //------------------------------------------- 滚动策略 ------------------------------------------------
    public abstract class IScroll {
        private static final int DEFAULT_DURATION = 250;
        private ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);

        {
            valueAnimator.setDuration(DEFAULT_DURATION);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addListener(new SimpleAnimatorListener() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    LogUtils.d("onAnimationEnd");

                    if (mAnimateBack != AnimateBack.Auto_Refresh) {
                        LogUtils.d("mAnimateBack!= AnimateBack.Auto_Refresh-->mAnimateBack:" + mAnimateBack + "--->refreshCompeleStateToRest");
                        refreshCompeleStateToRest();
                    }
                    //和上边顺序不要不调换!
                    if (mAnimateBack == AnimateBack.RefreshAble_Back) {
                        notityRefresh();
                    }
                    if (state == AUTO_PULL) {
                        LogUtils.d("state==AUTO_PULL");
                        if (mPullListener != null)
                            mPullListener.refresh(ZRefreshLayout.this);
//                            if (mIHeaderView != null)
//                                mIHeaderView.onRefreshing(getRefreshAbleHeight(), true);
                    }

                }
            });
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Integer offset = (Integer) animation.getAnimatedValue();
                    if (mIHeaderView != null)
                        mIHeaderView.animateBack(mAnimateBack,
                                1F * Math.abs(getScrollY()) / getRefreshAbleHeight(),
                                getRefreshAbleHeight(), isPinHeader);
                    scrollTo(offset, true);
                }

            });
        }

        void refreshCompeleStateToRest() {
            if (state == REFRESHING || state == PULL || state == AUTO_PULL) {
                LogUtils.d("刷新结束， 回滚到0");
                if (mPullListener != null && state == REFRESHING) {
                    mPullListener.refreshAnimationComplete(ZRefreshLayout.this);
                }
                state = COMPLETE;
                LogUtils.d("refreshAnimationComplete");
                if (mIHeaderView != null)
                    mIHeaderView.onComplete();
                state = REST;
                mAnimateBack = AnimateBack.None;
                haveDownEvent = false;
            }
            if (state == LOADMORE_ING || state == LOAD) {
                state = REST;
                mAnimateBack = AnimateBack.None;
                haveDownEvent = false;
            }

        }

        private void smoothScrollTo_(int fy) {
            if (state == PULL || state == REFRESH_ABLE) {
                if (mIHeaderView.interceptAnimateBack(mAnimateBack, mIScroll))
                    return;
            }
            valueAnimator.setIntValues(getScrollY(), fy);
            valueAnimator.start();
        }

        void smoothScrollTo_NotIntercept(int fy) {
            valueAnimator.setIntValues(getScrollY(), fy);
            valueAnimator.start();
        }

        private void scrollTo_(int fy) {
            scrollTo(fy > 0 ? fy : 0, false);
        }

        private void scrollTo_(int fy, boolean isAnimate) {
            scrollTo(fy > 0 ? fy : 0, isAnimate);
        }

        /**
         * @param fy
         * @param isAnimate 是动画的话就不做滚动映射了
         */
        protected abstract void scrollTo(int fy, boolean isAnimate);

        protected abstract int getScrollY();
    }

    public class Scroll_Pin extends IScroll {

        @Override
        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIResistance != null && !isAnimate)
                fy = mIResistance.getOffSetYMapValue(getRefreshAbleHeight(), fy);
            if (mIHeaderView != null && !isAnimate)
                mIHeaderView.onPullingDown(1F * Math.abs(fy) / getRefreshAbleHeight(), getRefreshAbleHeight());
            headerView.setTranslationY(fy);
        }

        @Override
        protected int getScrollY() {
            int scrolly = (int) headerView.getTranslationY();
            return scrolly;
        }
    }

    public class Scroll_PinNot extends IScroll {

        @Override
        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIResistance != null && !isAnimate)
                fy = mIResistance.getOffSetYMapValue(getRefreshAbleHeight(), fy);
            if (mIHeaderView != null && !isAnimate)
                mIHeaderView.onPullingDown(1F * Math.abs(fy) / getRefreshAbleHeight(), getRefreshAbleHeight());
            ZRefreshLayout.this.scrollTo(0, -fy);
        }

        @Override
        protected int getScrollY() {
            int scrolly = -ZRefreshLayout.this.getScrollY();
            return scrolly;
        }
    }

    public class ScrollFooter_Pin extends IScroll {

        @Override
        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIResistance != null && !isAnimate)
                fy = mIResistance.getOffSetYMapValue(getRefreshAbleHeight(), fy);
//            if (mIHeaderView != null && !isAnimate)
//                mIHeaderView.onPullingDown(1F * Math.abs(fy) / getRefreshAbleHeight(), getRefreshAbleHeight());
            footerView.setTranslationY(-fy);
        }

        @Override
        protected int getScrollY() {
            int scrolly = (int) footerView.getTranslationY();
            return scrolly;
        }
    }

    public class ScrollFooter_PinNot extends IScroll {

        @Override
        protected void scrollTo(int fy, boolean isAnimate) {
            if (mIResistance != null && !isAnimate)
                fy = mIResistance.getOffSetYMapValue(getRefreshAbleHeight(), fy);
//            if (mIHeaderView != null && !isAnimate)
//                mIHeaderView.onPullingDown(1F * Math.abs(fy) / getRefreshAbleHeight(), getRefreshAbleHeight());
            ZRefreshLayout.this.scrollTo(0, fy);
        }

        @Override
        protected int getScrollY() {
            int scrolly = ZRefreshLayout.this.getScrollY();
            return scrolly;
        }
    }

    public RefreshAbleListener getRefreshAbleListener() {
        return mRefreshAbleListener;
    }

    public void setRefreshAbleListener(RefreshAbleListener mRefreshAbleListener) {
        this.mRefreshAbleListener = mRefreshAbleListener;
    }


    //-------------------------内部接口----------------------------------------------------
    public interface PullListener {
        void refresh(ZRefreshLayout zRefreshLayout);

        void refreshAnimationComplete(ZRefreshLayout zRefreshLayout);
    }

    public interface RefreshAbleListener {
        void refreshAble(boolean refreshAble);
    }

    public interface LoadMoreListener {
        void loadMore(ZRefreshLayout zRefreshLayout);

        void loadMoreAnimationComplete(ZRefreshLayout zRefreshLayout);
    }
}
