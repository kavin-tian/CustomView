package com.demo.customview.slideseemorelayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.customview.R;

import java.util.ArrayList;

/**
 * Android京东、淘宝商品详情界面、下拉查看更多详情
 * 参考:https://github.com/kangkanger/SlideSeeMoreLayout
 */
public class GoodsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        ViewPager viewPager = findViewById(R.id.viewPager);

        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new GoodsFragment());
        fragments.add(new TestFragment1());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

    }
}
