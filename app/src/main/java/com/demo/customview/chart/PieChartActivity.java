package com.demo.customview.chart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.customview.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends DemoBase implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private PieChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_piechart);

        setTitle("PieChartActivity");

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarY = findViewById(R.id.seekBar2);

        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);
        //使用百分比数值
        chart.setUsePercentValues(true);
        //显示描述信息
        chart.getDescription().setEnabled(true);
        //设置额外的偏移量
        chart.setExtraOffsets(5, 10, 5, 5);
        //设置拖拽减速摩擦系数, 外圈旋转时的阻力
        chart.setDragDecelerationFrictionCoef(0.95f);
        //设置圈中间字体
        chart.setCenterTextTypeface(tfLight);
        //设置圈中间显示的字
        chart.setCenterText(generateCenterSpannableText());
        chart.setCenterTextSize(18f);
        chart.setCenterTextColor(Color.LTGRAY);

        //是否绘制中间的空洞
        chart.setDrawHoleEnabled(true);
        //设置空洞颜色
        chart.setHoleColor(Color.WHITE);

        //设置包裹空洞的一小圈的颜色
        chart.setTransparentCircleColor(Color.WHITE);
        //设置包裹空洞的一小圈的透明度
        chart.setTransparentCircleAlpha(110);
        //设置空洞的半径
        chart.setHoleRadius(58f);
        //设置包裹空洞的一小圈的半径
        chart.setTransparentCircleRadius(61f);
        //是否显示中间的字
        chart.setDrawCenterText(true);
        //设置旋转开始角度
        chart.setRotationAngle(0);
        //通过触摸使图表旋转
        chart.setRotationEnabled(true);
        //是否显示选中的为高亮显示
        chart.setHighlightPerTapEnabled(true);

        // 添加一个选择监听器
        chart.setOnChartValueSelectedListener(this);

        seekBarX.setProgress(4);
        seekBarY.setProgress(10);

        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        //得到图例
        Legend l = chart.getLegend();
        //设置图例位置
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        //设置图例垂直显示
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        //设置图例x轴间隙
        l.setXEntrySpace(7f);
        //设置图例y轴间隙
        l.setYEntrySpace(0f);
        //设置图例y轴偏移量
        l.setYOffset(0f);

        // entry label styling 输入标签样式, 饼状图上每份显示的标签样式
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);
    }

    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        //注意:当条目被添加到条目数组中时，条目的顺序决定了它们在图表中心的位置。
        for (int i = 0; i < count; i++) {
            //每片的数值
            float value = (float) (Math.random() * range);//Math.random()取值0-1之前的double数
            //每片的标签名字
            String label = parties[i % parties.length];
            Drawable drawable = getResources().getDrawable(R.drawable.star);

            PieEntry pieEntry = new PieEntry(value, label, drawable);
            entries.add(pieEntry);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);
        //每片之间的间隔
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        //被选中的片变大多少
        dataSet.setSelectionShift(5f);

        // 添加很多颜色
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        //设置选用颜色集,为每片设置不同的颜色
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        //每片显示的百分比数值字体大小
        data.setValueTextSize(11f);
        //每片显示的百分比数值字体颜色
        data.setValueTextColor(Color.WHITE);
        //设置字体
        data.setValueTypeface(tfLight);
        chart.setData(data);

        // 撤销所有的亮点
        chart.highlightValues(null);

        chart.invalidate();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "PieChartActivity");
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("MPAndroidChart");
        return s;
    }

    //某片被选中时被回调,
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        PieDataSet pieDataSet = (PieDataSet) chart.getData().getDataSet();
        List<PieEntry> pieEntryList = pieDataSet.getValues();
        float totalValues = 0.0f;
        for (int i = 0; i < pieEntryList.size(); i++) {
            PieEntry pieEntry = pieEntryList.get(i);
            float value = pieEntry.getValue();
            totalValues += value;
        }
        PieEntry pieEntry = (PieEntry) e;
        float value = pieEntry.getValue();
        String label = pieEntry.getLabel();
        String v = value / totalValues * 100 + "%";

        Log.e("TAG", "label:" + label);
        Log.e("TAG", "value:" + value);
        Log.e("TAG", "百分比数值:" + v);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
