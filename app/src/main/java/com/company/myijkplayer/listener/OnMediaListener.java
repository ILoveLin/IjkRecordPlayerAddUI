package com.company.myijkplayer.listener;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 监听播放进度
 */
public interface OnMediaListener {

    /**
     * 开发播放
     */
    void onStart();

    /**
     * 暂停播放
     */
    void onPause();


    /**
     * 播放进度
     *
     * @param progress // 正在播放到哪儿了
     * @param duration // 视频总长都
     */
    void onProgress(int progress, int duration);

    /**
     * 更改全（单）屏
     */
    void onChangeScreen(boolean isPortrait);

    /**
     * 播放完成
     */
    void onEndPlay();
    /**
     * 播放失败
     */
    void onError();

    /**
     * 跳转相册
     */
    void goToPictures();
    /**
     * 开麦说话,推流
     */
    void openEasyPusher(ImageView mTView,String statue);
    /**
     * 闭麦,关闭
     */
    void closeEasyPusher(ImageView mTView,String statue);
    /**
     * 切换清晰度
     */

    void switchUrlType(ImageView mTView);


    void getFrame(boolean currentFrame);
    void getStartRecord(ImageView mTView,String statue);
    void getEndRecord(ImageView mTView,String statue,String comefrom);
    void mVideoViewOK();



}
