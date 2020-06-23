package com.demo.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class FadeBanner extends FrameLayout {
    private static final float MIN_OFFSET_PX = 5;//最小位移像素
    private static final long PLAY_DELAY = 3000;
    Context context;
    private OnClickListener onClickListener;
    private OnPageChangeListener onPageChangeListener;
    private ArrayList<Integer> imgeList = new ArrayList<Integer>();
    private HashMap<Integer, ImageView> mImgMap = new HashMap<Integer, ImageView>();

    public FadeBanner(@NonNull Context context) {
        super(context);
        initview(context);
    }

    public FadeBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public FadeBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    startAlphaAnimation();
                    break;
            }
        }
    };

    /**
     * 播放下一张
     */
    private void playNext() {
        isHasUnderImage = false;
        ImageView imageView = mImgMap.get(getNextPosition());
        setUnderImage(imageView);
        handler.sendEmptyMessageDelayed(0, PLAY_DELAY);
    }

    private void initview(Context context) {
        this.context = context;
        if (imgeList.size() <= 0) {
            return;
        }
        for (int i = 0; i < imgeList.size(); i++) {
            //初始化要显示的图片对象
            final ImageView imageView = new ImageView(context);
            imageView.setImageResource(imgeList.get(i));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setTag(i);
            mImgMap.put(i, imageView);
        }
        addView(mImgMap.get(mImgMap.size() - 1));
        //开始轮播
        playNext();
    }

    /**
     * 设置图片数据
     */
    public void setImgeList(ArrayList<Integer> imgeList) {
        this.imgeList = imgeList;
        initview(context);
    }

    float downX = 0;
    float offset = 0;
    boolean isHasUnderImage = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isHasUnderImage = false;
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //触摸是停止轮播
                handler.removeMessages(0);
                float moveX = event.getX();
                offset = moveX - downX;
                slider(offset);
                break;
            case MotionEvent.ACTION_UP:
                startAlphaAnimation();
                setOnClick(offset);
                break;
        }
        //返回true目标view持续接收触摸事件
        return true;
    }

    /**
     * 设置当前图片的点击事件
     */
    private void setOnClick(float offset) {
        if (Math.abs(offset) <= MIN_OFFSET_PX) {
            ImageView imageView = mImgMap.get(getCurrentPosition());
            if (onClickListener != null) {
                int index = (int) imageView.getTag();
                onClickListener.onClick(imageView, index);
            }
        }
    }

    boolean isRunningAnimation = false;

    /**
     * 开始渐变动画
     */
    private void startAlphaAnimation() {

        if (mImgMap.size() <= 1) {
            return;
        }

        if (isRunningAnimation) {
            return;
        }

        int childCount = getChildCount();
        final ImageView currentImageView = (ImageView) getChildAt(childCount - 1);
        float alpha = currentImageView.getAlpha();
        //透明度变化
        AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, 0.0f);//第一个参数开始的透明度，第二个参数结束的透明度
        alphaAnimation.setDuration(500);//多长时间完成这个动作
        //动画终止时停留在最后一帧
        //alphaAnimation.setFillEnabled(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isRunningAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //关键代码,必须把动画设置为空下次才能重新设置动画
                currentImageView.clearAnimation();
                //还原初始透明度
                currentImageView.setImageAlpha(maxAlpha);

                if (getChildCount() > 1) {
                    removeView(currentImageView);
                }

                if (onPageChangeListener != null) {
                    //获取当前显示的图片
                    View showView = getChildAt(getChildCount() - 1);
                    int index = (Integer) showView.getTag();
                    onPageChangeListener.onImageChange(index);
                }

                playNext();
                isRunningAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        //开始执行动画,不能用setAnimation()只会执行一次
        currentImageView.startAnimation(alphaAnimation);

    }

    int maxAlpha = 255;//透明度范围0~255

    /**
     * 滑动时调用
     */
    private void slider(float offset) {

        if (mImgMap.size() <= 1) {
            return;
        }

        if (isRunningAnimation) {
            return;
        }

        //为了效果明显*2
        offset = offset * 2;
        float absOffset = Math.abs(offset);
        float percent = 1 - absOffset / getWidth();

        if (percent < 0 || percent > 1) {
            return;
        }

        //获取顶部的图片
        int topIndex = getChildCount() - 1;
        ImageView currentImageView = (ImageView) getChildAt(topIndex);

        //随滑动渐变透明度
        currentImageView.setImageAlpha((int) (percent * maxAlpha));

        //如果是从 左->右 滑动 , 上一张
        if (offset > 0 && absOffset > MIN_OFFSET_PX) {
            ImageView imageView = mImgMap.get(getPrePosition());
            setUnderImage(imageView);

        } else if (offset < 0 && absOffset > MIN_OFFSET_PX) { //右->左,  下一张
            ImageView imageView = mImgMap.get(getNextPosition());
            setUnderImage(imageView);
        }

    }


    /**
     * 设置位于下面的图片
     */
    private void setUnderImage(ImageView imageView) {

        //一次触摸事件只执行一次
        if (!isHasUnderImage) {
            isHasUnderImage = true;
            if (getChildCount() > 1) {
                removeViewAt(0);
            }
            addView(imageView, 0);
        }
    }


    /**
     * 获取当前图片的索引
     */
    private int getCurrentPosition() {

        //当前图片集合只有一张图片时, 上一张就 自己
        if (mImgMap.size() <= 1) {
            return 0;
        }
        int prePosition = 0;
        int topIndex = getChildCount() - 1;
        int currentPosition = (int) getChildAt(topIndex).getTag();

        return currentPosition;
    }

    /**
     * 获取上一张的索引
     */
    private int getPrePosition() {

        //当前图片集合只有一张图片时, 上一张就 自己
        if (mImgMap.size() <= 1) {
            return 0;
        }
        int prePosition = 0;
        int topIndex = getChildCount() - 1;
        int currentPosition = (int) getChildAt(topIndex).getTag();

        if (currentPosition == 0) {//当前图片就是第0张时, 上一张就在最后一张图片
            prePosition = mImgMap.size() - 1;
        } else {
            prePosition = currentPosition - 1;
        }
        return prePosition;
    }

    /**
     * 获取下一张的索引
     */
    private int getNextPosition() {

        //当前图片集合只有一张图片时, 上一张就 自己
        if (mImgMap.size() <= 1) {
            return 0;
        }
        int nextPosition = 0;

        //获取当前轮播图容器的最顶部图片角标
        int topIndex = getChildCount() - 1;

        //获取当前显示的图片在map集合中的索引
        int currentPosition = (int) getChildAt(topIndex).getTag();

        if (currentPosition == mImgMap.size() - 1) { //当图片是最后一张是, 下一张就是第 0 张
            nextPosition = 0;
        } else {
            nextPosition = currentPosition + 1;
        }
        return nextPosition;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public interface OnClickListener {
        void onClick(View v, int index);
    }

    public interface OnPageChangeListener {
        void onImageChange(int index);
    }

}
