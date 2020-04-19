package com.demo.customview.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.customview.CircleProgressBar;
import com.demo.customview.R;
import com.demo.customview.stellar.StellarMap;

import java.util.Random;

public class CircleProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress_bar);

        showCircleProgressBar();

    }

    /**
     * 模拟加载进度
     */
    private void showCircleProgressBar() {
        final CircleProgressBar progressBar = findViewById(R.id.progressBar);
        new Thread() {
            @Override
            public void run() {
                super.run();
                int progress = 0;
                while (progress <= 100) {
                    try {
                        progressBar.setProgress(progress);
                        progress++;
                        if (progress >= 100) {
                            progress = 0;
                        }
                        sleep(20);//睡眠20毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
