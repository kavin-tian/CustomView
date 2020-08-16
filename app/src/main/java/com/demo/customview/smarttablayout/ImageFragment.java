package com.demo.customview.smarttablayout;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.customview.R;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

public class ImageFragment extends Fragment {

    private static int[] images = new int[]{R.mipmap.slide0, R.mipmap.slide1, R.mipmap.slide2, R.mipmap.slide3};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        TextView title = (TextView) view.findViewById(R.id.item_title);
        ImageView image = (ImageView) view.findViewById(R.id.item_image);
        title.setText(String.valueOf(position));
        image.setImageResource(images[position]);
    }
}
