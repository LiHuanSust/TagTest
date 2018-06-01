package com.example.tagtest.tables;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.tools.GetDate;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActivityShowDataByMonth extends AppCompatActivity implements OnChartValueSelectedListener {

    private LineChart mChart;
    private Toolbar toolbar;
    private TextView toolBarText;
    private TextView costMoney;
    private TextView salaryMoney;
    private TextView search; //日期选择
    private GetDate date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_by_month);
        ActivityCollector.addActivity(this);
        initialise();
        GetDate date=new GetDate();
        GetDataSomeMonth dataSomeMonth=new GetDataSomeMonth(date.getYear()+"",date.getMonth()+"");
        List<String> list=dataSomeMonth.getCostData();
        costMoney.setText(dataSomeMonth.getCostMoney());
        salaryMoney.setText(dataSomeMonth.getSalaryMoney());
        Log.d("Activity",dataSomeMonth.getCostData().size()+"");
        GetTable myTable=new GetTable();
        myTable.getLineChartBySomeMonth(ActivityShowDataByMonth.this,mChart,dataSomeMonth);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
    public void initialise() //控件初始化
    {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolBarText=(TextView)findViewById(R.id.toolbar_text_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarText.setText("当月消费/收入数据展示");
        mChart = (LineChart) findViewById(R.id.line_chart);
        date=new GetDate();
        costMoney=(TextView)findViewById(R.id.line_chart_cost_money);
        salaryMoney=(TextView)findViewById(R.id.line_chart_salary_money);
        search=(TextView)findViewById(R.id.line_chart_month);
        if(date.getMonth()<10)
            search.setText(date.getYear()+"-"+"0"+date.getMonth());
        else
            search.setText(date.getYear()+"-"+date.getMonth());
        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(ActivityShowDataByMonth.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateTemp, View v) {
                        //date显示的是所有的数据情况，所以需要格式化输出
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM");
                        String format = simpleDateFormat.format(dateTemp);
                        String year;
                        String month;
                        String[] temp=format.split(","); //将年，月,日拿到
                        year=temp[0];
                        month=temp[1];
                        getTable(year,month);
                        search.setText(temp[0]+"-"+temp[1]);

                    }
                }).build();
                pvTime.show();

            }
        });
    }
    private void getTable(final String year,final String month)
    {
        mChart.clear();
        GetDate date=new GetDate();
        GetDataSomeMonth dataSomeMonth=new GetDataSomeMonth(year,month);
        List<String> list=dataSomeMonth.getCostData();
        costMoney.setText(dataSomeMonth.getCostMoney());
        salaryMoney.setText(dataSomeMonth.getSalaryMoney());
        Log.d("Activity",dataSomeMonth.getCostData().size()+"");
        GetTable myTable=new GetTable();
        myTable.getLineChartBySomeMonth(ActivityShowDataByMonth.this,mChart,dataSomeMonth);
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleX() + ", high: " + mChart.getHighestVisibleX());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}



