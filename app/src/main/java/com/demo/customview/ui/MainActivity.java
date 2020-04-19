package com.demo.customview.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.demo.customview.R;
import com.demo.customview.ui.CircleProgressBarActivity;
import com.demo.customview.ui.StellarMapActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClick1(View view) {
        Intent intent = new Intent(this, CircleProgressBarActivity.class);
        startActivity(intent);

    }

    public void onClick2(View view) {
        Intent intent = new Intent(this, StellarMapActivity.class);
        startActivity(intent);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(this, IOSScrollViewActivity.class);
        startActivity(intent);
    }


}
