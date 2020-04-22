package com.demo.customview.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
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

public class RoundViewActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_view);

        String url = "https://avatars1.githubusercontent.com/u/63182977?s=460&u=b19055cf8636ce0a1e91ae948e29c105fd61a374&v=4";
//        String url ="http://192.168.0.104:8080/img.jpg";
        ImageView imageView = findViewById(R.id.iv);
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = BitMapUtil.drawableToBitmap(drawable);
        Bitmap circleBitMap = BitMapUtil.circleBitMap(bitmap);
        Drawable circleDrawable = BitMapUtil.bitampTodrawable(circleBitMap);


        // 如果显示空白使用明文通信,清单配置稳健者 <application 标签中 添加android:usesCleartextTraffic="true"
        //Picasso要添加依赖才能用哦
        Picasso.with(this).load(url)
                .error(circleDrawable)
                .placeholder(circleDrawable)
                .transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) { //加工图片
                Bitmap zoom = BitMapUtil.zoom(source, dp2px(200),dp2px(200));
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
    public  int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }




}
