package com.demo.customview.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_tab_layout);
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

}
