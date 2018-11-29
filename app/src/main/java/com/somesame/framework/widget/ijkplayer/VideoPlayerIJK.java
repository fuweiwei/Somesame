package com.somesame.framework.widget.ijkplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/11/29
 */
public class VideoPlayerIJK extends FrameLayout {
    /**
     * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
     */
    private IMediaPlayer mIMediaPlayer;
    /**
     * 视频文件地址
     */
    private String mPath = "";

    private SurfaceView mSurfaceView;
    private Context mContext;
    private VideoPlayerListener listener;


    public VideoPlayerIJK(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public VideoPlayerIJK(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public VideoPlayerIJK(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);

    }

    private void initVideoView(Context context){
        this.mContext = context;
        setFocusable(true);
    }
    /**
     * 设置视频地址。
     * 根据是否第一次播放视频，做不同的操作。
     *
     * @param path the path of the video.
     */
    public void setVideoPath(String path) {
        if (TextUtils.equals("", mPath)) {
            //如果是第一次播放视频，那就创建一个新的surfaceView
            mPath = path;
            createSurfaceView();
        } else {
            //否则就直接load
            mPath = path;
            load();
        }
    }

    /**
     * 新建一个surfaceview
     */
    private void createSurfaceView() {
        //生成一个新的surface view
        mSurfaceView = new SurfaceView(mContext);
        mSurfaceView.getHolder().addCallback(new LmnSurfaceCallback());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT
                , LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mSurfaceView.setLayoutParams(layoutParams);
        this.addView(mSurfaceView);
    }
    /**
     * surfaceView的监听器
     */
    private class LmnSurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //surfaceview创建成功后，加载视频
            load();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    }
    /**
     * 加载视频
     */
    private void load() {
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            mIMediaPlayer.setDataSource(mPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        mIMediaPlayer.setDisplay(mSurfaceView.getHolder());

        mIMediaPlayer.prepareAsync();
    }
    /**
     * 创建一个新的player
     */
    private void createPlayer() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.stop();
            mIMediaPlayer.setDisplay(null);
            mIMediaPlayer.release();
        }
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
         //开启硬解码  ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        mIMediaPlayer = ijkMediaPlayer;
        if (listener != null) {
            mIMediaPlayer.setOnPreparedListener(listener);
            mIMediaPlayer.setOnInfoListener(listener);
            mIMediaPlayer.setOnSeekCompleteListener(listener);
            mIMediaPlayer.setOnBufferingUpdateListener(listener);
            mIMediaPlayer.setOnErrorListener(listener);
        }
    }
    public void setListener(VideoPlayerListener listener) {
        this.listener = listener;
        if (mIMediaPlayer != null) {
            mIMediaPlayer.setOnPreparedListener(listener);
        }
    }

    /**
     * -------======--------- 下面封装了一下控制视频的方法
     */

    public void start() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.start();
        }
    }

    public void release() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.reset();
            mIMediaPlayer.release();
            mIMediaPlayer = null;
        }
    }

    public void pause() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.pause();
        }
    }

    public void stop() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.stop();
        }
    }


    public void reset() {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.reset();
        }
    }


    public long getDuration() {
        if (mIMediaPlayer != null) {
            return mIMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }


    public long getCurrentPosition() {
        if (mIMediaPlayer != null) {
            return mIMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }


    public void seekTo(long l) {
        if (mIMediaPlayer != null) {
            mIMediaPlayer.seekTo(l);
        }
    }

    public abstract static class VideoPlayerListener implements IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnSeekCompleteListener {

    }

}
