package com.demo.customview.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.demo.customview.R;

public class MatrixActivity extends FragmentActivity {

    private ImageView imageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        imageview = findViewById(R.id.imageview);
    }

    float scale = 1f;

    public void Scale(View view) {
        scale += 0.1f;
        Matrix imageMatrix = imageview.getImageMatrix();
        // 创建矩阵
        Matrix matrix = new Matrix(imageMatrix);
        //设置放缩比例
        //第二个参数是Y轴的缩放大小，第三四个参数是缩放中心点。
        //一般这个缩放中心点比较不好理解。这个中心点并不一定在图片的中心位置。有可能在图片的外面,是参考屏幕坐标
        matrix.setScale(scale, scale, 0, 0);
        imageview.setImageMatrix(matrix);
    }

    public void move(View view) {
        //获取图片的原始宽度
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.cgx, options);
        int picWidth = options.outWidth;
        int picHeight = options.outHeight;

        int imageviewWidth = imageview.getWidth();
        int imageviewHeight = imageview.getHeight();

        int moveX = imageviewWidth / 2 - picWidth / 2;
        int moveY = imageviewHeight / 2 - picHeight / 2;

        Matrix imageMatrix = imageview.getImageMatrix();
        // 创建矩阵
        Matrix matrix = new Matrix(imageMatrix);
        // 移动
        matrix.postTranslate(moveX, moveY);
        imageview.setImageMatrix(matrix);

    }

    public void rotate(View view) {
        scale += 0.1f;
        Matrix imageMatrix = imageview.getImageMatrix();
        // 创建矩阵
        Matrix matrix = new Matrix(imageMatrix);
        // 旋转
        matrix.postRotate(scale);
        // 把新的矩阵设置给图片
        imageview.setImageMatrix(matrix);
    }

    public void skew(View view) {
        scale = 0.1f;
        Matrix imageMatrix = imageview.getImageMatrix();
        // 创建矩阵
        Matrix matrix = new Matrix(imageMatrix);
        // 扭曲
        matrix.postSkew(scale, scale);
        // 把新的矩阵设置给图片
        imageview.setImageMatrix(matrix);

    }
}
