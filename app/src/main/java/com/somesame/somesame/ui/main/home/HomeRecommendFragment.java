package com.somesame.somesame.ui.main.home;

import android.content.Context;
import android.os.Bundle;
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
        listUrls.add("http://pic5.bbzhi.com/chuangyibizhi/tonghuashijiekuanpingshiliangbizhi/tonghuashijiekuanpingshiliangbizhi_469355_9.jpg");
        listUrls.add("http://pic33.nipic.com/20130926/12271667_220916671108_2.jpg");
        listUrls.add("http://pic5.bbzhi.com/chuangyibizhi/shouhuojijieqiutiankuanpingshiliangbizhi/shouhuojijieqiutiankuanpingshiliangbizhi_467551_9.jpg");
        banner.setImages(listUrls);
        banner.setBannerAnimation(Transformer.RotateDown);
        banner.isAutoPlay(true);
        banner.start();
    }
}
