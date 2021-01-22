package com.company.myijkplayer.player;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.company.myijkplayer.R;
import com.company.myijkplayer.listener.OnControlClickListener;
import com.company.myijkplayer.listener.OnMediaListener;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by Nurmemet on 2020/4/9
 * Email: nur01@qq.com
 * qq:643229571
 * <p>
 * ijkPlayer的封装-（支持http和https-支持屏幕滑动--滑动时间,亮度,声音,支持全屏-单屏,双击暂停--继续,锁定屏幕-可以设置声道-单声道播放（ktv的风格））
 * 简述：https://www.jianshu.com/p/9e3319b2c2fb
 */
public class NurVideoView extends LinearLayout implements View.OnClickListener {

    private NurVideoPlayer mVideoView;
    private int mVideoViewHeight;
    private int bgColor;
    private boolean removeBack;
    private ImageView mScreenView;
    private final int ID_SCREEN_VIEW = 1010;
    private final int ID_VOLUME_CONTROL = 2020;
    private final int ID_BACK_IV = 3030;
    private boolean isPortrait = true;//是否全屏
    private Activity mActivity;
    private ImageView mVolumeControl;
    private OnControlClickListener onControlClickListener;
    private OnMediaListener mediaListener;

    public NurVideoView(Context context) {
        this(context, null);
    }

