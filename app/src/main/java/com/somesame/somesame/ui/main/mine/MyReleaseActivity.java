package com.somesame.somesame.ui.main.mine;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.ui.main.home.HomeContentFragment;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/19
 */
@Route(path= ActivityContracts.ACTIVITY_MYRELEASE)
public class MyReleaseActivity extends BaseActivity {
    private HomeContentFragment mHomeContentFragment;
    @Override
    protected void initView() {
        mBaseToolBar.setToolbarTitle("我发布的文章");
        mHomeContentFragment = new HomeContentFragment();
        FragmentManager fmManager = getSupportFragmentManager();
        FragmentTransaction transaction = fmManager.beginTransaction();
        transaction.replace(R.id.fragment,mHomeContentFragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_my_release;
    }
}
