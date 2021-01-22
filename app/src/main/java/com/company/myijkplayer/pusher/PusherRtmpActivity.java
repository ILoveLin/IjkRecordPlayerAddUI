package com.company.myijkplayer.pusher;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.company.myijkplayer.R;
import com.pedro.rtplibrary.rtmp.RtmpCamera3;

import net.ossrs.rtmp.ConnectCheckerRtmp;

/**
 * 推流是借鉴https://github.com/printlybyte/AndroidPCMtoAAC_RTMP_RTSP  作者集成的这里声明一下
 * <p>
 * 这个是使用RTMP 推流音频的案例
 * Describe Rtmp推流 音频(只有音频噢~),到中转服务器,提供给拉流端,拉取,从而实现,直播的同时,实现语音对话功能
 * 具体使用就几行代码，可以移植到直播播放器里面去实现，通话功能这里我没集成，但是   PusherRtmpActivity  是可以实现rtmp推流的，经过测试得出的结论！
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PusherRtmpActivity extends AppCompatActivity implements ConnectCheckerRtmp, View.OnClickListener {
    private RtmpCamera3 rtmpCamera3;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        button = findViewById(R.id.b_start_stop);
        button.setOnClickListener(this);
        rtmpCamera3 = new RtmpCamera3(this);

    }

    private void startConnect() {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                rtmpCamera3.startStream(ConstantUtils.DEAUFUTRTMPURL);
            }
        });
    }

    @Override
    public void onConnectionSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtmpActivity.this, "Connection success", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public void onConnectionFailedRtmp(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtmpActivity.this, "Connection failed. " + reason,
                        Toast.LENGTH_SHORT).show();
                rtmpCamera3.stopStream();
                button.setText("开始推送");
            }
        });
    }

    @Override
    public void onDisconnectRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtmpActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthErrorRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtmpActivity.this, "Auth error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthSuccessRtmp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PusherRtmpActivity.this, "Auth success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_start_stop:
                Log.e("TAG", "rtmpCamera3.isStreaming()=====" + rtmpCamera3.isStreaming());
                if (!rtmpCamera3.isStreaming()) {
                    if (rtmpCamera3.prepareAudio()) {
                        button.setText("停止推送");
                        rtmpCamera3.startStream(ConstantUtils.DEAUFUTRTMPURL);
                    } else {
                        Toast.makeText(this, "Error preparing stream, This device cant do it",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    button.setText("开始推送");
                    rtmpCamera3.stopStream();
                }
                break;
        }
    }


}
