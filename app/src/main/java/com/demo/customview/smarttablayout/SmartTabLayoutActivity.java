package com.demo.customview.smarttablayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.demo.customview.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class SmartTabLayoutActivity extends AppCompatActivity {
    private static final int WHAT_POSITION = 100;
    private ViewPager viewPager;
    private int positon = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_POSITION) {
                if (positon > 3) {
                    positon = 0;
                }
                viewPager.setCurrentItem(positon, true);
                handler.sendEmptyMessageDelayed(WHAT_POSITION, 3000);
                positon++;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_tab_layout);

        init1();
        init2();
    }

    private void init2() {
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("slide0", ImageFragment.class) //PageFragment必须是public修饰的类
                .add("slide1", ImageFragment.class)
                .add("slide2", ImageFragment.class)
                .add("slide3", ImageFragment.class)
                .create());

        viewPager = (ViewPager) findViewById(R.id.viewpager2);
        viewPager.setAdapter(adapter);

        final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab2);
        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(SmartTabLayoutActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        handler.sendEmptyMessageDelayed(WHAT_POSITION, 3000);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        handler.removeMessages(WHAT_POSITION);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.sendEmptyMessageDelayed(WHAT_POSITION, 3000);
                        break;
                }
                return false;
            }
        });

    }

    private void init1() {
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("今日更新", PageFragment.class) //PageFragment必须是public修饰的类
                .add("行业资讯", PageFragment.class)
                .add("客厅收纳", PageFragment.class)
                .add("八分生活", PageFragment.class)
                .add("美居美家", PageFragment.class)
                .add("厨房整理", PageFragment.class)
                .add("厨房收纳", PageFragment.class)
                .add("卫生间收纳", PageFragment.class)
                .add("综合整理", PageFragment.class)
                .add("美食特工", PageFragment.class)
                .add("旅行游记", PageFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        final SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);

        setTabTextColor(adapter, viewPagerTab, 0);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(SmartTabLayoutActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                setTabTextColor(adapter, viewPagerTab, position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabTextColor(FragmentPagerItemAdapter adapter, SmartTabLayout viewPagerTab, int position) {
        int childCount = adapter.getCount();

        for (int i = 0; i < childCount; i++) {
            if (i == position) {
                TextView tabAt = (TextView) viewPagerTab.getTabAt(position);
                tabAt.setTextColor(Color.BLACK);
                tabAt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                //加粗字体
                tabAt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                TextView tabAt = (TextView) viewPagerTab.getTabAt(i);
                tabAt.setTextColor(Color.parseColor("#8D000000"));

                //android中为textview动态设置字体为粗体
                tabAt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                //正常字体
                tabAt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(WHAT_POSITION);
    }
}
