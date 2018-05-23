package com.example.tagtest.tables;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.util.Log;
import android.widget.TextView;

import com.example.tagtest.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
    private BarChart barChart;
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
        //只显示百分比，不显示其名字
        pieChart.setDrawSliceText(false);
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
        //设置饼图数据标识是否在圆外显示
        //dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

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
    public void getBarChatByAccount(BarChart mChart,ArrayList<GetDataByAccount.AccountDataBase> data)
    {
        if(data==null)
        {
            mChart.setNoDataText("当月没有数据哦~");
            return;
        }
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        Description description=new Description();
        description.setText("当月各账户使用情况（单位：元）");
        mChart.setDescription(description);
        //mChart.getDescription().setEnabled(false);
       // mChart.setContentDescription("单位：元");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

       // IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(0);
        xAxis.setAxisMaximum(12);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        //xAxis.setLabelCount(7);
        //xAxis.setValueFormatter(xAxisFormatter);

        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        //leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        rightAxis.setTypeface(mTfLight);
        //rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        //rightAxis.setSpaceTop(15f);
        //rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);



        ArrayList<BarEntry> yValues1 = new ArrayList<BarEntry>(); //存放消费数据
        ArrayList<BarEntry> yValues2=new ArrayList<>(); //存放收入数据
        final ArrayList<String> xValues=new ArrayList<>(); //存放账户名
         int num=0;
         for(GetDataByAccount.AccountDataBase tempAccountData:data)
         {
             Log.d("TempName",tempAccountData.name);
             xValues.add(tempAccountData.name);
             yValues1.add(new BarEntry(num,Float.parseFloat(tempAccountData.costMoney)));
             yValues2.add(new BarEntry(num,100+num*num));
             num++;
         }

        BarDataSet costSet,salarySet;



        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            costSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            salarySet=(BarDataSet)mChart.getData().getDataSetByIndex(1);
            costSet.setValues(yValues1);
            salarySet.setValues(yValues2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            costSet = new BarDataSet(yValues1, "消费");
            costSet.setDrawIcons(false);
            //红色
            costSet.setColors(Color.rgb(255, 102, 0));
            //costSet.setBarBorderWidth(2f);
            salarySet=new BarDataSet(yValues2,"收入");
            salarySet.setDrawIcons(false);
            //绿色
            salarySet.setColors(Color.rgb(104, 241, 175));
            //salarySet.setBarBorderWidth(2f);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(costSet);
            dataSets.add(salarySet);

            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(10f);
            //barData.setValueTypeface(mTfLight);
            //barData.setBarWidth(0.2f);
            mChart.setData(barData);
        }
        float groupSpace=0.24f;
        float barSpace=0.1f; //*2
        float barWidth=0.28f;//*2
        //0.24+0.1*2+0.28*2=1.0
        mChart.getXAxis().setAxisMinimum(0);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        mChart.getXAxis().setAxisMaximum(0 + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * num);
        mChart.groupBars(0, groupSpace, barSpace);
        mChart.animateXY(1000,1400);
        //mChart.groupBars(0,0.28f,0.12f);
        //x轴坐标内容设置
        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            int len=0;
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
               float tempValue=value;
                int temp=(int)value;
                //从0开始，奇数显示，1，3/
                if(temp==1)  //如果只含有2个的话，就会出现1%2==1的情况，所以手动设置坐标1下面的显示
                    return xValues.get(0);
                if(temp%2!=0)
                  return xValues.get(temp%xValues.size());
                return "";

            }
        });
        mChart.invalidate();


    }
    public void getHorizontalBarChart(HorizontalBarChart mChart, List<GetDataByAccount.AccountHorizontalDataBase> dataList) {
        if (dataList == null) {
            mChart.setNoDataText("当月没有数据哦~");
            return;
        }
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        Description description = new Description();
        description.setText("各账户统计（单位：元/次）");
        mChart.setDescription(description);
        //mChart.getDescription().setEnabled(false);
        // mChart.setContentDescription("单位：元");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.animateY(2500);
        // mChart.setDrawYLabels(false);

        // IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final ArrayList<String> xValues=new ArrayList<>();
        for(GetDataByAccount.AccountHorizontalDataBase temp:dataList)
        {
            xValues.add(temp.name);
        }
        //设置x轴字体显示角度，-60表示逆时针旋转60度
        //xAxis.setLabelRotationAngle(-60);
       // xAxis.setAxisMaximum(0);
       // xAxis.setAxisMaximum(6);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        //xAxis.setSpaceMin(0.25f);
        //xAxis.setLabelCount(7);
        //xAxis.setValueFormatter(xAxisFormatter);

        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(6, false);
        //leftAxis.setValueFormatter(custom);
        //leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int x=(int)value;
                return xValues.get(x%xValues.size());
            }
        });
        //rightAxis.setLabelCount(8, false);
        //rightAxis.setValueFormatter(custom);
        //rightAxis.setSpaceTop(15f);
        //rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        float barWidth = 0.35f;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < dataList.size(); i++) {
            yVals1.add(new BarEntry(i, dataList.get(i).num));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "DataSet 1");

            set1.setDrawIcons(false);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(barWidth);
            mChart.setData(data);
        }
       // mChart.setFitBars(true);
        mChart.invalidate();

    }
    public void getBarChartWithYear(BarChart mChart, List<GetDataByYear.ShowDataByYearDataBase> dataList,String descriptText)
    {
        if(dataList==null)
        {
            mChart.setNoDataText("真厉害，这一年都没有花一分钱哦~");
            return;
        }
        setBarChart(mChart,descriptText);
        final ArrayList<String> xValues=new ArrayList<>();
        ArrayList<BarEntry> costValues=new ArrayList<>();
        ArrayList<BarEntry> salaryValues=new ArrayList<>();
        int i=2;
        for(GetDataByYear.ShowDataByYearDataBase tempData:dataList)
        {
            xValues.add(tempData.getMonth()+"月");
            costValues.add(new BarEntry(i,/*Float.parseFloat(tempData.getCostMoney())*/500*i+5));
            salaryValues.add(new BarEntry(i,/*Float.parseFloat(tempData.getSalaryMoney()*/1000*i+i));
            i++;
        }
        mChart.getXAxis().setLabelCount(12);
        //mChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        mChart.getXAxis().setCenterAxisLabels(true);
         mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.d("Values","value="+value);
                /*if(value<2)
                    return "";*/
                int temp=((int)value-2)/2;
                if(temp==0)
                {
                    return xValues.get(0);
                }
                if(temp<0 || temp>12)
                    return "";
                return xValues.get(temp%xValues.size());
               //return xValues.get(((int)value-1)%xValues.size());
            }
        });
        BarDataSet costSet,salarySet;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            costSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            salarySet=(BarDataSet)mChart.getData().getDataSetByIndex(1);
            costSet.setValues(costValues);
            salarySet.setValues(salaryValues);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            costSet = new BarDataSet(costValues, "消费");
            costSet.setDrawIcons(false);
            //红色
            costSet.setColors(Color.rgb(255, 102, 0));
            //costSet.setBarBorderWidth(2f);
            salarySet=new BarDataSet(salaryValues,"收入");
            salarySet.setDrawIcons(false);
            //绿色
            salarySet.setColors(Color.rgb(104, 241, 175));
            //salarySet.setBarBorderWidth(2f);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(costSet);
            dataSets.add(salarySet);

            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(10f);
            //barData.setValueTypeface(mTfLight);
            //barData.setBarWidth(0.2f);
            mChart.setData(barData);
        }
        float groupSpace=0.16f;
        float barSpace=0.07f; //*2
        float barWidth=0.35f;//*2
        //0.24+0.1*2+0.28*2=1.0
        //mChart.getXAxis().setAxisMinimum(0);
        //mChart.getXAxis().setAxisMaximum(26);

        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
       // mChart.getXAxis().setAxisMaximum(1+ mChart.getBarData().getGroupWidth(groupSpace, barSpace) * 12);
        //从2开始是因为每组有2个数据，而且这与x轴下方怎么显示标签密切相关
        //因为上面添加数据的时候，i也是从2开始的
        //主要因为0，1除以2都为0的缘故。。具体也不知道为啥，反正是试出来的
        mChart.groupBars(2, groupSpace, barSpace);
        mChart.getBarData().setBarWidth(barWidth);
        mChart.getXAxis().setAxisMinimum(2);
        mChart.getXAxis().setAxisMaximum(dataList.size()*2+2);
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
        set1.setColor(Color.rgb(255, 102, 0));
        set1.setCircleColor(Color.rgb(255, 102, 0));
        set2.setLineWidth(1.5f);
        set2.setCircleRadius(4f);
        //这种绿色与红色看起来好点，没有那么刺眼
        set2.setColor(Color.rgb(104, 241, 175));
        set2.setCircleColor(Color.rgb(104, 241, 175));
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

       for (int c : ColorTemplate.JOYFUL_COLORS)
           colors.add(c);

       for (int c : ColorTemplate.COLORFUL_COLORS)
           colors.add(c);

       for (int c : ColorTemplate.LIBERTY_COLORS)
           colors.add(c);

       for (int c : ColorTemplate.PASTEL_COLORS)
           colors.add(c);
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
       colors.add(ColorTemplate.getHoloBlue());
       return colors;
   }
    private void setBarChart(BarChart mChart,String descriptText)
    {
        /*
        * @param mChart 需要设置的图表
        * @param descriptText 表的描述语句
        * barChart的基本设置
        *背景，网格线，及x轴，y轴的设置
        *该函数是barChart公共属性的设置，提高代码的复用性
        */
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        Description description = new Description();
        description.setText(descriptText);
        mChart.setDescription(description);
        //mChart.getDescription().setEnabled(false);
        // mChart.setContentDescription("单位：元");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.animateY(2500);
        // mChart.setDrawYLabels(false);

        // IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴字体显示角度，-60表示逆时针旋转60度
        //xAxis.setLabelRotationAngle(-60);
        // xAxis.setAxisMaximum(0);
        // xAxis.setAxisMaximum(6);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(true);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setCenterAxisLabels(true);
        //xAxis.setSpaceMin(0.25f);
        //xAxis.setLabelCount(7);
        //xAxis.setValueFormatter(xAxisFormatter);

        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(6, false);
        //leftAxis.setValueFormatter(custom);
        //leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);

        //图例的设置
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

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
