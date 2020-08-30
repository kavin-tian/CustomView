package com.demo.customview.slideseemorelayout;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.demo.customview.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * 商品fragment
 * Created by Administrator on 2018/12/11.
 */

public class GoodsFragment extends Fragment {
    SlideSeeMoreLayout layout;
    GoodsDetailActivity activity;


    FloatingActionButton actionButton;
    ScrollView scrollView;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private LinearLayout rootView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods,null);
        layout = view.findViewById(R.id.layout);
        actionButton = view.findViewById(R.id.actionButton);
        scrollView = view.findViewById(R.id.scrollView);
        viewPager = view.findViewById(R.id.viewPager);
        rootView = view.findViewById(R.id.rootView);
        linearLayout = view.findViewById(R.id.linearLayout);
        initView();
        initListener();
        setAdapter();
        return view;
    }

    private void setAdapter() {
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TestFragment1());
        fragments.add(new TestFragment2());
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
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

    private void initView() {
        actionButton.hide();
    }

    private void initListener() {
        layout.setOnSlideDetailsListener(new SlideSeeMoreLayout.OnSlideDetailsListener() {
            @Override
            public void onStateChanged(SlideSeeMoreLayout.Status status) {
                if (status == SlideSeeMoreLayout.Status.OPEN){
                    //当前为查看更多页
                    actionButton.show();
                }else {
                    //当前为商品页
                    actionButton.hide();
                }
            }
        });


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.scrollTo(0,0);
                layout.smoothClose(true);
            }
        });

        //可以获取rootview的宽高
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayout.setMinimumHeight(rootView.getHeight());
                //切记用完一定要移除监听
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}
