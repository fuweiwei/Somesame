package com.somesame.somesame.ui.main.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MyGridAdapter(mContext,list));
    }
    private class  MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder>{
        private Context context;
        private List<Integer> picList;
        public MyGridAdapter(Context context,List<Integer> picList){
            this.context = context;
            this.picList = picList;
        }

        @Override
        public MyGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.griditem_mine_content,parent,false));
        }

        @Override
        public void onBindViewHolder(MyGridAdapter.ViewHolder holder, int position) {
         holder.imageView.setImageResource(picList.get(position));
        }

        @Override
        public int getItemCount() {
            return picList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageView;
            public ViewHolder(View itemView){
                super(itemView);
                imageView=(ImageView) itemView.findViewById(R.id.iv);
            }
        }
    }


}
