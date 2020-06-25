package com.demo.customview.anim;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * 贝塞尔曲线（二阶抛物线）
 * controllPoint 是中间的转折点
 * startValue 是起始的位置
 * endValue 是结束的位置
 */
public class BizierEvaluator implements TypeEvaluator<Point> {
    private Point controllPoint;
    public BizierEvaluator(Point controllPoint) {
        this.controllPoint = controllPoint;
    }
    /**
     *这个方法会在执行动画时, 一直设置控制运动轨迹
     *二次方贝兹曲线的路径由给定点起始点P0、控制点P1、结束点P2的函数B（t）追踪：
     *公式: B(t) = (1-t)²P0 + 2t(1-t)P1 + t²P2 , 取值范围: 0<= t <=1
     */
    @Override
    public Point evaluate(float t, Point startValue, Point endValue) {
        Log.e("-----------TAG", "t: "+ t+"----startValue: "+ startValue.x+"----endValue: "+ endValue.x);
        int x = (int) ((1 - t) * (1 - t) * startValue.x + 2 * t * (1 - t) * controllPoint.x + t * t * endValue.x);
        int y = (int) ((1 - t) * (1 - t) * startValue.y + 2 * t * (1 - t) * controllPoint.y + t * t * endValue.y);
        return new Point(x, y);
    }
}