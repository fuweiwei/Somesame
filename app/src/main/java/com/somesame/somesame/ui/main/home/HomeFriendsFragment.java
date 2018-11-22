package com.somesame.somesame.ui.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.somesame.framework.widget.refresh.ZRefreshLayout;
import com.somesame.framework.widget.refresh.footer.LoadFooter;
import com.somesame.framework.widget.refresh.header.SinaRefreshHeader;
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

public class HomeFriendsFragment extends BaseFragment<HomeRecommendContract.Presenter> implements HomeRecommendContract.View {
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
        return R.layout.fragment_home_friends;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new StaggeredListAdapter(mActivity,10));
        zRefreshLayout.setIHeaderView(new SinaRefreshHeader());
        zRefreshLayout.setPinHeader(false);
        zRefreshLayout.setIFooterView(new LoadFooter());
        zRefreshLayout.setLoadMoreListener(new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(final ZRefreshLayout zRefreshLayout) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        zRefreshLayout.refresh2LoadMoreComplete();
                    }
                }, 2000);
            }

            @Override
            public void loadMoreAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });
        zRefreshLayout.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(final ZRefreshLayout zRefreshLayout) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        zRefreshLayout.refresh2LoadMoreComplete();
                    }
                }, 2000);
            }

            @Override
            public void refreshAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });
        zRefreshLayout.setCanLoadMore(true);
    }
}
