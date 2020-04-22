package com.demo.customview.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.demo.customview.MarqueeTextView2;
import com.demo.customview.MarqueeTextView;
import com.demo.customview.R;

public class MarqueeActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marquee);
        TextView textView = findViewById(R.id.tv);
        textView.setSelected(true);

        MarqueeTextView marqueeTextView = findViewById(R.id.marqueeTextView);
        marqueeTextView.setMarqueeEnable(true);
        marqueeTextView.setMarqueeSpeed(0,true);

        MarqueeTextView2 marqueeTextView2 = findViewById(R.id.marqueeTextView2);
        marqueeTextView2.startScroll();
        marqueeTextView2.setSpeedMulriple(1f);


    }
}
