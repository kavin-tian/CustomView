package com.demo.customview.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.demo.customview.R;

/**
 * kavin_tian@163.com
 */
public class ToggleButtonActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toggle_button);

        ToggleButton tb = findViewById(R.id.toggleButton);

        //SharedPreferences中的信息以XML文件的形式保存在 /data/data/PACKAGE_NAME/shared_prefs目录下。
        //Context.MODE_PRIVATE：为默认操作模式,代表该文件是私有数据,只能被应用本身访问,在该模式下,写入的内容会覆盖原文件的内容
        //Context.MODE_APPEND：模式会检查文件是否存在,存在就往文件追加内容,否则就创建新文件.
        //MODE_WORLD_READABLE：表示当前文件可以被其他应用读取.
        //MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入
        //特别注意：出于安全性的考虑，MODE_WORLD_READABLE 和 MODE_WORLD_WRITEABLE 在Android 4.2版本中已经被弃用
        final SharedPreferences sharedPreferences = getSharedPreferences("toggle_button_state", Context.MODE_PRIVATE);
        boolean isChecked = sharedPreferences.getBoolean("isChecked", false);


        tb.setChecked(isChecked);

        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("isChecked", isChecked).commit();
                if (isChecked) {
                    //密码已经开启
                    toast("密码已经开启");
                } else {
                    toast("密码已经关闭");
                }

            }
        });

    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
