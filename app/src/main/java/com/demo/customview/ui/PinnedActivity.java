package com.demo.customview.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.demo.customview.PinnedSectionListView;
import com.demo.customview.R;

import java.util.ArrayList;

/**
 * Created by kavin on 2016/12/11.
 */

public class PinnedActivity extends Activity {
    ListView listview;
    private ArrayList<Base> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinned);
        listview = findViewById(R.id.listview);

        data = new ArrayList<>();
        data.add(new Province("省1"));
        for (int i = 0; i < 6; i++) {
            data.add(new City("城市1" + i));
        }

        data.add(new Province("省2"));
        for (int i = 0; i < 6; i++) {
            data.add(new City("城市2" + i));
        }

        data.add(new Province("省3"));
        for (int i = 0; i < 6; i++) {
            data.add(new City("城市3" + i));
        }

        data.add(new Province("4"));
        for (int i = 0; i < 6; i++) {
            data.add(new City("城市4" + i));
        }
        listview.setAdapter(new MyAdapter());
    }

    public static final int PROVICE_TYPE = 0;
    public static final int CITY_TYPE = 1;

    class MyAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        // 需要的条目个数
        @Override
        public int getViewTypeCount() {
            return 2;
        }

        // 根据当前位置，返回条目类型
        @Override
        public int getItemViewType(int position) {
            Base base = data.get(position);
            if (base instanceof Province) {
                return PROVICE_TYPE;
            } else {
                return CITY_TYPE;
            }
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i);
            if (view == null) {
                switch (itemViewType) {
                    case PROVICE_TYPE:
                        view = new TextView(getApplicationContext());
                        view.setBackgroundColor(Color.RED);
                        break;
                    case CITY_TYPE:
                        view = new TextView(getApplicationContext());

                        view.setBackgroundColor(Color.BLUE);
                        break;
                    default:
                        break;
                }
            }
            TextView tv = (TextView) view;
            tv.setTextSize(20);
            tv.setPadding(0, 15, 0, 15);
            tv.setText(data.get(i).name);
            return view;
        }

        // 设置要被固定的条目类型
        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == PROVICE_TYPE;
        }
    }


    public class Base {
        public String name;

        public Base(String name) {
            this.name = name;
        }
    }

    public class City extends Base {

        public City(String name) {
            super(name);
        }
    }

    public class Province extends Base {
        public Province(String name) {
            super(name);
        }
    }

}
