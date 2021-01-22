package com.company.myijkplayer.pusher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.company.myijkplayer.R;
import com.pedro.rtplibrary.rtsp.RtspCamera2;
import com.pedro.rtsp.utils.ConnectCheckerRtsp;

/**
 * LoveLin
 * <p>
 * 推流是借鉴https://github.com/printlybyte/AndroidPCMtoAAC_RTMP_RTSP  作者集成的这里声明一下
 * <p>
 * 这个是使用RTSP 推流音频的案例
 * Describe Rtsp推流 音频(只有音频噢~),到中转服务器,提供给拉流端,拉取,从而实现,直播的同时,实现语音对话功能
 * 具体使用就几行代码，可以移植到直播播放器里面去实现，通话功能这里我没集成，但是   PusherRtspActivity  是可以实现rtsp推流的，经过测试得出的结论！
 */
public class PusherRtspActivity extends AppCompatActivity implements View.OnClickListener, ConnectCheckerRtsp {
    private RtspCamera2 rtspCamera2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp_pusher);
        initView();
    }


    private void initView() {
        button = findViewById(R.id.b_start_stop);
        button.setOnClickListener(this);
//        new RtspCamera2(this);
        rtspCamera2 = new RtspCamera2(getApplicationContext(), this);

    }

    private void startConnect() {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                rtspCamera2.startStream(ConstantUtils.DEAUFUTRTSPURL);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_start_stop:
                if (!rtspCamera2.isStreaming()) {
                    if (rtspCamera2.prepareAudio()) {
                        button.setText("停止推送");
                        rtspCamera2.startStream(ConstantUtils.DEAUFUTRTSPURL);
                    } else {
                        Toast.makeText(this, "Error preparing stream, This device cant do it",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    button.setText("开始推送");
                    rtspCamera2.stopStream();
                }
                break;
        }
    }


    @Override
    public void onConnectionSuccessRtsp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtspActivity.this, "Connection 成功", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onConnectionFailedRtsp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG=====", "reason====" + reason);
                Toast.makeText(PusherRtspActivity.this, "Connection 失败. " + reason,
                        Toast.LENGTH_SHORT).show();
                rtspCamera2.stopStream();
                button.setText(R.string.start_button);
            }
        });
    }

    @Override
    public void onDisconnectRtsp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtspActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthErrorRtsp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtspActivity.this, "Auth error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthSuccessRtsp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtspActivity.this, "Auth success", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
