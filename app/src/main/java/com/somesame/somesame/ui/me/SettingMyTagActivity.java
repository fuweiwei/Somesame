package com.somesame.somesame.ui.me;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;
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
 * @date 18/11/20
 */
@Route(path = ActivityContracts.ACTIVITY_SETTING_ME_TAG)
public class SettingMyTagActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.listView)
    RecyclerView recyclerView;
    @BindView(R.id.loading)
    FrameLayout4Loading loading;
    private List<String> mList;

    @Override
    protected void initView() {
        mBaseToolBar.setToolbarTitle("设置我的个人标签");
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        mList = new ArrayList<>();
        mList.add("动漫控");
        mList.add("手办收藏者");
        mList.add("化妆晚会控");
        mList.add("游戏控");
        mList.add("活动女王");
        mList.add("体育爱好者");
        mList.add("电音控");
        mList.add("萝莉控");
        mList.add("二次元萌妹控");
        recyclerView.setAdapter(new MyTagAdapter(mContext,mList));

    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_setting_me_tag;
    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        ARouter.getInstance()
                .build(ActivityContracts.ACTIVITY_MAIN)
                .navigation();
        finish();

    }

    private class MyTagAdapter extends RecyclerView.Adapter<MyTagAdapter.ViewHolder>{
        private List<String> list;
        private Context context;


        public MyTagAdapter(Context context ,List<String> list){
            this.list = list;
            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listitem_my_tag,parent,false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.button_item.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private Button button_item;

            public ViewHolder(View itemView) {
                super(itemView);
                button_item = itemView.findViewById(R.id.btn_item);
            }
        }
    }
}
