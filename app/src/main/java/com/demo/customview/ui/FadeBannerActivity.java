package com.demo.customview.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.customview.FadeBanner;
import com.demo.customview.R;

import java.util.ArrayList;

public class FadeBannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade_banner);

        FadeBanner fadeBanner = findViewById(R.id.myBanner);
        final TextView tv = findViewById(R.id.tv);

        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.slide0);
        list.add(R.mipmap.slide1);
        list.add(R.mipmap.slide2);
        list.add(R.mipmap.slide3);

        fadeBanner.setImgeList(list);
        //重第二张开始播放
        fadeBanner.setPosition(2);
        int position = fadeBanner.getCurrentPosition();
        tv.setText("当前页为: "+position);
        fadeBanner.setOnClickListener(new FadeBanner.OnClickListener() {
            @Override
            public void onClick(View v, int index) {
                Toast.makeText(FadeBannerActivity.this, "onClick: " + index, Toast.LENGTH_SHORT).show();
            }
        });
        fadeBanner.setOnPageChangeListener(new FadeBanner.OnPageChangeListener() {
            @Override
            public void onImageChange(int index) {
                tv.setText("当前页为: " + index);
//                Toast.makeText(FadeBannerActivity.this, "OnPageChange: " + index, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
