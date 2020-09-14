package com.demo.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MySlidingMenu extends ViewGroup {

    private View menuView;
    private View mainView;
    private int menuwidth;
    private int downX;

    /**
     * 保存上一次移动位置
     **/
    private int nextStartIndx = 0;
    private int distance;
    private Scroller scroller;
    private int downY;

    public MySlidingMenu(Context context) {
        // super(context);
        this(context, null);
    }

    public MySlidingMenu(Context context, AttributeSet attrs) {
        // super(context, attrs);
        this(context, attrs, -1);
    }

    public MySlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context);
    }

    // 测量宽高
    // 1.自定义控件
    // 2.首页控件
    // 3.菜单页控件
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1.自定义控件
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取子控件
        // 根据子控件的索引获取子控件，索引：布局文件从上往下，依次从0开始
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        // 2.首页控件
        // 除了可以设置规则之外也是可以设置宽高，如果子控件有宽高，可以直接作为参数设置
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
        // 3.菜单页的控件
        menuwidth = menuView.getLayoutParams().width;
        menuView.measure(menuwidth, heightMeasureSpec);
    }

    // l,t,r,b（相当于距离父控件的填充距离）相对于父控件的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 首页
        mainView.layout(l, t, r, b);
        // 菜单页
        menuView.layout(l - menuwidth, t, l, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 纠正scrollTo的正负问题
     */
    private void myScrollTo(int x) {
        // 传正值 -> 系统负值 传正值 -> 转化成负值 -> 系统正值
        scrollTo(-x, 0);
    }

    /**
     * 缓慢滑动效果
     * startx:开始的位置
     * tox : 结束的位置
     */
    private void scrollToTime(int startx, int tox) {
        // 获取移动的距离
        int dx = tox - startx;

        // 根据移动距离设置相应的事件
        // 获取每段的时间
        int scaleTime = 1000 / menuwidth;
        // 获取移动距离所花的时间
        int time = scaleTime * Math.abs(dx);

        // 将移动的距离拆分成一段一段
        // startX startY : 开始位置
        // dx dy : 移动的距离
        // duration : 持续的时间
        scroller.startScroll(startx, 0, dx, 0, time);
        invalidate(); //源码里会调用到下面computeScroll()
    }

    @Override
    public void computeScroll() {
        // 判断是否还有移动的小段
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();// 获取移动的小段
            myScrollTo(currX);
            invalidate(); //递归调用computeScroll()
        }
        super.computeScroll();
    }

    /**
     * 打开关闭侧拉菜单操作
     */
    public void toggle() {
        // 判断移动距离，如果是0，是关闭状态，执行打开，如果是菜单页的宽度，是打开的状态，执行关闭
        // 获取scrollTo移动距离，也是正负相反
        if (myGetScroll() == 0) {
            // 是关闭状态，执行打开
            distance = 0;
            nextStartIndx = menuwidth;
        } else {
            // 是打开的状态，执行关闭
            distance = menuwidth;
            nextStartIndx = 0;
        }
        scrollToTime(distance, nextStartIndx);

    }

    /**
     * 纠正getScroll方法正负的问题
     */
    private float myGetScroll() {
        return -getScrollX();
    }

    // 事件分发，用来判定是否将触摸事件分发给子控件，主要是判定是否分发事件，具体的判断操作有onInterceptTouchEvent实现
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return super.dispatchTouchEvent(ev);
    }

    // 是dispatchTouchEvent判断，具体判断操作有它来做
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 判断，如果是上下滑动，return false：不拦截传递给scrollview，如果左右滑动，return true：拦截事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int distanceX = moveX - downX;
                int distanceY = moveY - downY;
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    // 实现触摸事件的
    // 移动自定义侧拉菜单
    // 获取按下的x的坐标，在获取移动的x的坐标，让自定义侧拉菜单移动两个坐标之间的距离
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                // 获取移动的距离
                distance = nextStartIndx + moveX - downX;

                // 控制滑动的范围
                if (distance < 0) {
                    distance = 0;
                } else if (distance > menuwidth) {
                    distance = menuwidth;
                }
                // 自定义侧拉菜单移动相应的距离
                // scrollTo(x, y);//移动相应的距离，x和y：x轴和y轴的距离
                // scrollBy(x, y);//移动相应的距离，x和y：x轴和y轴的距离
                myScrollTo(distance);
                break;
            case MotionEvent.ACTION_UP:
                // 抬起鼠标的时候，保存上一次移动的距离，方便下一次移动的设置移动的起始位置
                // nextStartIndx = distance;

                // 自动滑动效果
                if (distance < menuwidth / 2) {
                    // myScrollTo(0);
                    nextStartIndx = 0;
                } else {
                    // myScrollTo(menuwidth);
                    nextStartIndx = menuwidth;
                }

                // 缓慢滑动
                scrollToTime(distance, nextStartIndx);
                break;
        }
        return true;
    }

}
