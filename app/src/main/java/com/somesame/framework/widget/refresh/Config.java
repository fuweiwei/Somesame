package com.somesame.framework.widget.refresh;


/**
 * Created by fuzhipeng on 2017/1/11.
 */

public class Config {

    IHeaderView headerView;
    IFooterView footerView;
    IResistance resistance;
    boolean isPinHeader;
    boolean isPinFooter;
    boolean isSpringback; //回弹效果，在没有上拉与下拉时是否需要这个效果
    boolean isDebug;
    ZRefreshLayout.PullListener mPullListener;
    ZRefreshLayout.LoadMoreListener mLoadMoreListener;
    int delay_millis_auto_complete = -1;

    private Config() {
    }

    public static Config build() {
        return new Config();
    }


    public Config setHeader(IHeaderView headerView) {
        this.headerView = headerView;
        return this;
    }


    public Config setFooter(IFooterView footerView) {
        this.footerView = footerView;
        return this;
    }

    public Config setSpringback(boolean springback) {
        this.isSpringback = springback;
        return this;
    }

    public Config setResistance(IResistance resistance) {
        this.resistance = resistance;
        return this;
    }

    public Config setPinHeader(boolean pinContent) {
        this.isPinHeader = pinContent;
        return this;
    }

    public Config setPinFooter(boolean pinFooter) {
        this.isPinFooter = pinFooter;
        return this;
    }

    public Config setLoadMoreListener(ZRefreshLayout.LoadMoreListener mLoadMoreListener) {
        this.mLoadMoreListener = mLoadMoreListener;
        return this;
    }

    public Config setPullListener(ZRefreshLayout.PullListener mPullListener) {
        this.mPullListener = mPullListener;
        return this;
    }

    public Config writeLog(boolean writeLog) {
        this.isDebug = writeLog;
        return this;
    }

    public Config setDelayAutoCompleteTime(int delayAutoComplete) {
        this.delay_millis_auto_complete = delayAutoComplete;
        return this;
    }


    public void perform() {
        ZRefreshLayout.config = this;
    }

}
