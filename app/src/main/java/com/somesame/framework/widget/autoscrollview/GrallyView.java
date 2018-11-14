package com.somesame.framework.widget.autoscrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.somesame.somesame.R;
import com.somesame.somesame.model.home.HomeBannerModel;

import java.util.ArrayList;
import java.util.List;


/***
 * 轮播广告
 * @author Veer
 * @email  276412667@qq.com
 * @date   18/11/14
 */
public class GrallyView extends FrameLayout {

    private List<HomeBannerModel> list;
    private List<ImageView> arrimg;
    private AutoDownUpScrollViewPager viewPager;
    public PagerAdapter pagerAdapter;
    private LayoutInflater mInflater;
    private Context context;
    private IPageItemClicked iPageItemClicked;
    private LinearLayout ll;

    private int interval = 3000;
    Handler handle = new Handler(){
        @Override
        public void handleMessage(Message msg){
            int what = msg.what;
            if (what==1) {
                if (pagerAdapter != null) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }
            else if (what==2){
                //setStartAutoScroll();
            }
        }
    };



    public GrallyView(Context context) {
        super(context);
        init(context, null);

    }

    public GrallyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public GrallyView(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setOnItemClickEvent(IPageItemClicked iPageItemClicked) {
        this.iPageItemClicked = iPageItemClicked;
    }

    public void setStopAutoScroll() {
        if (viewPager != null) {
            viewPager.stopAutoScroll();
        }
    }

    public void setStartAutoScroll() {
        if (viewPager != null && this.list.size()>1) {
            viewPager.startAutoScroll();
        }
    }


    private void init(final Context context, AttributeSet attrs) {
        this.context = context;


        list = new ArrayList<HomeBannerModel>();
        mInflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(
                R.layout.home_grally_item, this, false);

        ll = (LinearLayout) view.findViewById(R.id.ll);
        viewPager = (AutoDownUpScrollViewPager) view.findViewById(R.id.view_pager);
        addView(view);
    }

    public void setData(List<HomeBannerModel> list) {

        if (list == null ) return;
        if (list.size()==0 ) return;
        if (list.size() == this.list.size()) {
            boolean fl = false;
            for (int i = 0; i < list.size(); i++) {
                HomeBannerModel bannerbodyModel = list.get(i);
                HomeBannerModel bannerbodyModel1 = this.list.get(i);
                if (!bannerbodyModel.getBannerId().equals(bannerbodyModel1.getBannerId())) {
                    fl = true;
                }
            }
            if (!fl) {
                return;
            }
        }

        this.list.clear();
        this.list.addAll(list);
        ll.removeAllViews();
        drawPoint();

        if (pagerAdapter == null) {
            pagerAdapter = new MyPagerAdapter();
            viewPager.setAdapter(pagerAdapter);
            viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
            viewPager.setCurrentItem(3 * 1000);
            viewPager.setInterval(interval);
            viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
            if(this.list.size()>1){
                viewPager.startAutoScroll();
            }
            viewPager.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            viewPager.stopAutoScroll();

                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(GrallyView.this.list.size()>1){
                                viewPager.startAutoScroll();
                            }else {
                                viewPager.stopAutoScroll();
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            if(GrallyView.this.list.size()>1){
                                viewPager.startAutoScroll();
                            }else {
                                viewPager.stopAutoScroll();
                            }

                            break;

                        default:
                            break;
                    }

                    return false;
                }

            });
        } else {

            handle.sendEmptyMessage(1);

        }

        //handle.sendEmptyMessage(2);


    }

    private void drawPoint() {
        arrimg = new ArrayList<ImageView>();
        for (int i = 0; i < list.size(); i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(R.mipmap.icon_dian);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            //params.bottomMargin = 5;
            arrimg.add(img);
            ll.addView(img, params);
        }
        if (arrimg.size() > 0) {
            draw_Point(0);
        }
    }

    private void draw_Point(int index) {
        if (list == null || arrimg == null || arrimg.size() == 0) return;
        for (int i = 0; i < list.size(); i++) {
            arrimg.get(i).setImageResource(R.mipmap.icon_dian);
        }
        if (index >= 0) {
            arrimg.get(index % list.size()).setImageResource(
                    R.mipmap.icon_dian_hei);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {

            draw_Point(position);

        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            // TODO 调整图片大小
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            android.view.ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);

            final HomeBannerModel bannerbodyModel = list.get(position % list.size());
            Glide.with(context).load(bannerbodyModel.getImageUrl()).into(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (iPageItemClicked != null) {
//                        viewPager.stopAutoScroll();
                        iPageItemClicked.onItemClick(bannerbodyModel,position % list.size());
                    }
                }
            });
            ((ViewPager) container).addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }

    public interface IPageItemClicked {
        public void onItemClick(HomeBannerModel bannerbodyModel, int position);
    }


}
