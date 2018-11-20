package com.somesame.somesame.ui.me;

import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.widget.FrameLayout4Loading;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/20
 */
@Route(path = ActivityContracts.ACTIVITY_SETTING_ME_INFO)
public class SettingMyInfoActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.loading)
    FrameLayout4Loading loading;

    @Override
    protected void initView() {
        mBaseToolBar.setToolbarTitle("设置我的个人资料");
    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_setting_me_info;
    }


    @OnClick(R.id.btn)
    public void onViewClicked() {
        ARouter.getInstance()
                .build(ActivityContracts.ACTIVITY_SETTING_ME_TAG)
                .navigation();
        finish();

    }
}
