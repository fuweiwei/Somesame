package com.somesame.somesame.ui.main.home;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/14
 */

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.LinearViewHolder>{
    private Context mContext;
    private AdapterView.OnItemClickListener mListener;
    private List<String> list=new ArrayList<>();
    private int mSize;

    public StaggeredGridAdapter(Context context,int size) {
        this.mContext = context;
        this.mSize = size;
        for(int i=0;i<size;i++){
            list.add(String.format("%s-%s", i/10+1,i));
        }
    }

    @Override
    public StaggeredGridAdapter.LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.griditem_home_content,parent,false));
    }

    @Override
    public void onBindViewHolder(StaggeredGridAdapter.LinearViewHolder holder, final int position) {
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

    }
    @Override
    public int getItemCount() {
        return mSize;
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private LinearLayout linearLayout;
        public LinearViewHolder(View itemView){
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.iv);
            linearLayout = itemView.findViewById(R.id.view_content);
        }
    }

}
