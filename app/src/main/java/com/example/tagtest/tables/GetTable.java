package com.example.tagtest.tables;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.util.Log;
import android.widget.TextView;

import com.example.tagtest.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/5/6.
 * 对MPAndroidChart的封装
 */

public class GetTable {
    private Typeface mTfLight;
    private Typeface mTfRegular;
    private PieChart pieChart;
    private LineChart lineChart;
    private String money;
    private Activity activity;
    private ArrayList<String> xLabel = new ArrayList<>();
    private GetDataSomeMonth lineChartData; //lineChart的数据
    //获得今日消费或收入的饼状图
    //flag用来标记是消费还是收入
    //初步打算只把每天的消费和收入情况以饼状图展现
    //因为以月显示的话类别太多，在显示上可能会有问题把=吧，先测试一下再说
    //每月的消费类别统计，以直方图展示吧
    public void getPieChartByToday(PieChart mChart, GetData myData, String describe, boolean flag) {
        this.pieChart = mChart;
        myData = (GetDataSomeday) myData;
        if (flag) {
            if (myData.getCostData().size() == 0) {
                mChart.setNoDataText("今天没有消费数据哦~");
                return;
            }

            money = myData.getCostMoney();
        } else {
            if (myData.getSalaryData().size() == 0) {
                mChart.setNoDataText("今天没有收入数据哦~");
                return;
            }
            money = myData.getSalaryMoney();
        }
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextTypeface(mTfLight);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);
        //mChart.setDescription();
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        //设置中间的文字
        pieChart.setCenterText(generateCenterSpannableText(describe));

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //mChart.setOnChartValueSelectedListener(this);


        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Log.d("Get:size", myData.getCostData().size() + "");
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        ArrayList<DataBase> list = new ArrayList<>();
        if (flag) {
            //消费数据
            Collection<DataBase> costData = myData.getCostData().values();
            for (DataBase temp : costData) {
                list.add(temp);
            }
            Log.d("List length:", list.size() + "");
            for (DataBase dataBase : list) {
                entries.add(new PieEntry(Float.parseFloat(dataBase.getMoney()), dataBase.getTypeName()));
            }
        } else {
            //收入数据
            //获取value值
            Collection<DataBase> salaryData = myData.getSalaryData().values();
            for (DataBase temp : salaryData) {
                list.add(temp);
            }
            //把数据加进去
            for (DataBase dataBase : list) {
                entries.add(new PieEntry(Float.parseFloat(dataBase.getMoney()), dataBase.getTypeName()));
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors


        dataSet.setColors(getColors());
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);
        //图表的初始化
       // mChart.invalidate();
        //设置比例图
        Legend l = mChart.getLegend();
        //mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);  //左下边显示
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        l.setWordWrapEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setXEntrySpace(2f);//设置距离饼图的距离，防止与饼图重合
        l.setYEntrySpace(2f);
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
        //设置动画
        mChart.animateXY(1400, 1400);

    }

    //统计当月的消费支出水平
    //以lineChart展现
    //Boolean 标记支出还是消费
    public void getLineChartBySomeMonth(Activity activity,LineChart mChart, GetDataSomeMonth data) {
        lineChart=mChart;
        this.lineChartData=data;
        this.activity=activity;
        mChart.clear();
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        MyMarkerView mv = new MyMarkerView(activity, R.layout.mark_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        //mv.setChartView(mChart); // For bounds control
        //mChart.setMarker(mv); // Set the marker to the chart
        float averageCost=Float.parseFloat(lineChartData.getCostMoney())/lineChartData.getDayNumberOfMonth();
        float averageSalary=Float.parseFloat(lineChartData.getSalaryMoney())/lineChartData.getDayNumberOfMonth();
        LimitLine aveCostLimit = new LimitLine(averageCost, "平均消费");
        aveCostLimit.setLineColor(Color.RED);
        aveCostLimit.setLineWidth(2f);
        aveCostLimit.setTextColor(Color.GRAY);
        aveCostLimit.setTextSize(12f);
        aveCostLimit.enableDashedLine(10f, 10f, 0f);
        LimitLine aveSalaryLimit = new LimitLine(averageSalary, "平均收入");
        aveSalaryLimit.setLineColor(Color.GREEN);
        aveSalaryLimit.setLineWidth(2f);
        aveSalaryLimit.setTextColor(Color.GRAY);
        aveSalaryLimit.setTextSize(12f);
        aveSalaryLimit.enableDashedLine(10f, 10f, 0f);
        // .. and more styling options
        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);
        xl.setAxisMaximum(lineChartData.getDayNumber()+1);
        final ArrayList<String> day=new ArrayList<>();
        int dayNumber=data.getDayNumber();
        for(int i=0;i<=dayNumber;i++)
        {
           // int temp=i+1;
           day.add(i+"");
        }
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return day.get((int)value%day.size());
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
        //左边x轴数据是否反转排列
        leftAxis.setInverted(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.addLimitLine(aveCostLimit);
        leftAxis.addLimitLine(aveSalaryLimit);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        // add data
        setData();

        // // restrain the maximum scale-out factor
        // mChart.setScaleMinima(3f, 3f);
        //
        // // center the view to a specific position inside the chart
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // dont forget to refresh the drawing
        mChart.invalidate();
      }

    private void setData() {

        ArrayList<Entry> entries1 = new ArrayList<Entry>();
        ArrayList<Entry> entries2 = new ArrayList<Entry>();
        List<String> costDataList=lineChartData.getCostData();
        List<String> salaryDataList=lineChartData.getSalaryData();
        int len=lineChartData.getDayNumber();
        for (int i = 0; i <len ; i++) {
            float xVal = (float) (i);
            //float yVal = (float) (dataTemp[i]);
            entries1.add(new Entry(i+1, Float.parseFloat(costDataList.get(i))));
        }
        for (int i = 0; i <len ; i++) {
            float xVal = (float) (i);
            //float yVal = (float) (dataTemp[i]);
            entries2.add(new Entry(i+1, Float.parseFloat(salaryDataList.get(i))));
        }

        // sort by x-value
        //Collections.sort(entries, new EntryXComparator());

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries1, "消费数据");
        LineDataSet set2=new LineDataSet(entries2,"收入数据");
        set1.setLineWidth(1.5f);
        set1.setCircleRadius(4f);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set2.setLineWidth(1.5f);
        set2.setCircleRadius(4f);
        set2.setColor(Color.GREEN);
        set2.setCircleColor(Color.GREEN);
        // create a data object with the datasets
        LineData data = new LineData(set1);
        data.addDataSet(set2);
        // set data
        lineChart.setData(data);
    }

    private SpannableString generateCenterSpannableText(String describe) {

        SpannableString s = new SpannableString(describe);
       /* s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);*/
        return s;
    }
   private ArrayList<Integer> getColors()
   {
       ArrayList<Integer> colors = new ArrayList<Integer>();

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
       return colors;
   }
}
class MyMarkerView extends MarkerView {
        private TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);

            tvContent = (TextView) findViewById(R.id.tvContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvContent.setText(Utils.formatNumber(e.getX(), 0, true)+"号");
            super.refreshContent(e, highlight);
        }

        @Override
        public MPPointF getOffset() {
            return new MPPointF(-(getWidth() / 2), -getHeight());
        }
    }
