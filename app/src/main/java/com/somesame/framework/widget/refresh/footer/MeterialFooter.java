package com.somesame.framework.widget.refresh.footer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.somesame.framework.widget.refresh.AUtils;
import com.somesame.framework.widget.refresh.IFooterView;
import com.somesame.framework.widget.refresh.ZRefreshLayout;
import com.somesame.framework.widget.refresh.utils.ScreenUtils;
import com.somesame.framework.widget.refresh.v4.MeterialCircle;
import com.somesame.somesame.R;


/**
 */
public class MeterialFooter implements IFooterView {
    private MeterialCircle mMeterialCircle;
    private ViewGroup rootView;

    @Override
    public View getView(ZRefreshLayout zRefreshLayout) {
        rootView = (ViewGroup) View.inflate(zRefreshLayout.getContext(), R.layout.header_meterial, null);
        //注意inflate那种模式  第一层需要空出去 不然会wrapcontent
        LinearLayout ll_main = (LinearLayout) rootView.findViewById(R.id.ll_main);
        int[] screenPixs = ScreenUtils.getScreenPix((Activity) zRefreshLayout.getContext());
        ll_main.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (screenPixs[1] * 0.1)));
        mMeterialCircle=new MeterialCircle(ll_main,(int) (screenPixs[1] * 0.065));
        rootView.addView(mMeterialCircle.getView());
        rootView.setVisibility(View.INVISIBLE);
        return rootView;
    }

    @Override
    public void onStart(ZRefreshLayout zRefreshLayout, float footerHeight,boolean isPinFooter) {
        if(!isPinFooter){
            rootView.setTranslationY(footerHeight);
        }
        rootView.setVisibility(View.VISIBLE);
        mMeterialCircle.start();
        AUtils.notityLoadMoreListener(zRefreshLayout);
    }

    @Override
    public void onComplete(ZRefreshLayout zRefreshLayout) {
        rootView.setVisibility(View.INVISIBLE);
        mMeterialCircle.reset();
        AUtils.notifyLoadMoreCompleteListener(zRefreshLayout);

    }
    @Override
    public IFooterView clone_() {
        MeterialFooter clone =new MeterialFooter();
        return clone;
    }
}
