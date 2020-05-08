package com.demo.customview.ui;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.customview.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by huang on 2016/12/11.
 */

public class PhotoViewActivity extends Activity {
    PhotoView photoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        photoview = findViewById(R.id.photoview);


        // 监听单击事件
        photoview.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Toast.makeText(PhotoViewActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
    }




}
