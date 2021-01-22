package com.company.myijkplayer.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Nurmemet on 2020/1/2.
 * Email:nur01@qq.com
 * 简述:https://www.jianshu.com/u/27a084d3e8ce
 */

public class NurPlayButton extends View {

    private Paint mPaint;
    private boolean isPlay = false;
    private int status = 0;//1正在改变播放,2正在改变暂停
    private int xValue = 0;
    private OnClickListener onClickListener;

    public NurPlayButton(Context context) {
        super(context);
        init();
    }

    public NurPlayButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NurPlayButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
    }


    public void change(boolean changePlayStyle) {
        if (this.isPlay == changePlayStyle) {
            return;
        }
        this.isPlay = changePlayStyle;
        startAnim(changePlayStyle);
    }

    private void startAnim(boolean isPlay) {
        if (isPlay) status = 1;
        else status = 2;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = height / 10;
        mPaint.setStrokeWidth(width);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        int center = height / 2;
        int startY = (int) (center / 2.5);
        int startX = height / 6;


        int _xValue = width / 4;
        if (status == 1) {//改变播放
            isPlay = true;
            xValue -= _xValue;
        } else if (status == 2) {//改变暂停
            isPlay = false;
            xValue += _xValue;
        }

        int rx = center + startX;
        int lx = center - startX;
        int rxValue = rx + xValue;
        canvas.drawLines(new float[]{lx, center - startY, lx, center + startY}, mPaint);

        if (rxValue >= rx) {
            rxValue = rx;
        }

        canvas.drawLines(new float[]{
                rxValue, center - startY, rx, center,
                rx, center, rxValue, center + startY,
        }, mPaint);


        if (isPlay) {
            if (rxValue - _xValue > lx) {
                invalidate();
            } else {
                status = 0;
            }
        } else {
            if (rxValue < rx) {
                invalidate();
            } else {
                status = 0;
            }
        }
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        onClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onClickListener.onClick(this);
        }
        return true;
    }


}
