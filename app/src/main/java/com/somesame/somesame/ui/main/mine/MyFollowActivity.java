package com.somesame.somesame.ui.main.mine;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;

import butterknife.BindView;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/19
 */
@Route(path= ActivityContracts.ACTIVITY_MYFOLLOW)
public class MyFollowActivity extends BaseActivity {
    @BindView(R.id.listView)
    RecyclerView recyclerView;
    @Override
    protected void initView() {
        mBaseToolBar.setToolbarTitle("我的关注");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new MyFollowAdapter(mContext,10));
    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_myfans;
    }
    public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.MyViewHolder> {
        private Context context;
        private int size;

        public MyFollowAdapter(Context context, int size){
            this.context = context;
            this.size = size;
        }

        @Override
        public MyFollowAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_my_follow, viewGroup, false);
            return new MyFollowAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return size;
        }

        public  class MyViewHolder extends RecyclerView.ViewHolder{
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}
