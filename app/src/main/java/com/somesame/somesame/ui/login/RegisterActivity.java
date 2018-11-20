package com.somesame.somesame.ui.login;

import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.ui.base.ToolBarType;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/20
 */
@Route(path = ActivityContracts.ACTIVITY_REGISTER)
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.btn)
    Button btn;

    @Override
    protected void initView() {

    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_register;
    }
    protected ToolBarType getToolBarType() {
        return ToolBarType.NO;
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        ARouter.getInstance()
                .build(ActivityContracts.ACTIVITY_SETTING_ME_INFO)
                .navigation();
        finish();
    }
}
