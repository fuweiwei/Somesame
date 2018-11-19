package com.somesame.somesame.ui.main.msg;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseFragment;
import com.somesame.somesame.ui.main.home.HomeContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/13
 */

public class MsgFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.rel_msg)
    RelativeLayout relMsg;
    @BindView(R.id.iv_contact)
    ImageView ivContact;
    @BindView(R.id.rel_contact)
    RelativeLayout relContact;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    private final SparseArray<BaseFragment> mFragments = new SparseArray<BaseFragment>();
    private BaseFragment mLastFragment;
    private ChatFragment mChatFragment;
    private ContactFragment mContactFragment;
    public static final int TAB_MSG = 1, TAB_CONTACT = 2;

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
        relMsg.setTag(TAB_MSG);
        relContact.setTag(TAB_CONTACT);
        setFragmentIndicator(TAB_MSG);
    }

    public void setFragmentIndicator(int tag) {
        setBtnStatus(tag);
        BaseFragment newInfo = mFragments.get(tag);
        FragmentManager fmManager = getChildFragmentManager();
        FragmentTransaction transaction = fmManager.beginTransaction();
        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        if (newInfo == null) {
            // 如果newInfo为空，则创建一个并添加到界面上
            switch (tag) {
                case TAB_MSG:
                    mChatFragment = new ChatFragment();
                    newInfo = mChatFragment;
                    break;

                case TAB_CONTACT:
                    mContactFragment = new ContactFragment();
                    newInfo = mContactFragment;
                    break;
                default:
                    break;
            }
            mFragments.put(tag, newInfo);
            transaction.add(R.id.fragment, newInfo,
                    String.valueOf(tag));
        } else {
            transaction.show(newInfo);
        }
        mLastFragment = newInfo;
        //提交事务
        transaction.commitAllowingStateLoss();
    }

    private void setBtnStatus(int tag) {
           ivMsg.setImageResource(R.mipmap.ic_contact);
           ivContact.setImageResource(R.mipmap.ic_mail);
        switch (tag) {
            case TAB_MSG:
                ivMsg.setImageResource(R.mipmap.ic_contact_s);
                break;
            case TAB_CONTACT:
                ivContact.setImageResource(R.mipmap.ic_mail_s);
                break;
            default:
                break;
        }

    }
    @OnClick({R.id.rel_msg, R.id.rel_contact})
    public void onViewClicked(View view) {
        int tag = (int) view.getTag();
        switch (view.getId()) {
            case R.id.rel_msg:
                setFragmentIndicator(tag);
                break;
            case R.id.rel_contact:
                setFragmentIndicator(tag);
                break;
        }
    }
}