    public NurVideoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NurVideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NurVideoView);
            mVideoViewHeight = (int) ta.getDimension(R.styleable.NurVideoView_video_view_height, ViewGroup.LayoutParams.MATCH_PARENT);
            bgColor = ta.getColor(R.styleable.NurVideoView_video_background_color, Color.BLACK);
            removeBack = ta.getBoolean(R.styleable.NurVideoView_remove_back_btn, false);
            ta.recycle();
        }
        setOrientation(VERTICAL);
        initVideoView();
    }

    /**
     * 引入播放器
     */
    private void initVideoView() {
        mVideoView = new NurVideoPlayer(getContext());
        mVideoView.setBgColor(bgColor);
        setVideoViewHeight(mVideoViewHeight);
        addView(mVideoView);
        mScreenView = mVideoView.getScreenView();
        mVolumeControl = mVideoView.getVolumeImageView();
        View mBackIv = mVideoView.getBackIv();
        mBackIv.setOnClickListener(this);
        mBackIv.setId(ID_BACK_IV);
        if (removeBack) {
            mBackIv.setVisibility(GONE);
        }

        mVolumeControl.setOnClickListener(this);
        mVolumeControl.setId(ID_VOLUME_CONTROL);
        mScreenView.setOnClickListener(this);
        mScreenView.setId(ID_SCREEN_VIEW);
    }

    public void setVideoTransitionName(String transitionName) {
        mVideoView.setTransitionName(transitionName);
    }

    /**
     * 设置高度
     */
    private void setVideoViewHeight(int h) {
        mVideoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, h));
    }


    /**
     * 初始化播放器
     */
    public void setUp(Activity activity, String rul) {
        setUp(activity, Uri.parse(rul), null);
    }

    /**
     * 初始化播放器
     */
    public void setUp(Activity activity, String rul, String title) {
        setUp(activity, Uri.parse(rul), title);
    }

    /**
     * 初始化播放器
     */
    public void setUp(Activity activity, Uri uri, String title) {
        this.mActivity = activity;
        mVideoView.initPlayer(activity, uri, title);
    }


    /**
     * 监听（播放，暂停）
     */
    public void setOnMediaListener(OnMediaListener mediaListener) {
        this.mediaListener = mediaListener;
        mVideoView.setOnMediaListener(mediaListener);
    }

    /**
     * 截图
     */
    public void getFrame() {
        mVideoView.getFramePicture();
    }

    /**
     * 开始录像
     */
    public void getStartRecord() {
        mVideoView.getStartRecord();
    }

    /**
     * 结束录像
     */
    public void getEndRecord() {
        mVideoView.getEndRecord("");
    }

    /**
     * 跳转相册
     */
    public void goToPictures() {
        mVideoView.getEndRecord("");
    }
    /**
     * 切换清晰度
     */
    public void switchUrlType() {
        mVideoView.getEndRecord("");
    }




    /**
     * 开始播放
     */
    public void start(int msec) {
        mVideoView.start(msec);
    }

    /**
     * 开始播放
     */
    public void start() {
        mVideoView.start();
    }

    /**
     * 暂停播放
     */
    public void pause() {
        _isPause = true;
        mVideoView.pause();
    }

    private boolean _isPause = false;

    /**
     * 继续播放
     */
    public void resume() {
        if (_isPause) {
            mVideoView.start();
            _isPause = false;
        }
    }

    /**
     * 禁止播放
     */
    public void stopPlay() {
        mVideoView.stopPlayback();
    }

    /**
     * 小广告view
     */
    public void setSmallADView(View view) {
        mVideoView.setMinADLayout(view);
    }

    /**
     * 获取广告view（小）
     */
    public RelativeLayout getSmallADView() {
        return mVideoView.getMinADLayout();
    }

    /**
     * 满（就是播放器的上面）-广告view
     */
    public void setMaxADView(View view) {
        mVideoView.setMaxADLayout(view);
    }

    /**
     * volume image view
     */
    public ImageView getVolumeImageView() {
        return mVideoView.getVolumeImageView();
    }

    /**
     * 获取背景image view
     */
    public ImageView getThumbImageView() {
        return mVideoView.getThumbImageView();
    }

    /**
     * 获取广告view（大）
     */
    public RelativeLayout getMaxADView() {
        return mVideoView.getMaxADLayout();
    }


    /**
     * @return true 正在是全屏
     */
    public boolean getIsFullScreen() {
        return !isPortrait;
    }

    /**
     * 声道控制器view
     */
    public void setVolumeControlImage(@DrawableRes int resId) {
        mVolumeControl.setVisibility(VISIBLE);
        mVolumeControl.setImageResource(resId);
    }

    /**
     * 声道控制器view
     * 默认情况下visibility=gone
     */
    public ImageView getVolumeControl() {
        return mVolumeControl;
    }

    /**
     * 关闭全部控制器
     */
    public void hideControls() {
        mVideoView.hideControllers();
    }


    /**
     * 获取右中边的icon btn
     */
    public ImageView getRightIcon() {
        return mVideoView.getRCImage();
    }

    /**
     * 打开全部控制器
     */
    public void showControllers() {
        mVideoView.showControllers();
    }

    /**
     * 全屏-单屏
     *
     * @param changeFull true 要更改全屏
     */
    public void setChangeScreen(boolean changeFull) {
        isPortrait = changeFull;
        changeScreen();
    }

    /**
     * 设置单声道
     *
     * @param isLeft == true 播放左声道
     */
    public void setMonoChannel(boolean isLeft) {
        setVolume(isLeft ? 1.0f : 0.0f, isLeft ? 0.0f : 1.0f);
    }

    /**
     * 设置声道
     */
    public void setVolume(float leftVolume, float rightVolume) {
        mVolumeControl.setVisibility(VISIBLE);
        IMediaPlayer mediaPlayer = mVideoView.getMediaPlayer();
        if (mediaPlayer == null) return;
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    /**
     * 控制器的点击事件
     */
    public void setOnControlClickListener(OnControlClickListener controlClickListener) {
        this.onControlClickListener = controlClickListener;
    }


    /**
     * 调声音
     */
    public boolean onKeyDown(int keyCode) {
        return mVideoView.onKeyDown(keyCode);
    }

    /**
     * 获取ijk player 的media player
     */
    public IMediaPlayer getMediaPlayer() {
        return mVideoView.getMediaPlayer();
    }


    /**
     * 更改全（单）屏
     */
    private void changeScreen() {
        int[] videoWH = mVideoView.getVideoWH();
        int videoW = videoWH[0];
        int videoH = videoWH[1];

        if (videoH <= 0 || videoW <= 0) {
            return;
        }

        if (videoH > videoW) {
            if (isPortrait) {
                setVideoViewHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                setVideoViewHeight(mVideoViewHeight);
            }
        } else {
            if (isPortrait) {
                setVideoViewHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);//横向
            } else {
                setVideoViewHeight(mVideoViewHeight);
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//纵行
            }
        }

        if (isPortrait) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mScreenView.setImageResource(R.mipmap.nur_ic_fangxiao);
        } else {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mScreenView.setImageResource(R.mipmap.nur_ic_fangda);
        }
        isPortrait = !isPortrait;
        if (mediaListener != null) {
            mediaListener.onChangeScreen(isPortrait);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == ID_SCREEN_VIEW) {
            changeScreen();
            if (onControlClickListener != null) {
                onControlClickListener.onScreenControlClick();
            }
        } else if (id == ID_VOLUME_CONTROL) {
            if (onControlClickListener != null) {
                onControlClickListener.onVolumeControlClick();
            }
        } else if (id == ID_BACK_IV) {
            if (onControlClickListener != null) {
                onControlClickListener.onBackBtnClick();
            }
        }
    }

}
