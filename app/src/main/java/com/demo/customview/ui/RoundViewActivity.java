package com.demo.customview.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.demo.customview.BitMapUtil;
import com.demo.customview.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 圆形头像处理
 */
public class RoundViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_view);

        setimgeview1();
        setimgeview2();
    }


    private void setimgeview1() {
        String url = "https://avatars1.githubusercontent.com/u/63182977?s=460&u=b19055cf8636ce0a1e91ae948e29c105fd61a374&v=4";
        ImageView imageView = findViewById(R.id.iv);
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = BitMapUtil.drawableToBitmap(drawable);
        Bitmap circleBitMap = BitMapUtil.circleBitMap(bitmap);
        Drawable circleDrawable = BitMapUtil.bitampTodrawable(circleBitMap);


        // 如果显示空白使用明文通信,清单配置稳健者 <application 标签中 添加android:usesCleartextTraffic="true"
        //Picasso要添加依赖才能用哦
        Picasso.with(this).load(url)
                .error(circleDrawable)//加载失败后显示的图片
                .placeholder(circleDrawable)//占位图片
                .transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) { //加工图片
                        Bitmap zoom = BitMapUtil.zoom(source, dp2px(200), dp2px(200));
                        Bitmap circleBitMap = BitMapUtil.circleBitMap(zoom);
                        //1:transform当中处理完图片之后，需要调用recylce方法回收
                        source.recycle();
                        return circleBitMap;
                    }
                    @Override
                    public String key() {
                        //2:重写key方法的返回值，不能是null
                        return "";
                    }
                }).into(imageView);

    }


    //用图片进行叠加取交集显示
    private void setimgeview2() {

        ImageView imageView = findViewById(R.id.iv2);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDensity = getResources().getDisplayMetrics().densityDpi;
        //获取准备好的 圆形图片
        Bitmap yuan = BitmapFactory.decodeResource(getResources(), R.mipmap.yuan100, opts);
        //获取需要裁减的图片
        Bitmap pic = BitmapFactory.decodeResource(getResources(), R.mipmap.cgx, opts);

        int width = dp2px(200);
        //图片进行统一尺寸缩放
        Bitmap zoomYuan = BitMapUtil.zoom(yuan, width, width);
        Bitmap zoomPic = BitMapUtil.zoom(pic, width, width);

        Bitmap circleBitMap = BitMapUtil.circleBitMap2(zoomPic, zoomYuan);
        imageView.setImageBitmap(circleBitMap);
    }


    public int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5); //加0.5是 四五舍人
    }


}
