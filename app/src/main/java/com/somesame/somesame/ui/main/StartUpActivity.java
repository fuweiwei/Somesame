package com.somesame.somesame.ui.main;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.ui.base.ToolBarType;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/19
 */
@Route(path= ActivityContracts.ACTIVITY_STARTUP)
public class StartUpActivity extends BaseActivity {
    @Override
    protected void initView() {
        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                ARouter.getInstance()
                        .build(ActivityContracts.ACTIVITY_MAIN)
                        .navigation();
                finish();
            }
        }, 2000);

    }
    @Override
    protected ToolBarType getToolBarType() {
        return ToolBarType.NO;
    }
    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_start_up;
    }
}
