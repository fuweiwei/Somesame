package com.somesame.somesame.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.somesame.somesame.R;

/***
 * 统一标题栏
 * @author Veer
 * @email  276412667@qq.com
 * @date   18/11/16
 */
public class BaseToolBar extends LinearLayout {
    //上下文，创建view的时候需要用到
    protected Context mContext;
    protected View mToolBarView; //自定义的title布局

    //toolbar
    protected RelativeLayout mToolBar; //标题布局
    protected ImageView toolbarBack; //返回
    protected ImageView toolbarRightImg; //右边图片
    protected TextView toolbarTitle; //标题
    protected TextView toolbarTextRight; //右边文字
    protected TextView dividerLine;
    protected LinearLayout mLayoutTitle;

    //视图构造器
    protected LayoutInflater mInflater;

    public BaseToolBar(Context context) {
        super(context);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化toolbar*/
        initToolBar();
    }

    public BaseToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化toolbar*/
        initToolBar();
    }

    public BaseToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化toolbar*/
        initToolBar();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseToolBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化toolbar*/
        initToolBar();
    }

    protected void initToolBar() {
        /*通过inflater获取toolbar的布局文件*/
        mToolBarView = mInflater.inflate(R.layout.toolbar_title_view, this);
        mToolBar = (RelativeLayout) mToolBarView.findViewById(R.id.toolbar);
        toolbarBack = (ImageView) mToolBarView.findViewById(R.id.toolbar_back);
        toolbarRightImg = (ImageView) mToolBarView.findViewById(R.id.toolbar_iv_right);
        toolbarTitle = (TextView) mToolBarView.findViewById(R.id.toolbar_title);
        toolbarTextRight = (TextView) mToolBarView.findViewById(R.id.toolbar_text_right);
        dividerLine = (TextView) mToolBarView.findViewById(R.id.divider_line);
        mLayoutTitle = (LinearLayout) mToolBarView.findViewById(R.id.layout_title);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //设置状态栏背景状态
//            //true：表明当前Android系统版本>=4.4
//            mLayoutTitle.setPadding(0, UltimateBar.getStatusBarHeight(mContext), 0, 0);
//        }
//        else {
//            LayoutParams linearParams = (LayoutParams) mToolBar.getLayoutParams(); //取控件textView当前的布局参数
//            linearParams.height = mContext.getResources().getDimensionPixelOffset(R.dimen.height_5_5_80);// 控件的高强制设成20
//            mToolBar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件</pre>
//            mToolBar.setPadding(0, 0, 0, 0);
//        }
    }

    public RelativeLayout getToolBar() {
        return mToolBar;
    }

    public View getToolBarView() {
        return mToolBarView;
    }

    public TextView getToolbarTitle() {
        return toolbarTitle;
    }

    public void setToolbarTitle(String title) {
        if (null != toolbarTitle) {
            toolbarTitle.setText(title);
        }
    }

    public void setToolbarTitle(int title) {
        if (null != toolbarTitle) {
            toolbarTitle.setText(getResources().getString(title));
        }
    }

    public ImageView getToolbarBack() {
        return toolbarBack;
    }
    /***
     * 设置左边的返回键不显示
     * @date  17/11/1 下午7:47
     * @author  Veer
     */
    public void setLeftGone(){
        if(toolbarBack!=null){
            toolbarBack.setVisibility(GONE);

        }
    }
    /***
     * 设置左边的返回键显示
     * @date  17/11/1 下午7:47
     * @author  Veer
     */
    public void setLeftVisible(){
        if(toolbarBack!=null){
            toolbarBack.setVisibility(VISIBLE);

        }
    }

    public TextView getToolbarTextRight() {
        return toolbarTextRight;
    }

    public void setToolbarTextRight(String textRight) {
        if (!TextUtils.isEmpty(textRight)) {
            if (null != toolbarTextRight && !toolbarTextRight.isShown()) {
                toolbarTextRight.setVisibility(View.VISIBLE);
                this.toolbarTextRight.setText(textRight);
            }
        }else {
            toolbarTextRight.setVisibility(View.GONE);
        }
    }
    public void setToolbarTextRightColor(Context context, int textRightColor) {
            if (null != toolbarTextRight && !toolbarTextRight.isShown()) {
                toolbarTextRight.setTextColor(ContextCompat.getColor(context,textRightColor));
        }
    }

    public void setToolbarTextRight(int textRight) {
        if (textRight > 0) {
            if (null != toolbarTextRight && !toolbarTextRight.isShown()) {
                toolbarTextRight.setVisibility(View.VISIBLE);
            }
        }
        if (null != toolbarTextRight) {
            this.toolbarTextRight.setText(getResources().getString(textRight));
        }
    }
    public void setToolbarImgRight(int imgRight,View.OnClickListener onClickListener) {
        if (imgRight > 0) {
            if (null != toolbarRightImg && !toolbarRightImg.isShown()) {
                toolbarRightImg.setVisibility(View.VISIBLE);
            }
        }
        if (null != toolbarRightImg) {
            this.toolbarRightImg.setImageResource(imgRight);
            this.toolbarRightImg.setOnClickListener(onClickListener);
        }
    }

    public void setToolbarRightTextVisibility(int visibility) {
        if (null != toolbarTextRight) {
            this.toolbarTextRight.setVisibility(visibility);
        }
    }

    public void setToolbarTextRightOnClickListener(OnClickListener listener) {
        if (null != toolbarTextRight) {
            this.toolbarTextRight.setOnClickListener(listener);
        }
    }

    public void setIsShownDividerLine(boolean isShownDividerLine) {
        if (null != dividerLine) {
            dividerLine.setVisibility(isShownDividerLine ? View.VISIBLE : View.GONE);
        }
    }
}
