package com.demo.customview.anim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.customview.R;

/**
recyclerview的简单实现
*/

public class MyAnimationAdapter extends RecyclerView.Adapter<MyAnimationAdapter.ViewHolder> {

    private AnimationActivity mContext;
    public MyAnimationAdapter(AnimationActivity context) {
        this.mContext = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.iv_anim = (ImageButton) view.findViewById(R.id.ib_add);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.iv_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] location = new int[2];
                view.getLocationInWindow(location);
                mContext.playAnimation(location);
            }
        });
    }
    @Override
    public int getItemCount() {
        return 30;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageButton iv_anim;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
