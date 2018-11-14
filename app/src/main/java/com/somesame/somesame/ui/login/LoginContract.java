package com.somesame.somesame.ui.login;

import com.somesame.somesame.base.BaseContract;
import com.somesame.somesame.widget.FrameLayout4Loading;

/**
 * 主页配置约定
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/8/16
 */

public interface LoginContract {
    interface View extends BaseContract.BaseView{
        void loginSuccess(String msg);
        void loginError(String msg);

    }
    interface Presenter extends BaseContract.BasePresenter<View>{
        void login(FrameLayout4Loading frameLayout4Loading, String name, String password);
    }
}
