package com.somesame.somesame.ui.main.msg;

import android.os.Bundle;
import android.view.View;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.base.BaseFragment;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/15
 */

public class ContactFragment extends BaseFragment {


    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
