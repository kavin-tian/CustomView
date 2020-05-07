package com.demo.customview.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.demo.customview.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class PullToRefreshActivity extends FragmentActivity {


    Handler handler = new Handler();
    PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        pullToRefreshListView = findViewById(R.id.pullToRefreshListView);

        // 设置下拉刷新和上拉加载模式
        this.pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        // 监听下拉刷新
        this.pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 恢复下拉刷新状态
                        PullToRefreshActivity.this.pullToRefreshListView.onRefreshComplete();
                    }
                },2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 恢复下拉刷新状态
                        PullToRefreshActivity.this.pullToRefreshListView.onRefreshComplete();
                    }
                },2000);
            }
        });

        pullToRefreshListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 30;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(PullToRefreshActivity.this);
                textView.setText(""+position);
                textView.setPadding(10,10,10,10);
                return textView;
            }
        });

    }

}