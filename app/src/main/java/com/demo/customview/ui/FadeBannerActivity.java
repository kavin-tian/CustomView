package com.demo.customview.ui;

import android.os.Bundle;
import android.view.View;
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

        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.slide1);
        list.add(R.mipmap.slide2);
        list.add(R.mipmap.slide3);
        list.add(R.mipmap.slide4);

        fadeBanner.setImgeList(list);
        fadeBanner.setOnClickListener(new FadeBanner.OnClickListener() {
            @Override
            public void onClick(View v, int index) {
                Toast.makeText(FadeBannerActivity.this, "onClick: " + index, Toast.LENGTH_SHORT).show();
            }
        });
        fadeBanner.setOnPageChangeListener(new FadeBanner.OnPageChangeListener() {
            @Override
            public void onImageChange(int index) {
                Toast.makeText(FadeBannerActivity.this, "OnPageChange: " + index, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
