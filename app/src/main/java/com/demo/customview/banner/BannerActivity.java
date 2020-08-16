package com.demo.customview.banner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.demo.customview.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

/**
 * 报错: Invoke-customs are only supported starting with Android O (--min-api 26),用到1.8的新特性
 * android {
 *  compileOptions {
 *         sourceCompatibility JavaVersion.VERSION_1_8
 *         targetCompatibility JavaVersion.VERSION_1_8
 *     }
 *  }
 *
 * 参考博客:
 * https://github.com/youth5201314/banner
 * https://blog.csdn.net/Wen_dey/article/details/88073941?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.channel_param
 *
 */
public class BannerActivity extends AppCompatActivity {

    private Banner banner;
    private MyCircleIndicator2 indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        banner = findViewById(R.id.banner);
        indicator = findViewById(R.id.indicator);
        useBanner();

        indicator.setBanner(banner);
    }

    public void useBanner() {
        //—————————————————————————如果你想偷懒，而又只是图片轮播————————————————————————
        banner.setAdapter(new BannerImageAdapter<DataBean>(DataBean.getTestData3()) {
            @Override
            public void onBindView(BannerImageHolder holder, DataBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.imageUrl)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this));
        //更多使用方法仔细阅读文档，或者查看demo

    }


    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止轮播
        banner.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁
        banner.destroy();
    }


}
