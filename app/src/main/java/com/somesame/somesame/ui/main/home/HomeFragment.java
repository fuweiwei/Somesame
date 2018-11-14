package com.somesame.somesame.ui.main.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/13
 */

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.view_recommend)
    View viewRecommend;
    @BindView(R.id.lin_recommend)
    LinearLayout linRecommend;
    @BindView(R.id.tv_friends)
    TextView tvFriends;
    @BindView(R.id.view_friends)
    View viewFriends;
    @BindView(R.id.lin_friends)
    LinearLayout linFriends;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.view_follow)
    View viewFollow;
    @BindView(R.id.lin_follow)
    LinearLayout linFollow;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.view_new)
    View viewNew;
    @BindView(R.id.lin_new)
    LinearLayout linNew;
    @BindView(R.id.vp)
    ViewPager vp;
    public static final int TAB1 = 0, TAB2 = 1,TAB3 = 2, TAB4 = 3;
    private FragmentPagerAdapter mAdapter;
    private final SparseArray<BaseFragment> mFragments = new SparseArray<BaseFragment>();
    private HomeRecommendFragment mHomeRecommendFragment;
    private HomeFriendsFragment mHomeFriendsFragment;
    private HomeFollowFragment mHomeFollowFragment;
    private HomeNewFragment mHomeNewFragment;

    @Override
    protected HomeContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        linRecommend.setTag(TAB1);
        linFriends.setTag(TAB2);
        linFollow.setTag(TAB3);
        linNew.setTag(TAB4);

        mHomeRecommendFragment = new HomeRecommendFragment();
        mHomeFriendsFragment = new HomeFriendsFragment();
        mHomeFollowFragment = new HomeFollowFragment();
        mHomeNewFragment = new HomeNewFragment();
        mFragments.put(TAB1,mHomeRecommendFragment);
        mFragments.put(TAB2,mHomeFriendsFragment);
        mFragments.put(TAB3,mHomeFollowFragment);
        mFragments.put(TAB4,mHomeNewFragment);
        mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        vp.setAdapter(mAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBtn(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(TAB1);

    }
    private void setBtn(int tag){
        viewFollow.setVisibility(View.GONE);
        viewFriends.setVisibility(View.GONE);
        viewRecommend.setVisibility(View.GONE);
        viewNew.setVisibility(View.GONE);
        tvRecommend.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_black));
        tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_black));
        tvFriends.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_black));
        tvNew.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_black));
        switch (tag){
            case TAB1:
                viewRecommend.setVisibility(View.VISIBLE);
                tvRecommend.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                break;
            case TAB2:
                viewFriends.setVisibility(View.VISIBLE);
                tvFriends.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                break;
            case TAB3:
                viewFollow.setVisibility(View.VISIBLE);
                tvFollow.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                break;
            case TAB4:
                viewNew.setVisibility(View.VISIBLE);
                tvNew.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.lin_recommend, R.id.lin_friends, R.id.lin_follow, R.id.lin_new})
    public void onViewClicked(View view) {
        int tag = (int) view.getTag();
        switch (view.getId()) {
            case R.id.lin_recommend:
                vp.setCurrentItem(tag);
                break;
            case R.id.lin_friends:
                vp.setCurrentItem(tag);
                break;
            case R.id.lin_follow:
                vp.setCurrentItem(tag);
                break;
            case R.id.lin_new:
                vp.setCurrentItem(tag);
                break;
                default:
                    break;
        }
    }
}