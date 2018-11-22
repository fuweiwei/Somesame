package com.somesame.somesame.ui.book;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.model.BookModel;
import com.somesame.somesame.ui.common.ShareDialog;
import com.somesame.somesame.widget.FrameLayout4Loading;

import butterknife.BindView;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/7/2
 */
@Route(path= ActivityContracts.ACTIVITY_BOOK)
public class BookActivity extends BaseActivity<BookPresenter> implements BookContract.View{
    @BindView(R.id.loading)
    FrameLayout4Loading mFrameLayout4Loading;

    @Override
    public void setBook(BookModel model) {

    }

    @Override
    protected void initView() {
        mBaseToolBar.setToolbarTitle("文章详情");
        mBaseToolBar.setToolbarImgRight(R.mipmap.ic_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog dialog = new ShareDialog();
                dialog.showAllowingStateLoss(mContext);
            }
        });
    }

    @Override
    protected  BookPresenter initPresenter() {
        return new BookPresenter();
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_book;
    }
}