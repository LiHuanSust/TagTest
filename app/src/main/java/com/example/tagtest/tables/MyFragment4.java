package com.example.tagtest.tables;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.tools.GetDate;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by MyFirstPC on 2018/3/25.
 */

public class MyFragment4 extends Fragment {
    //private BarChart chat;
    private PieChart mChart;
    private Context context;
    private Typeface test;
    private Typeface mTfLight;
    private Typeface mTfRegular;
    private GetDate date;
   @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourth_fragment, container, false);
        mChart=view.findViewById(R.id.chart);
        inilite();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }
   public void inilite() {
       date=new GetDate();
       mChart.setUsePercentValues(true);
       mChart.getDescription().setEnabled(false);
       mChart.setExtraOffsets(5, 10, 5, 5);

       mChart.setDragDecelerationFrictionCoef(0.95f);

       mChart.setCenterTextTypeface(mTfLight);
      // mChart.setCenterText(generateCenterSpannableText());

       mChart.setDrawHoleEnabled(true);
       mChart.setHoleColor(Color.WHITE);

       mChart.setTransparentCircleColor(Color.WHITE);
       mChart.setTransparentCircleAlpha(110);

       mChart.setHoleRadius(58f);
       mChart.setTransparentCircleRadius(61f);

       mChart.setDrawCenterText(true);

       mChart.setRotationAngle(0);
       // enable rotation of the chart by touch
       mChart.setRotationEnabled(true);
       mChart.setHighlightPerTapEnabled(true);

       // mChart.setUnit(" €");
       // mChart.setDrawUnitsInChart(true);

       // add a selection listener
       //mChart.setOnChartValueSelectedListener(this);

       setData(4, 100);

       //mChart.animateY(1400, Easing.EaseInOutQuad);
       // mChart.spin(2000, 0, 360);

       //mSeekBarX.setOnSeekBarChangeListener(this);
       //mSeekBarY.setOnSeekBarChangeListener(this);

       Legend l = mChart.getLegend();
       l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
       l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
       l.setOrientation(Legend.LegendOrientation.VERTICAL);
       l.setDrawInside(false);
       l.setXEntrySpace(7f);
       l.setYEntrySpace(0f);
       l.setYOffset(0f);

       // entry label styling
       mChart.setEntryLabelColor(Color.WHITE);
       mChart.setEntryLabelTypeface(mTfRegular);
       mChart.setEntryLabelTextSize(12f);
   }
    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        String[] type={"支出","收入"};
        List<MyData> dataTodayCost= DataSupport.where("year=? and month=? and day=? and type=?",date.getYear()+"",date.getMonth()+"",date.getDay()+"","1").find(MyData.class);
        Set<Test> testSet = new HashSet<>();
        {
            for(MyData temp:dataTodayCost)
            {
               Test a=new Test(temp.getTypeSelect(),temp.getMoney());

            }
        }
        /*for(int i=0;i<dataMap.size();i++) {

            entries.add(new PieEntry(Float.parseFloat(),type[i++]));
                    //mParties[i % mParties.length],
                   // getResources().getDrawable(R.drawable.star)));
        }*/

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

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

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
    class Test
    {
        String type;
        String money;
        public Test(String type,String money)
        {
          this.type=type;
          this.money=money;
        }
        public boolean isSame(final Test temp)
        {
            if(this.type.equals(temp.type))
                return true;
            return false;
        }

    }
}