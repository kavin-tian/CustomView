package com.demo.customview.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.customview.R;
import com.demo.customview.chart.BarChartActivity;
import com.demo.customview.chart.LineChartActivity;
import com.demo.customview.chart.LineChartActivity1;
import com.demo.customview.chart.PieChartActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick1(View view) {
        Intent intent = new Intent(this, CircleProgressBarActivity.class);
        startActivity(intent);

    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, StellarMapActivity.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, IOSScrollViewActivity.class);
        startActivity(intent);
    }

    public void onClick4(View view) {
        Intent intent = new Intent(this, RoundViewActivity.class);
        startActivity(intent);
    }

    public void onClick4_1(View view) {
        Intent intent = new Intent(this, LoveViewActivity.class);
        startActivity(intent);
    }

    public void onClick4_2(View view) {
        Intent intent = new Intent(this, RoundImageViewActivity.class);
        startActivity(intent);
    }

    public void onClick4_3(View view) {
        Intent intent = new Intent(this, CircleImageViewActivity.class);
        startActivity(intent);
    }

    public void onClick5(View view) {
        Intent intent = new Intent(this, ToggleButtonActivity.class);
        startActivity(intent);

    }
    public void onClick6(View view) {
        Intent intent = new Intent(this, MarqueeActivity.class);
        startActivity(intent);

    }
    public void onClick7(View view) {
        Intent intent = new Intent(this, LineChartActivity.class);
        startActivity(intent);

    }
    public void onClick8(View view) {
        Intent intent = new Intent(this, LineChartActivity1.class);
        startActivity(intent);

    }

    public void onClick9(View view) {
        Intent intent = new Intent(this, BarChartActivity.class);
        startActivity(intent);
    }
    public void onClick10(View view) {
        Intent intent = new Intent(this, PieChartActivity.class);
        startActivity(intent);
    }


    public void onClick11(View view) {
        Intent intent = new Intent(this, PullToRefreshActivity.class);
        startActivity(intent);
    }
    public void onClick12(View view) {
        Intent intent = new Intent(this, PinnedActivity.class);
        startActivity(intent);
    }
    public void onClick13(View view) {
        Intent intent = new Intent(this, PhotoViewActivity.class);
        startActivity(intent);
    }
    public void onClick14(View view) {
        Intent intent = new Intent(this, MatrixActivity.class);
        startActivity(intent);
    }

    public void onClick15(View view) {
        Intent intent = new Intent(this, SmartRefreshLayoutActivity.class);
        startActivity(intent);
    }

    public void onClick16(View view) {
        Intent intent = new Intent(this, MySmartRefreshLayoutActivity.class);
        startActivity(intent);
    }
    public void onClick17(View view) {
        Intent intent = new Intent(this, FadeBannerActivity.class);
        startActivity(intent);
    }

    public void onClick18(View view) {
        Intent intent = new Intent(this, BottomSheetLayoutActivity.class);
        startActivity(intent);
    }
}
