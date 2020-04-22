package com.demo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 可以控制速度,暂停,开始,滚到方向的跑马灯
 */

public class MarqueeTextView2 extends androidx.appcompat.widget.AppCompatTextView implements Runnable {

    private static final int NORMAL = 15;
    private int currentScrollX;// 当前滚动的位置

    private boolean isStop = false;

    private int textWidth;

    private boolean isMeasure = false;
    private boolean toLeft = true;
    private int speedMulriple = NORMAL;

    public MarqueeTextView2(Context context) {

        super(context);
    }

    public MarqueeTextView2(Context context, AttributeSet attrs) {

        super(context, attrs);

    }

    public MarqueeTextView2(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (!isMeasure) {// 文字宽度只需获取一次就可以了
            getTextWidth();
            isMeasure = true;
        }
    }

    /**
     * 获取文字宽度
     */

    private void getTextWidth() {

        Paint paint = this.getPaint();

        String str = this.getText().toString();

        textWidth = (int) paint.measureText(str);

    }


    @Override
    public void run() {
        if (isStop) {
            return;
        }

        if (toLeft) {
            currentScrollX += 1;// 滚动方向-,右-->左
            scrollTo(currentScrollX, 0);
            if (getScrollX() >= textWidth) {
                scrollTo(-getWidth(), 0);
                currentScrollX = -getWidth();
            }
        } else {
            currentScrollX -= 1;// 滚动方向-,左-->右
            scrollTo(currentScrollX, 0);
            if (getScrollX() <= -(this.getWidth())) {
                scrollTo(textWidth, 0);
                currentScrollX = textWidth;
            }
        }

        if (getScrollX() == 0) {  //滚到初始位置时停顿 1秒
            postDelayed(this, 1000);// 滚动速度
            return;
        }
//        Log.e("TAG", "run: " + getScrollX());
        postDelayed(this, speedMulriple);// 滚动速度

    }

// 开始滚动

    public void startScroll() {

        isStop = false;

        this.removeCallbacks(this);

        postDelayed(this, 1000);

    }

// 停止滚动

    public void stopScroll() {

        isStop = true;

    }

// 从头开始滚动

    public void startFor0() {

        currentScrollX = 0;

        startScroll();

    }


    /**
     * 设置滚动方向,默认往左
     */
    public void setToLeft(boolean toLeft) {
        this.toLeft = toLeft;

    }

    /**
     * 正常速度的倍数
     */
    public void setSpeedMulriple(float mulriple) {
        mulriple = 1 / mulriple;
        speedMulriple = (int) (NORMAL * mulriple);

    }

}