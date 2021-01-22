package com.company.myijkplayer.player;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Muradil on 2019/12/25.
 */

public class NurOnTouch implements View.OnTouchListener {

    private final NurTouchListener nurTouchListner;
    private final float density;
    private float downY;
    private long mCurTime = 0;


    private int moveType = 0;

    public static int changeTypeVolume = 1;
    public static int changeTypeLiangdu = 2;
    public static int changeTypeVideoSeek = 3;
    private int changeType = 0;


    NurOnTouch(Context context, NurTouchListener nurTouchListner) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        density = dm.density;
        this.nurTouchListner = nurTouchListner;
    }

    private float downX;

    private boolean isMove = false;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int centerX = view.getWidth() / 2;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = motionEvent.getX();
                float moveY = motionEvent.getY();
                float maxX = getMax(downX, moveX);
                float maxY = getMax(downY, moveY);
                float minY = getMin(downY, moveY);
                float minX = getMin(downX, moveX);


                float _x = maxX - minX;
                float _y = maxY - minY;

                float minMoveValue = 5 * density;
                if (_x > minMoveValue || _y > minMoveValue) {
                    isMove = true;

                    int moveTypeVertical = 2;
                    if (moveType == 0) {
                        int moveTypeHorizontal = 1;
                        if (_x > _y)
                            moveType = moveTypeHorizontal;
                        else moveType = moveTypeVertical;
                    }
                    if (moveType == moveTypeVertical) {
                        float y = downY - moveY;
                        if (centerX < downX) {
                            changeType = changeTypeVolume;
                            nurTouchListner.onMoveLeft(y / density);
                        } else {
                            changeType = changeTypeLiangdu;
                            nurTouchListner.onMoveRight(y / density);
                        }
                    } else {
                        changeType = changeTypeVideoSeek;
                        nurTouchListner.onMoveSeek((moveX - downX) / density);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isMove) {
                    getClick();
                }
                nurTouchListner.onActionUp(changeType);
                moveType = 0;
                changeType = 0;
                break;
        }
        return true;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://单击
                    nurTouchListner.onClick();
                    break;
                case 2://双击
                    nurTouchListner.onDoubleClick();
                    break;
            }
        }
    };

    /**
     * 双击/单击
     */
    private void getClick() {
        long mLastTime = mCurTime;
        mCurTime = System.currentTimeMillis();
        if (mCurTime - mLastTime < 300) {//双击事件
            mCurTime = 0;
            mLastTime = 0;
            handler.removeMessages(1);
            handler.sendEmptyMessage(2);
        } else {//单击事件
            handler.sendEmptyMessageDelayed(1, 310);
        }

    }


    private float getMax(float a, float b) {
        return a > b ? a : b;
    }

    private float getMin(float a, float b) {
        return a > b ? b : a;
    }

    interface NurTouchListener {
        void onClick();

        void onDoubleClick();

        void onMoveSeek(float f);

        void onMoveLeft(float f);

        void onMoveRight(float f);

        void onActionUp(int changeType);

    }
}
