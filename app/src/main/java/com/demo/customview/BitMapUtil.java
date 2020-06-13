package com.demo.customview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Created by Administrator on 2015/12/19.
 */
public class BitMapUtil {

    /**
     * 图片缩放
     * wf.wh必须不能是int
     *
     * @param source
     * @param wf 指定的宽度
     * @param hf 指定的高度
     * @return
     */
    public static Bitmap zoom(Bitmap source, float wf, float hf) {
        Matrix matrix = new Matrix();
        float sx = wf / source.getWidth();
        float sy = hf / source.getHeight();
        matrix.postScale(sx, sy);//矩阵
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    /**
     * 头像圆形裁剪
     * @param source
     * @return
     */
    public static Bitmap circleBitMap(Bitmap source) {
        final Paint paint = new Paint();
        //抗锯齿效果
        int width = source.getWidth();
        paint.setAntiAlias(true);
        //指定大小bitmap
        Bitmap target = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //根据target生成一个画布
        Canvas canvas = new Canvas(target);
        //在画布上画了一个圆
        //参数CX,cy-->确定绘制圆的圆心点
        //半径参数
        //画笔
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        //这句话是关键:
        //分析：我们以一张图片作为画布，在上面画了一个圆-->画图展示-->"这时候,绘制的圆和图片本身就出现了一个圆形的交集图案"
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有哪几种
        //PorterDuff.Mode.SRC_IN 取两层图像交集部门,只显示上层图像,注意这里是指取相交叉的部分,然后显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部门,只显示下层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //使用设置了setXfermode方案的paint绘制图像
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 头像圆形裁剪,根据两张图片进行叠加
     * @param pic 需要裁减的图片
     * @param shape 需要裁减形状
     * @return
     */
    public static Bitmap circleBitMap2(Bitmap pic,Bitmap shape) {
        final Paint paint = new Paint();
        //抗锯齿效果
        int width = pic.getWidth();
        paint.setAntiAlias(true);
        //指定大小bitmap
        Bitmap target = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //根据target生成一个画布
        Canvas canvas = new Canvas(target);

        canvas.drawBitmap(shape, 0, 0, paint);

        //PorterDuff.Mode.SRC_IN 取两层图像交集部门,只显示上层图像,注意这里是指取相交叉的部分,然后显示上层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //使用设置了setXfermode方案的paint绘制图像
        canvas.drawBitmap(pic, 0, 0, paint);
        return target;
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap drawableToBitamp2(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        return bitmap;
    }


    public static Drawable bitampTodrawable(Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
    }

    public static Drawable bitampTodrawable2(Resources resources, Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(resources, bitmap);
        return drawable;
    }


}
