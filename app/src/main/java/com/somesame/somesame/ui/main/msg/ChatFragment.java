package com.somesame.somesame.ui.main.msg;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.base.BaseFragment;

import butterknife.BindView;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/15
 */

public class ChatFragment extends BaseFragment {
    @BindView(R.id.listView)
    RecyclerView recyclerView;
    private ChatAdapter mAdapter;


    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_contact_msg;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mAdapter = new ChatAdapter(mContext,8);
        recyclerView.setAdapter(mAdapter);

    }
    public class ChatAdapter extends RecyclerSwipeAdapter<ChatAdapter.MyViewHolder>{
        private Context context;
        private int size;

        public ChatAdapter(Context context,int size){
            this.context = context;
            this.size = size;
        }

        @Override
        public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_msg_chat, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatAdapter.MyViewHolder viewHolder, int i) {
         viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }

        @Override
        public int getItemCount() {
            return size;
        }

        @Override
        public int getSwipeLayoutResourceId(int i) {
            return i;
        }
        public  class MyViewHolder extends RecyclerView.ViewHolder{
            private SwipeLayout swipeLayout;
            public MyViewHolder(View itemView) {
                super(itemView);
                swipeLayout=(SwipeLayout)itemView.findViewById(R.id.swipeLayout);
            }
        }

    }
}
