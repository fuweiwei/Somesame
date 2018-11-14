package com.somesame.framework.widget.refresh.loadmore;

import android.view.View;

import com.somesame.framework.widget.refresh.ZRefreshLayout;


/**
 * [2017] by Zone
 */

public interface LoadMoreOtherListener {

    void addListener(View view, ZRefreshLayout zRefreshLayout);
    void removeListener(View view);
    boolean haveListener();
    boolean instanceOf(View view);
    LoadMoreOtherListener clone_();
}
