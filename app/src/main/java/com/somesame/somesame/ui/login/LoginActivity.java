package com.somesame.somesame.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.db.dao.DbUserDao;
import com.somesame.somesame.db.entity.User;
import com.somesame.somesame.widget.FrameLayout4Loading;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View  {
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_password)
    EditText mEtPassWord;
    @BindView(R.id.btn)
    Button mBtn;
    @BindView(R.id.loading)
    FrameLayout4Loading mFrameLayout4Loading;

    @Override
    protected void initView() {
        User user = DbUserDao.getInstance().getUser();
        if(!TextUtils.isEmpty(user.getName())){
            mEtName.setText(user.getName());
            mEtPassWord.setText(user.getPassword());
        }
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn)
    public void onToBanner(View view){
        String name = mEtName.getText().toString();
        String password = mEtPassWord.getText().toString();
        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){
            mPresenter.login(mFrameLayout4Loading,name,password);
        }else{
            ToastUtils.showShort("请输入正确的用户和密码");
        }
    }

    @Override
    public void loginSuccess(String msg) {
        ToastUtils.showShort(msg);
        ARouter.getInstance()
                .build(ActivityContracts.ACTIVITY_BOOK)
                .navigation();
    }

    @Override
    public void loginError(String msg) {
        ToastUtils.showShort(msg);

    }
}
