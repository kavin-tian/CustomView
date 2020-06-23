package com.demo.customview.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.customview.R;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class BottomSheetLayoutActivity extends Activity {
    private View shopCartListView;
    private BottomSheetLayout bottomSheetLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet);
        bottomSheetLayout = findViewById(R.id.bottomSheet);
        View button = findViewById(R.id.button);
    }

    public void click(View view) {

        if (shopCartListView == null) {
            shopCartListView = createShopCartListView();
        }

        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            bottomSheetLayout.showWithSheetView(shopCartListView);
        }

    }

    private View createShopCartListView() {
        View view = LayoutInflater.from(this).inflate(R.layout.cart_list, bottomSheetLayout, false);
        RecyclerView recyclerView = view.findViewById(R.id.rvCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(new MyAdapter());
        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(26);
            return new MyViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText("--------------item: "+position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(TextView textView) {
            super(textView);
        }
    }
}
