package com.somesame.framework.widget.refresh.footer;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.somesame.framework.widget.refresh.AUtils;
import com.somesame.framework.widget.refresh.IFooterView;
import com.somesame.framework.widget.refresh.ZRefreshLayout;
import com.somesame.framework.widget.refresh.utils.ScreenUtils;
import com.somesame.somesame.R;


/**
 */
public class LoadFooter implements IFooterView, ValueAnimator.AnimatorUpdateListener {
    private View rootView;
    private ImageView loadingView;
    private ZRefreshLayout zRefreshLayout;
    private ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        this.zRefreshLayout = zRefreshLayout;
        rootView = View.inflate(zRefreshLayout.getContext(), R.layout.footer, null);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.1)));

        ViewGroup.LayoutParams loadingViewLp = loadingView.getLayoutParams();
        loadingViewLp.width = (int) (screenPixs[1] * 0.05);
        loadingViewLp.height = (int) (screenPixs[1] * 0.05);
        loadingView.setLayoutParams(loadingViewLp);
        rootView.setVisibility(View.INVISIBLE);
        initAnimator();
        return rootView;
    }

    private void initAnimator() {
        valueAnimator.setDuration(1200).setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(this);
    }

    @Override
    public void onStart(ZRefreshLayout zRefreshLayout, float footerHeight, boolean isPinFooter) {
        if (!isPinFooter) {
            rootView.setTranslationY(footerHeight);
        }
        rootView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        valueAnimator.start();
        AUtils.notityLoadMoreListener(zRefreshLayout);
    }

    @Override
    public void onComplete(ZRefreshLayout zRefreshLayout) {
        if (valueAnimator.isRunning())
            valueAnimator.end();
        loadingView.setVisibility(View.INVISIBLE);
        rootView.setVisibility(View.INVISIBLE);
        AUtils.notifyLoadMoreCompleteListener(zRefreshLayout);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        loadingView.setRotation(((Integer) animation.getAnimatedValue() / 30) * 30F);
    }

    @Override
    public IFooterView clone_() {
        LoadFooter clone = new LoadFooter();
        return clone;
    }
}
