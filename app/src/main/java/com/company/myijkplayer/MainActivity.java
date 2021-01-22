package com.company.myijkplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.myijkplayer.bean.SwitchVideoModel;
import com.company.myijkplayer.listener.OnControlClickListener;
import com.company.myijkplayer.listener.OnMediaListener;
import com.company.myijkplayer.player.NurVideoView;
import com.company.myijkplayer.player.SwitchVideoTypeDialog;
import com.company.myijkplayer.utils.SharePreferenceUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NurVideoView nurVideoPlayer;
    private ImageView mRecordView, mKTVView;
    private List<SwitchVideoModel> mUrlList = new ArrayList<>();
    private int mSourcePosition = 0;
    private String mTypeText = "高清";
    private String currentUrl;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(MainActivity.this, "切换url", Toast.LENGTH_SHORT).show();
                    showSwitchDialog();
                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "截图成功", Toast.LENGTH_SHORT).show();

                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "开始录像", Toast.LENGTH_SHORT).show();
                    mRecordView.setImageResource(R.mipmap.record_pressed);
                    break;
                case 3:
                    Toast.makeText(MainActivity.this, "结束录像", Toast.LENGTH_SHORT).show();
                    mRecordView.setImageResource(R.mipmap.record);

                    break;
                case 4:     //打开麦克风，推流
                    Toast.makeText(MainActivity.this, "打开，语音直播", Toast.LENGTH_SHORT).show();
                    mKTVView.setImageResource(R.mipmap.icon_mic_pre);

                    break;
                case 5:     //关闭麦克风，关闭推流
                    Toast.makeText(MainActivity.this, "关闭，语音直播", Toast.LENGTH_SHORT).show();
                    mKTVView.setImageResource(R.mipmap.icon_mic_nor);
                    break;
                case 6:
                    Toast.makeText(MainActivity.this, "播放错误,请重新进入界面", Toast.LENGTH_SHORT).show();

                    break;
                case 7:
                    if (mSourcePosition == 0) {
                        nurVideoPlayer.setUp(MainActivity.this, currentUrl, mTypeText + "");
                    } else {
                        nurVideoPlayer.setUp(MainActivity.this, currentUrl, mTypeText + "");
                    }
                    break;
                case 8:
                    if (mSourcePosition == 0) {
                        nurVideoPlayer.setUp(MainActivity.this, currentUrl, mTypeText + "");
                    } else {
                        nurVideoPlayer.setUp(MainActivity.this, currentUrl, mTypeText + "");
                    }
                    if (null != mRecordView) {
                        mRecordView.setImageResource(R.mipmap.record);
                    }

                    break;
            }
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "rtmp://58.200.131.2:1935/livetv/jxhd";
//        String url = "rtsp://root:root@192.168.66.42:7788/session0.mpg";
//        String url = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        nurVideoPlayer = findViewById(R.id.videoView);
        nurVideoPlayer.setUp(MainActivity.this, url, "我是标题:默认是江西卫视电视台直播");
        requestPermission();
        initData();
        responseListener();

    }

    private void responseListener() {
        nurVideoPlayer.setOnControlClickListener(new OnControlClickListener() {
            @Override
            public void onBackBtnClick() {
                finish();
            }

            @Override
            public void onVolumeControlClick() {

            }

            @Override
            public void onScreenControlClick() {

            }
        });
        nurVideoPlayer.setOnMediaListener(new OnMediaListener() {
            @Override
            public void onStart() {
                Log.e("TAG", "===OnMedia===" + "onStart");

            }

            @Override
            public void onPause() {
                Log.e("TAG", "===OnMedia===" + "onPause");

            }

            @Override
            public void onProgress(int progress, int duration) {
//                Log.e("TAG", "===OnMedia===" + "onProgress");   //类似于loading

            }

            @Override
            public void onChangeScreen(boolean isPortrait) {
                Log.e("TAG", "===OnMedia===" + "onChangeScreen");

            }

            @Override
            public void onEndPlay() {
                Log.e("TAG", "===OnMedia===" + "onEndPlay");

            }

            @Override
            public void openEasyPusher(ImageView mTView, String statue) {
                Log.e("TAG", "===openEasyPusher===" + statue);
                mKTVView = mTView;
                mHandler.sendEmptyMessage(4);
            }

            @Override
            public void closeEasyPusher(ImageView mTView, String statue) {
                Log.e("TAG", "===closeEasyPusher===" + statue);
                mKTVView = mTView;
                mHandler.sendEmptyMessage(5);
            }

            @Override
            public void onError() {
//                Toast.makeText(mContext, "网路未连接，请检查网络设置", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "===OnMedia=onError==" + "网路未连接，请检查网络设置");
                mHandler.sendEmptyMessage(6);

            }

            @Override
            public void goToPictures() {
                Log.e("TAG", "===OnMedia===" + "goToPictures");
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(intent);
            }


            @Override
            public void switchUrlType(ImageView mTView) {
                Log.e("TAG", "===OnMedia===" + "switchUrlType");
                mHandler.sendEmptyMessage(0);

            }

            @Override
            public void getFrame(boolean currentFrame) {
                Log.e("TAG", "===OnMedia===" + "getFrame");
                mHandler.sendEmptyMessage(1);

            }

            @Override
            public void getStartRecord(ImageView mTView, String statue) {
                mRecordView = mTView;
                Log.e("TAG", "===OnMedia===" + "getStartRecord" + "=====" + statue);
                mHandler.sendEmptyMessage(2);

            }

            @Override
            public void getEndRecord(ImageView mTView, String statue, String comefrom) {
                mRecordView = mTView;
                mRecordView = mTView;
                Log.e("TAG", "===OnMedia===" + "getEndRecord" + "=====" + statue);
                Log.e("TAG", "===OnMedia===" + "getEndRecord" + "=====" + comefrom);
                if ("onReceive".equals(comefrom)) {
                    mHandler.sendEmptyMessage(7);
                } else if ("正常切换url".equals(comefrom)) {
                    mHandler.sendEmptyMessage(8);
                } else {
                    mHandler.sendEmptyMessage(3);

                }
            }

            @Override
            public void mVideoViewOK() {
                Log.e("TAG", "===OnMedia===" + "mVideoViewOK");

            }
        });
    }


    private void initData() {
        String name = "高清";
        String name2 = "标清";
        //public static final String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
//    public static final String path = "rtmp://58.200.131.2:1935/livetv/jxhd";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, "rtmp://58.200.131.2:1935/livetv/jxhd");   //0
        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"); //1
//        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, "rtmp://58.200.131.2:1935/livetv/jxtv"); //1
//        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, "http://mov.bn.netease.com/open-movie/nos/mp4/2020/04/07/SF8M8LRLN_sd.mp4"); //1
//        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, url01);
//        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, url02);
        mUrlList = new ArrayList<>();
        mUrlList.add(switchVideoModel);
        mUrlList.add(switchVideoModel2);
        mSourcePosition = 0;  //标清

    }

    /**
     * 弹出切换清晰度
     */
    private void showSwitchDialog() {
        SwitchVideoTypeDialog switchVideoTypeDialog = new SwitchVideoTypeDialog(this);
        switchVideoTypeDialog.initList(mUrlList, new SwitchVideoTypeDialog.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final String name = mUrlList.get(position).getName();
                Log.e("path=====Start:=====", "切换清晰度show======position======" + position);
//                Log.e("path=====Start:=====", "切换清晰度show======mTypeText ======" + mTypeText + "");
//                Log.e("path=====Start:=====", "我是当前播放的url======path======" + mUrlList.get(position).getUrl());

                if (mSourcePosition != position) {
                    mTypeText = name;
                    mSourcePosition = position;

                    //不为null并且确实开启了录像
                    if (null != mRecordView && "end".equals(mRecordView.getTag())) {
                        Intent intent = new Intent();
                        intent.putExtra("FLAGE_MY_BROADCAST", "onReceive");
                        intent.setAction("ACTION_NAME_SERACE");
                        sendBroadcast(intent);
                        Log.e("path=====Start:=====", "服务开始,发送广播了哦===结束录像===" + mRecordView.getTag());
                        //如果在录象,发送广播,此时, 结束录像的回调s=onReceive 发送消息7
                    }
                    currentUrl = mUrlList.get(position).getUrl();
//                    Toast.makeText(IjkPlayerTestActivity.this, "录像成功", Toast.LENGTH_SHORT).show();
//                    nurVideoPlayer.setUp(IjkPlayerTestActivity.this,currentUrl, name+"");
                    mHandler.sendEmptyMessage(8);   //切换url成功

//                    Toast.makeText(IjkPlayerTestActivity.this, "" + name, Toast.LENGTH_SHORT).show();
//                    Log.e("path=====Start:=====", "切换清晰度show=====切换=url======" + mUrlList.get(position).getUrl());
                } else {
                    Toast.makeText(MainActivity.this, "已经是 " + name, Toast.LENGTH_LONG).show();
                }
            }
        });
//        Log.e("path=====Start:=====", "切换清晰度show"); //   /storage/emulated/0/1604026573438.mp4
        switchVideoTypeDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (nurVideoPlayer.getIsFullScreen()) {
            nurVideoPlayer.setChangeScreen(false);
        } else
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        nurVideoPlayer.pause();
        String recordType = (String) SharePreferenceUtil.get(this, SharePreferenceUtil.Key_Record, "end");
        if (recordType.equals("start")) {
            nurVideoPlayer.getEndRecord();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        nurVideoPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nurVideoPlayer.stopPlay();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean b = nurVideoPlayer.onKeyDown(keyCode);
        return b || super.onKeyDown(keyCode, event);
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        XXPermissions.with(this)
                // 不适配 Android 11 可以这样写
                //.permission(Permission.Group.STORAGE)
                // 适配 Android 11 需要这样写，这里无需再写 Permission.Group.STORAGE
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            Toast.makeText(MainActivity.this, "获取存储权限成功", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            Toast.makeText(MainActivity.this, "被永久拒绝授权，请手动授予存储权限", Toast.LENGTH_SHORT).show();

                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                            Toast.makeText(MainActivity.this, "获取存储权限失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

}
