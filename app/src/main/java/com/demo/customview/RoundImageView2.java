package com.demo.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * 首先绘制一个圆角的矩形,把图片和圆角矩形叠放一起,取交集即可
 */
public class RoundImageView2 extends androidx.appcompat.widget.AppCompatImageView {


    //圆角大小，默认为10
    private int mBorderRadius = 10;

    private Paint mPaint;

    public RoundImageView2(Context context) {
        this(context, null);
    }

    public RoundImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        //获取ImageView中的Drawable,并转换为bitmap
        Bitmap bitmap = drawableToBitamp(getDrawable());
        //对获取的原始尺寸bitmap进行当前ImageIvew的匹配缩放, 获取一个与当前ImageIvew宽高相同的bitmap
        Bitmap zoomBitmap = zoom(bitmap, getWidth(), getHeight());
        //获取一个圆角的bitmap
        Bitmap roundRectBitMap = roundRectBitMap(zoomBitmap);
        //把圆角的bitmap绘制到当前的ImageVeiw
        canvas.drawBitmap(roundRectBitMap, 0, 0, mPaint);
    }


    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


    /**缩放bitmap
     */
    public Bitmap zoom(Bitmap source, float wf, float hf) {
        Matrix matrix = new Matrix();
        float sx = wf / source.getWidth();
        float sy = hf / source.getHeight();
        matrix.postScale(sx, sy);//矩阵
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    /**
     * 获取圆角的bitmap
     */
    public Bitmap roundRectBitMap(Bitmap source) {
        final Paint paint = new Paint();
        //抗锯齿效果
        int width = source.getWidth();
        int height = source.getHeight();
        paint.setAntiAlias(true);
        //指定大小bitmap
        Bitmap target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //根据target生成一个画布
        Canvas canvas = new Canvas(target);
        //在画布上画了一个圆
        //参数CX,cy-->确定绘制圆的圆心点
        //半径参数
        //画笔
//        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        canvas.drawRoundRect(new RectF(0, 0, width, height), 10, 10, paint);
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
}