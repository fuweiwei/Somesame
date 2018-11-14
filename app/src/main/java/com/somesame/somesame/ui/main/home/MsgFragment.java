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

public class MsgFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    @Override
    protected HomeContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
