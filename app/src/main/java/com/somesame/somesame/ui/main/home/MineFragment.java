package com.somesame.somesame.ui.main.home;

import android.os.Bundle;
import android.view.View;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/13
 */

public class MineFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
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

    }
}
