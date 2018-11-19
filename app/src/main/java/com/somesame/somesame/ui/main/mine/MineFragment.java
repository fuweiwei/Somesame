package com.somesame.somesame.ui.main.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.ui.main.home.HomeContract;
import com.somesame.somesame.widget.FrameLayout4Loading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/13
 */

public class MineFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lin_follow)
    LinearLayout linFollow;
    @BindView(R.id.lin_fans)
    LinearLayout linFans;
    @BindView(R.id.lin_zan)
    LinearLayout linZan;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    @BindView(R.id.lin_release)
    LinearLayout linRelease;
    @BindView(R.id.rel_recharge)
    RelativeLayout relRecharge;
    @BindView(R.id.loading)
    FrameLayout4Loading loading;

    @Override
    protected HomeContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.ic_face1);
        list.add(R.mipmap.ic_face2);
        list.add(R.mipmap.ic_face3);
        list.add(R.mipmap.ic_face4);
        list.add(R.mipmap.ic_face5);
        list.add(R.mipmap.ic_face6);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MyGridAdapter(mContext, list));
    }


    @OnClick({R.id.lin_follow, R.id.lin_fans, R.id.lin_zan, R.id.lin_release,R.id.btn_edit, R.id.rel_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_follow:
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_MYFOLLOW)
                        .navigation();
                break;
            case R.id.lin_fans:
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_MYFANS)
                        .navigation();
                break;
            case R.id.lin_zan:
                break;
            case R.id.lin_release:
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_MYRELEASE)
                        .navigation();
                break;
            case R.id.btn_edit:
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_EDIT_MYINFO)
                        .navigation();
                break;
            case R.id.rel_recharge:
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_RECHARGE)
                        .navigation();
                break;
        }
    }

    private class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder> {
        private Context context;
        private List<Integer> picList;

        public MyGridAdapter(Context context, List<Integer> picList) {
            this.context = context;
            this.picList = picList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.griditem_mine_content, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.imageView.setImageResource(picList.get(position));
        }

        @Override
        public int getItemCount() {
            return picList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv);
            }
        }
    }


}
