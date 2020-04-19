package com.demo.customview.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.customview.R;
import com.demo.customview.stellar.StellarMap;

import java.util.Random;

public class StellarMapActivity extends AppCompatActivity {

    private String[] one = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)",
            "180天理财计划(加息10%)", "林业局投资商业经营", "中学老师购买车辆"};

    private String[] two = new String[]{"屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营", "旅游公司扩大规模",
            "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stellar_map);

        showStellarMap();
    }

    private void showStellarMap() {
        StellarMap stellarMap = findViewById(R.id.stellarMap);

        int padding = dp2px(this, 5);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new MyAdapter());
        //每组出现的数据组的搭配规则
        stellarMap.setRegularity(8, 8);
        //设置从哪一组开始做动画操作
        stellarMap.setGroup(0, true);

    }

    private class MyAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {//总共要显示几组数据
            return 2;
        }

        @Override
        public int getCount(int group) {//第group组有几个数据
            return 8;
        }

        @Override
        public View getView(int group, int position, View convertView) {

            Random random = new Random();
            TextView tv = new TextView(StellarMapActivity.this);
            int r = random.nextInt(210);
            int g = random.nextInt(210);
            int b = random.nextInt(210);
            tv.setTextColor(Color.rgb(r, g, b));
            tv.setTextSize(dp2px(StellarMapActivity.this, 8) + random.nextInt(8));
            if (group == 0) {
                tv.setText(one[position]);
            } else if (group == 1) {
                tv.setText(two[position]);
            }
            return tv;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            //下一次要显示的组
            return group == 1 ? 0 : 1;
        }
    }


    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

}
