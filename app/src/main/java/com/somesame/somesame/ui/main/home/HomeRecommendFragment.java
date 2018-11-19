package com.somesame.somesame.ui.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/14
 */

public class HomeRecommendFragment extends BaseFragment<HomeRecommendContract.Presenter> implements HomeRecommendContract.View {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private List<String> mTabList = new ArrayList<>();
    private  List<BaseFragment> mFragments = new ArrayList<>();


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
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object o, ImageView imageView) {
                Glide.with(mActivity).load(o).into(imageView);
            }
        });
        List<String> listUrls = new ArrayList<>();
        listUrls.add("http://5b0988e595225.cdn.sohucs.com/images/20181112/5e81aab009f34aac85ef2fd7bb4a0092.jpeg");
        listUrls.add("http://5b0988e595225.cdn.sohucs.com/images/20181112/872bc73fc04c41d198e4577c96ac9da5.jpeg");
        listUrls.add("http://02.imgmini.eastday.com/mobile/20171227/20171227163521_0402b38a8498671846efd5ea8a0e60f6_4.jpeg");
        listUrls.add("http://5b0988e595225.cdn.sohucs.com/images/20181112/15b608ba69f14faaa2ebc4f3a7db2c43.jpeg");
        banner.setImages(listUrls);
        banner.setBannerAnimation(Transformer.RotateDown);
        banner.isAutoPlay(true);
        banner.start();

        mTabList.add("二次元");
        mTabList.add("动漫控");
        mTabList.add("化妆舞会");
        mTabList.add("二次元");
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addTab(tabLayout.newTab().setText(mTabList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(mTabList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(mTabList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(mTabList.get(3)));
        HomeContentFragment fragment = new HomeContentFragment();
        HomeContentFragment fragment1 = new HomeContentFragment();
        HomeContentFragment fragment2 = new HomeContentFragment();
        HomeContentFragment fragment3 = new HomeContentFragment();
        mFragments.add(fragment);
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getChildFragmentManager(), mFragments, mTabList);
        viewPager.setAdapter(fragmentAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);//给Tabs设置适配器

    }
    public class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private List<BaseFragment> mFragments;
        private List<String> mTitles;

        public TabFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
