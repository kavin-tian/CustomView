package com.demo.customview.chart;


import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.demo.customview.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class BarChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);

        setTitle("BarChartActivity");

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarY = findViewById(R.id.seekBar2);

        seekBarY.setOnSeekBarChangeListener(this);
        seekBarX.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        //如果在图表中显示超过60个条目，则不会绘制任何值
        chart.setMaxVisibleValueCount(60);

        //缩放现在只能分别在x轴和y轴上进行
        chart.setPinchZoom(false);

        //设置网格背景
        chart.setDrawGridBackground(false);
        //设置x轴的数值显示样式
        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);
        //得到x轴
        XAxis xAxis = chart.getXAxis();
        //x轴设置为下边
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //设置字体
        xAxis.setTypeface(tfLight);
        //绘制网格线
        xAxis.setDrawGridLines(false);
        //间隔时间1f==每天都显示,2f隔一天显示
        xAxis.setGranularity(1f); // only intervals of 1 day
        //显示x轴坐标值数量
        xAxis.setLabelCount(7);
        //设置坐标数值的显示样式
        xAxis.setValueFormatter(xAxisFormatter);

        //设置y轴的数值显示样式
        ValueFormatter custom = new MyValueFormatter("$");

        //得到左边的y轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        //最高的柱子距离图表顶部的间隔
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        //隐藏右边的y轴
        chart.getAxisRight().setEnabled(false);

        //得到图例
        Legend l = chart.getLegend();
        //设置标签位置到左下角
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        // setting data 设置数据
        seekBarY.setProgress(50);
        seekBarX.setProgress(12);
    }

    private void setData(int count, float range) {

        float start = 1f;

        //构造模拟数据集
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {

            set1 = new BarDataSet(values, "The year 2017");
            set1.setDrawIcons(false);
            int color1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int color2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int color3 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int color4 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            set1.setColors(color1, color2, color3, color4);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "BarChartActivity");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private final RectF onValueSelectedRectF = new RectF();

    //当某个柱子被选中时被调用
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        BarEntry barEntry = (BarEntry) e;
        Log.e("TAG", "x轴:" + barEntry.getX() + "------------" + "y轴:" + barEntry.getY());
    }

    @Override
    public void onNothingSelected() {
    }
}
