package com.somesame.somesame.ui.main.home;

import android.os.Bundle;
import android.view.View;

import com.somesame.framework.widget.autoscrollview.GrallyView;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;

import butterknife.BindView;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/14
 */

public class HomeRecommendFragment extends BaseFragment<HomeRecommendContract.Presenter> implements HomeRecommendContract.View {
    @BindView(R.id.adView)
    GrallyView grallyView;
    @Override
    protected HomeRecommendContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
