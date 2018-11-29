package com.somesame.somesame.ui.book;


import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.somesame.framework.widget.ijkplayer.VideoPlayerIJK;
import com.somesame.somesame.R;
import com.somesame.somesame.base.BaseActivity;
import com.somesame.somesame.common.ActivityContracts;
import com.somesame.somesame.model.BookModel;
import com.somesame.somesame.ui.common.ShareDialog;
import com.somesame.somesame.widget.FrameLayout4Loading;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

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
    @BindView(R.id.video)
    VideoPlayerIJK videoPlayerIJK;

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
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        videoPlayerIJK.setListener(new VideoPlayerIJK.VideoPlayerListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

            }

            @Override
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {

            }

            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                iMediaPlayer.start();
            }

            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }

            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }

            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {

            }

            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

            }
        });
        videoPlayerIJK.setVideoPath("https://oimryzjfe.qnssl.com/content/1F3D7F815F2C6870FB512B8CA2C3D2C1.mp4");

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