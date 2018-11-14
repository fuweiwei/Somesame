package com.somesame.framework.widget.refresh.header;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.somesame.framework.widget.refresh.AUtils;
import com.somesame.framework.widget.refresh.AnimateBack;
import com.somesame.framework.widget.refresh.IHeaderView;
import com.somesame.framework.widget.refresh.ZRefreshLayout;
import com.somesame.framework.widget.refresh.utils.ScreenUtils;
import com.somesame.framework.widget.refresh.v4.MeterialCircle;


/**
 */
public class MeterialHead implements IHeaderView {
    private  int[] colors;
    private MeterialCircle mMeterialCircle;
    private ZRefreshLayout zRefreshLayout;

    public MeterialHead(int[] colors) {
        this.colors = colors;
    }
    public MeterialHead() {
    }

    @Override
    public IHeaderView clone_() {
        MeterialHead clone = new MeterialHead(colors);
        return clone;
    }

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        this.zRefreshLayout = zRefreshLayout;
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        mMeterialCircle = new MeterialCircle(zRefreshLayout, (int) (screenPixs[1] * 0.065));
        if(colors!=null)
            mMeterialCircle.setColorSchemeColors(colors);
        AUtils.setHeaderHeightToRefresh(zRefreshLayout,(int) (screenPixs[1] * 0.065*2.0));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mMeterialCircle.getView().getLayoutParams();
        lp.bottomMargin= (int) (screenPixs[1] * 0.065/2);
        mMeterialCircle.getView().setLayoutParams(lp);
        return mMeterialCircle.getView();
    }


    @Override
    public void onPullingDown(float fraction, float headHeight) {
        mMeterialCircle.pullProgress(fraction);
    }

    @Override
    public void refreshAble(boolean refreshAble) {

    }

    @Override
    public void animateBack(AnimateBack animateBack, float fraction, float headHeight, boolean isPinContent) {
    }

    @Override
    public boolean interceptAnimateBack(AnimateBack animateBack, final ZRefreshLayout.IScroll iScroll) {
        if (zRefreshLayout.isPinHeader() && animateBack == AnimateBack.Complete_Back) {
            mMeterialCircle.startScaleDownAnimation(new MeterialCircle.ScaleDownCallback() {
                @Override
                public void over() {
                    AUtils.notityRefreshCompeleStateToRest(iScroll);
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onRefreshing(float headHeight, boolean isAutoRefresh) {
        if (isAutoRefresh) {
//            mMeterialCircle.startScaleUpAnimation();
        }
        mMeterialCircle.start();
    }

    @Override
    public void onComplete() {
        mMeterialCircle.reset();
        mMeterialCircle.getView().setTranslationY(0);//众神归位
    }
}
