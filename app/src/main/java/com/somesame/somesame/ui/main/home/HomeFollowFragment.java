package com.somesame.somesame.ui.main.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.somesame.framework.widget.refresh.ZRefreshLayout;
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

public class HomeFollowFragment extends BaseFragment<HomeRecommendContract.Presenter> implements HomeRecommendContract.View {
    @BindView(R.id.layout_refresh)
    ZRefreshLayout zRefreshLayout;
    @BindView(R.id.listView)
    RecyclerView recyclerView;
    @Override
    protected HomeRecommendContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_home_follow;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new StaggeredGridAdapter(mContext,3));
    }
}
