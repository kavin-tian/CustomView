package com.demo.customview;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

/**
 * 跑马灯效果的TextView, 使用方式：
 * 启动/关闭：{@link #setMarqueeEnable(boolean)}
 * xml文件中记得设置：android:focusable="true", android:singleLine="true"
 */

public class MarqueeTextView extends androidx.appcompat.widget.AppCompatTextView {

    private boolean isMarqueeEnable = false;

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMarqueeEnable(boolean enable) {
        if (isMarqueeEnable != enable) {
            isMarqueeEnable = enable;
            if (enable) {
                setEllipsize(TextUtils.TruncateAt.MARQUEE);
                setMarqueeRepeatLimit(-1);//设置循环跑 marquee_forever = -1
                setSingleLine();
            } else {
                setEllipsize(TextUtils.TruncateAt.END);
            }
            onWindowFocusChanged(enable);
        }
    }

    public boolean isMarqueeEnable() {
            return isMarqueeEnable;
    }

    @Override
    public boolean isFocused() {
        return isMarqueeEnable;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(isMarqueeEnable, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(isMarqueeEnable);
    }


    /**
     *
     * @param speed
     * @param speedIsMultiplier 是否使用倍增器
     */
    public void setMarqueeSpeed(float speed, boolean speedIsMultiplier) {

        try {
            Field f = this.getClass().getDeclaredField("mMarquee");
            f.setAccessible(true);

            Object marquee = f.get(this);
            if (marquee != null) {

                String scrollSpeedFieldName = "mScrollUnit";
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.L)
//                    scrollSpeedFieldName = "mPixelsPerSecond";

                Field mf = marquee.getClass().getDeclaredField(scrollSpeedFieldName);
                mf.setAccessible(true);

                float newSpeed = speed;
                if (speedIsMultiplier)
                    newSpeed = mf.getFloat(marquee) * speed;

                mf.setFloat(marquee, newSpeed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}