package com.somesame.somesame.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.somesame.framework.widget.refresh.Config;
import com.somesame.framework.widget.refresh.footer.MeterialFooter;
import com.somesame.framework.widget.refresh.header.MeterialHead;
import com.somesame.framework.widget.refresh.resistance.DampingHalf;
import com.somesame.somesame.BuildConfig;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/7/2
 */

public class MyApplication extends Application {
    private static MyApplication mMyApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;
        //初始化工具包
        Utils.init(this);
        intARouter();
        initRefresh();
    }
    /**
     * 初始化路由
     */
    private void intARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }
    public static MyApplication getApplication() {
        return mMyApplication;
    }
    /**
     * 下拉刷新与上拉加载更多控件初始化
     * 此处设置的参数为全局的，也可以根据不同页面的效果在不同页面设置ZRefreshLayout来完成
     */
    private void initRefresh() {
        Config.build()
                .setHeader(new MeterialHead()) //头部效果
                .setFooter(new MeterialFooter()) //加载更多效果
                .setResistance(new DampingHalf())
                .setSpringback(false) //在没有加载监听时不需要效果
                .setPinHeader(true) //头部固定，加载样式悬浮，类似meteria效果
                .setPinFooter(false) //加载更多不悬浮，跟着布局后面
                .writeLog(true)
                .perform();
    }
}
