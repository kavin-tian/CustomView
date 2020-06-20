package com.demo.customview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.customview.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class MySmartRefreshLayoutActivity extends Activity {

    private SmartRefreshLayout refreshLayout;
    private MySmartRefreshLayoutActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //全屏显示,但显示状态栏信息
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        //全屏显示,但显示底部菜单栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_my_smart_refresh_layout);
        this.context = this;

        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                Toast.makeText(context, "刷新成功", Toast.LENGTH_SHORT).show();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

        //关闭上拉加载更多功能
        //refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//                refreshlayout.finishLoadMoreWithNoMoreData();
            }
        });

    }
}
