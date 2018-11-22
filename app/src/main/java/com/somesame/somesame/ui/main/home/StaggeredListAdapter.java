package com.somesame.somesame.ui.main.home;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.somesame.somesame.R;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.ui.common.ShareDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/14
 */

public class StaggeredListAdapter extends RecyclerView.Adapter<StaggeredListAdapter.LinearViewHolder>{
    private FragmentActivity mContext;
    private AdapterView.OnItemClickListener mListener;
    private List<String> list=new ArrayList<>();
    private int mSize;

    public StaggeredListAdapter(FragmentActivity context, int size) {
        this.mContext = context;
        this.mSize = size;
        for(int i=0;i<size;i++){
            list.add(String.format("%s-%s", i/10+1,i));
        }
    }

    @Override
    public StaggeredListAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_home_content,parent,false));
    }

    @Override
    public void onBindViewHolder(StaggeredListAdapter.LinearViewHolder holder, int position) {
        if (position % 2 == 0) {
            holder.imageView.setImageResource(R.mipmap.ic_baby1);
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_baby2);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_BOOK)
                        .navigation();
            }
        });
        holder.linearLayout_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog dialog = new ShareDialog();
                dialog.showAllowingStateLoss(mContext);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mSize;
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private LinearLayout linearLayout;
        private LinearLayout linearLayout_forward;
        public LinearViewHolder(View itemView){
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.iv);
            linearLayout = itemView.findViewById(R.id.view_content);
            linearLayout_forward = itemView.findViewById(R.id.lin_forward);
        }
    }

}
