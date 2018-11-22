package com.somesame.somesame.ui.common;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 分享弹窗
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/20
 */

public class ShareDialog extends BaseDialogFragment {
    @BindView(R.id.lin_qq)
    LinearLayout linQq;
    @BindView(R.id.lin_wx)
    LinearLayout linWx;
    @BindView(R.id.lin_friends)
    LinearLayout linFriends;
    @BindView(R.id.lin_cancle)
    LinearLayout linCancle;

    @Override
    protected int getDialogLayoutID() {
        return R.layout.dialog_share;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);

    }


    @OnClick({R.id.lin_qq, R.id.lin_wx, R.id.lin_friends, R.id.lin_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_qq:
                break;
            case R.id.lin_wx:
                break;
            case R.id.lin_friends:
                break;
            case R.id.lin_cancle:
                dismissAllowingStateLoss();
                break;
        }
    }


}
