package com.demo.customview.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.customview.MySlidingMenu;
import com.demo.customview.R;

public class SlidingMenuActivity extends AppCompatActivity {

    private ImageView mBack;
    private MySlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);

        initView();
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mSlidingMenu = (MySlidingMenu) findViewById(R.id.myslidingmenu);
        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //打开关闭侧拉菜单
                mSlidingMenu.toggle();
            }
        });
    }

    /**
     * 菜单页的textview的点击事件
     *@param view ： 被点击控件的对象
     */
    public void showtext(View view){
        TextView textView = (TextView) view;
        Toast.makeText(this, textView.getText(), Toast.LENGTH_SHORT).show();
    }
}
