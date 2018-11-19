package com.somesame.somesame.ui.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.base.BaseFragment;
import com.somesame.somesame.ui.base.ToolBarType;
import com.somesame.somesame.ui.main.home.HomeFragment;
import com.somesame.somesame.ui.main.home.MineFragment;
import com.somesame.somesame.ui.main.msg.MsgFragment;
import com.somesame.somesame.ui.main.home.WorldFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页面
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/12
 */

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View{
    @BindView(R.id.lin_bar_home)
    LinearLayout mLLayoutBarHome;
    @BindView(R.id.lin_bar_msg)
    LinearLayout mLLayoutBarMsg;
    @BindView(R.id.lin_bar_world)
    LinearLayout mLLayoutBarWorld;
    @BindView(R.id.lin_bar_mine)
    LinearLayout mLLayoutBarMine;
    @BindView(R.id.lin_bar_nav)
    LinearLayout mLLayoutBarNav;

    @BindView(R.id.iv_home)
    ImageView mIvHome;
    @BindView(R.id.iv_msg)
    ImageView mIvMsg;
    @BindView(R.id.iv_world)
    ImageView mIvWorld;
    @BindView(R.id.iv_mine)
    ImageView mIvMine;

    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.tv_msg)
    TextView mTvMsg;
    @BindView(R.id.tv_world)
    TextView mTvWorld;
    @BindView(R.id.tv_mine)
    TextView mTvMine;

    private BaseFragment mLastFragment;
    private HomeFragment mHomeFragment;
    private MsgFragment mMsgFragment;
    private WorldFragment mWorldFragment;
    private MineFragment mMineFragment;
    private final SparseArray<BaseFragment> mFragments = new SparseArray<BaseFragment>();
    public static final int TAB_HOME = 1, TAB_MINE = 4,TAB_MSG = 2, TAB_WORLD = 3;

    @Override
    protected void initView() {
        mLLayoutBarHome.setTag(TAB_HOME);
        mLLayoutBarMsg.setTag(TAB_MSG);
        mLLayoutBarWorld.setTag(TAB_WORLD);
        mLLayoutBarMine.setTag(TAB_MINE);
        setFragmentIndicator(TAB_HOME);
    }

    @Override
    protected ToolBarType getToolBarType() {
        return ToolBarType.NO;
    }

    @Override
    protected MainContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_main;
    }
    public void setFragmentIndicator(int tag) {
        setBtnStatus(tag);
        BaseFragment newInfo = mFragments.get(tag);
        FragmentManager fmManager = getSupportFragmentManager();
        FragmentTransaction transaction = fmManager.beginTransaction();
        if (mLastFragment != null) {
            transaction.hide(mLastFragment);
        }
        if (newInfo == null) {
            // 如果newInfo为空，则创建一个并添加到界面上
            switch (tag) {
                case TAB_HOME:
                    mHomeFragment = new HomeFragment();
                    newInfo = mHomeFragment;
                    break;

                case TAB_MSG:
                    mMsgFragment = new MsgFragment();
                    newInfo = mMsgFragment;
                    break;
                case TAB_WORLD:
                    mWorldFragment = new WorldFragment();
                    newInfo = mWorldFragment;
                    break;
                case TAB_MINE:
                    mMineFragment = new MineFragment();
                    newInfo = mMineFragment;
                    break;
                default:
                    break;
            }
            mFragments.put(tag, newInfo);
            transaction.add(R.id.fragment_bar, newInfo,
                    String.valueOf(tag));
        } else {
            transaction.show(newInfo);
        }
        mLastFragment = newInfo;
        //提交事务
        transaction.commitAllowingStateLoss();
    }
    private void setBtnStatus(int tag) {
        mTvHome.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_gary));
        mTvMsg.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_gary));
        mTvWorld.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_gary));
        mTvMine.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_gary));
        mIvHome.setSelected(false);
        mIvMsg.setSelected(false);
        mIvWorld.setSelected(false);
        mIvMine.setSelected(false);
        switch (tag) {
            case TAB_HOME:
                mTvHome.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                mIvHome.setSelected(true);
                break;
            case TAB_MSG:
                mTvMsg.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                mIvMsg.setSelected(true);
                break;
            case TAB_WORLD:
                mTvWorld.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                mIvWorld.setSelected(true);
                break;
            case TAB_MINE:
                mTvMine.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_red));
                mIvMine.setSelected(true);
                break;
            default:
                break;
        }

    }
    @OnClick(R.id.lin_bar_home)
    public void onToHome(View view){
        setFragmentIndicator((Integer) view.getTag());
    }
    @OnClick(R.id.lin_bar_msg)
    public void onToMsg(View view){
        setFragmentIndicator((Integer) view.getTag());
    }
    @OnClick(R.id.lin_bar_world)
    public void onToWorld(View view){
        setFragmentIndicator((Integer) view.getTag());
    }
    @OnClick(R.id.lin_bar_mine)
    public void onToMine(View view){
        setFragmentIndicator((Integer) view.getTag());
    }

}
