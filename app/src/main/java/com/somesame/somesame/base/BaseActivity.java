package com.somesame.somesame.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.somesame.somesame.ui.base.BaseToolBar;
import com.somesame.somesame.ui.base.ToolBarType;
import com.somesame.somesame.widget.ProgressDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/7/2
 */

public abstract class BaseActivity<P extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {
    protected FragmentActivity mContext;
    protected P mPresenter;
    private Unbinder mUnBinder;
    private ProgressDialog mDialog;
    protected BaseToolBar mBaseToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getActivityLayoutID(),getToolBarType());
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化butterknife
        mUnBinder = ButterKnife.bind(this);
        mPresenter = initPresenter();
        attachView();
        initView();
    }
    private void setContentView(int layoutResID,ToolBarType toolBarType) {
        if(toolBarType == null){
            super.setContentView(layoutResID);
        }else{
            if(toolBarType == ToolBarType.NO){
                super.setContentView(layoutResID);
            }else if(toolBarType == ToolBarType.Default){
                mBaseToolBar = new BaseToolBar(mContext);
                View view = this.getLayoutInflater().inflate(layoutResID, null);
                if (view instanceof DrawerLayout) {
                    addTitleView(view, ((DrawerLayout) view).getChildAt(0), toolBarType);
                } else {
                    addTitleView(view, view, toolBarType);
                }
                ImageView back = mBaseToolBar.getToolbarBack();
                if (null != back) {
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                }
            }

        }
    }
    /**
     * 动态添加头布局
     * @param parentView
     * @param view
     * @param type
     */
    private void addTitleView(View parentView, View view, ToolBarType type ) {
        if (view instanceof LinearLayout) {
            ((LinearLayout) view).addView(mBaseToolBar, 0);
            super.setContentView(parentView);
        }
        if (type == ToolBarType.Default && (view instanceof RelativeLayout || view instanceof FrameLayout)) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            //设置LinearLayout属性(宽和高)
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setLayoutParams(layoutParams);
            layout.addView(mBaseToolBar, 0);
            layout.addView(view);
            super.setContentView(layout);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
        }
    }
    /**
     * 挂载view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 卸载view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 设置标题类型
     * @return 默认有标题
     */
    protected  ToolBarType getToolBarType(){
        return ToolBarType.Default;
    };

    /**
     * 在子View中初始化Presenter
     *
     * @return
     */
    protected abstract P initPresenter();
    /**
     * 设置Activity的布局ID
     *
     * @return
     */
    protected abstract int getActivityLayoutID();

    @Override
    public void showLoading() {
        mDialog = new ProgressDialog(this);
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showSuccess(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showFailed(String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showNoNet() {
        ToastUtils.showShort("无网络");
    }

    @Override
    public void onRetry() {

    }


    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
