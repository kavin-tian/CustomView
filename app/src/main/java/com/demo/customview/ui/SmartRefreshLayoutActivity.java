package com.demo.customview.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.customview.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class SmartRefreshLayoutActivity extends FragmentActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        //initHeaderAndFooter();
        setContentView(R.layout.activity_smart_refresh);
        refreshLayout = findViewById(R.id.refreshLayout);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new MyRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                Toast.makeText(context,"刷新成功",Toast.LENGTH_SHORT).show();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Toast.makeText(context,"加载成功",Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
//                refreshlayout.finishLoadMoreWithNoMoreData();
            }
        });
    }

    /**
     * 把控件的英文变成中文
     * Classics经典的
     */
    private void initHeaderAndFooter() {
        ClassicsHeader.REFRESH_HEADER_PULLING = "下拉可以刷新";//"下拉可以刷新";
        ClassicsHeader.REFRESH_HEADER_REFRESHING = "正在刷新...";//"正在刷新...";
        ClassicsHeader.REFRESH_HEADER_LOADING = "正在加载...";//"正在加载...";
        ClassicsHeader.REFRESH_HEADER_RELEASE = "释放立即刷新";//"释放立即刷新";
        ClassicsHeader.REFRESH_HEADER_FINISH = "刷新完成";//"刷新完成";
        ClassicsHeader.REFRESH_HEADER_FAILED = "刷新失败";//"刷新失败";
        ClassicsHeader.REFRESH_HEADER_UPDATE = "上次更新 M-d HH:mm";//"上次更新 M-d HH:mm";
        ClassicsHeader.REFRESH_HEADER_SECONDARY = "释放进入二楼";//"释放进入二楼";

        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";//"上拉加载更多";
        ClassicsFooter. REFRESH_FOOTER_RELEASE = "释放立即加载";//"释放立即加载";
        ClassicsFooter .REFRESH_FOOTER_LOADING = "正在加载...";//"正在加载...";
        ClassicsFooter. REFRESH_FOOTER_REFRESHING = "正在刷新...";//"正在刷新...";
        ClassicsFooter. REFRESH_FOOTER_FINISH = "加载完成";//"加载完成";
        ClassicsFooter. REFRESH_FOOTER_FAILED = "加载失败";//"加载失败";
        ClassicsFooter. REFRESH_FOOTER_NOTHING = "没有更多数据了";//"没有更多数据了";
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setPadding(10,10,10,10);
            MyViewHolder holder = new MyViewHolder(textView);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            TextView textView = (TextView) myViewHolder.itemView;
            textView.setText("test"+position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        private class MyViewHolder extends RecyclerView.ViewHolder{
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
