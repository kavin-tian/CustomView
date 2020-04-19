package com.demo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆环进度条
 */
public class CircleProgressBar extends View {
    private Paint paint = new Paint();
    private float circleWidth;
    private int circleProgressColor;
    private int circleColor;
    private int progress;
    private int textColor;
    private float textSize;
    private int max = 100;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        //圆环的颜色, 默认取是红色
        circleColor = mTypedArray.getColor(R.styleable.CircleProgress_circleColor, Color.RED);
        //圆环进度的颜色
        circleProgressColor = mTypedArray.getColor(R.styleable.CircleProgress_circleProgressColor, Color.GREEN);
        //中间进度百分比文字字符串的颜色
        textColor = mTypedArray.getColor(R.styleable.CircleProgress_textColor, Color.GREEN);
        //中间进度百分比的字符串的字体大小
        textSize = mTypedArray.getDimension(R.styleable.CircleProgress_textSize, 15);
        //圆环的宽度
        circleWidth = mTypedArray.getDimension(R.styleable.CircleProgress_circleWidth, 5);
        //进度条进度
        progress = mTypedArray.getInteger(R.styleable.CircleProgress_progress, 50);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //第1步：绘制一个最外层的圆圈
        paint.setAntiAlias(true);//设置抗锯齿,轮廓变得圆滑
        paint.setColor(circleColor);//设置颜色
        paint.setStrokeWidth(circleWidth);//设置圆圈,内圈与外圈之间的宽度
        // paint.setStyle(Paint.Style.FILL);//设置为实心圆
        paint.setStyle(Paint.Style.STROKE);//设置为空心圆
        float center = getWidth() / 2;//圆的圆心坐标x=y=控件宽度的一半
        int radius = (int) (center - circleWidth / 2); //因为设置的厚度往内外都扩张
        //开始画圆圈,cx,xy圆心坐标,raduis半径,paint画笔
        canvas.drawCircle(center, center, radius, paint);

        //第2步：绘制一个进度圆弧
        /**
         * 参数解释：
         * oval：绘制弧形圈所包含的矩形范围轮廓
         * 0：开始的角度
         * 360 * progress / max：扫描过的角度
         * false：是否包含圆心
         * paint：绘制弧形时候的画笔
         */
        //矩形内切圆的4个切点
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        paint.setColor(circleProgressColor);
        paint.setStrokeWidth(circleWidth);
        paint.setStyle(Paint.Style.STROKE);
        //开始画弧形
        canvas.drawArc(oval, 0, 360 * progress / max, false, paint);


        //第3步：绘制正中间的文本
        float textWidth = paint.measureText(progress + "%");
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        canvas.drawText(progress + "%", center - textWidth / 2, center + textSize / 2, paint);

    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (progress > 100) {
            this.progress = 100;
        }
        /*
         *invalidate和postInvalidate都是用于进行View的刷新,onDraw方法就会被回调，invalidate方法应用在UI线程中，
         * 而postInvalidate方法应用在非UI线程中，用于将线程切换到UI线程，postInvalidate方法最后调用的也是invalidate方法。
         */
        postInvalidate();
    }
}
