package com.demo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;

/*自定义圆边框的控件*/
public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final Xfermode xfermode;
    private Bitmap mBitmap;
    private Paint paint;
    private int mBorderWidth = 10;
    private int mBorderColor = Color.parseColor("#f2f2f2");

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
        xfermode = new PorterDuffXfermode(localMode);
    }

    public CircleImageView(Context paramContext) {
        super(paramContext);
    }

    public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public CircleImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);

        TypedArray a = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CircularImage);
        mBorderColor = a.getColor(R.styleable.CircularImage_border_color, mBorderColor);
        final int defalut = (int) (2 * paramContext.getResources().getDisplayMetrics().density + 0.5f);
        mBorderWidth = a.getDimensionPixelOffset(R.styleable.CircularImage_border_width, defalut);
        a.recycle();
    }



    @Override
    protected void onDraw(Canvas paramCanvas) {

        final Drawable imageDrawable = getDrawable();
        if (imageDrawable == null)
            return;
        if (imageDrawable instanceof NinePatchDrawable) {
            return;
        }
        if (this.paint == null) {
            final Paint localPaint = new Paint();
            localPaint.setFilterBitmap(false);
            localPaint.setAntiAlias(true);
            localPaint.setXfermode(xfermode);
            this.paint = localPaint;
        }
        final int width = getWidth();
        final int height = getHeight();
        /** 保存layer */
        int layer = paramCanvas.saveLayer(0.0F, 0.0F, width, height, null, 31);
        /** 设置drawable的大小 */
        imageDrawable.setBounds(0, 0, width, height);
        /** 将drawable绑定到bitmap(this.bitmap)上面（drawable只能通过bitmap显示出来） */
        imageDrawable.draw(paramCanvas);
        if ((mBitmap == null) || (mBitmap.isRecycled())) {
            mBitmap = createOvalBitmap(width, height);
        }
        /** 将bitmap画到canvas上面 */
        paramCanvas.drawBitmap(mBitmap, 0.0F, 0.0F, this.paint);
        /** 将画布复制到layer上 */
        paramCanvas.restoreToCount(layer);
        drawBorder(paramCanvas, width, height);
    }

    /**
     * 绘制最外面的边框
     */
    private void drawBorder(Canvas canvas, final int width, final int height) {
        if (mBorderWidth == 0) {
            return;
        }
        final Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        /**
         * 坐标x：view宽度的一般 坐标y：view高度的一般 半径r：因为是view的宽度-border的一半
         */
        canvas.drawCircle(width / 2, height / 2, (width - mBorderWidth) / 2,
                mBorderPaint);
        canvas = null;
    }

    /**
     * 获取一个bitmap，目的是用来承载drawable;
     * 将这个bitmap放在canvas上面承载，并在其上面画一个椭圆(其实也是一个圆，因为width=height)来固定显示区域
     */
    public Bitmap createOvalBitmap(final int width, final int height) {
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(width, height, localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        RectF localRectF = new RectF(mBorderWidth, mBorderWidth, width - mBorderWidth,
                height - mBorderWidth);
        localCanvas.drawOval(localRectF, localPaint);
        return localBitmap;
    }

}
