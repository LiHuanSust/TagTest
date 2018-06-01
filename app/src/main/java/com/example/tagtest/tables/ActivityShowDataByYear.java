package com.example.tagtest.tables;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.tools.GetDate;
import com.github.mikephil.charting.charts.BarChart;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityShowDataByYear extends AppCompatActivity {
    private BarChart barChart;
    private GetDate dateNow;
    private Toolbar toolbar;
    private TextView toolBarTextView;
    private TextView costMoney;
    private TextView salaryMoney;
    private TextView selectDataByYear;
    private GetTable getTable;
    private GetDataByYear getDataByYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_by_year);
        ActivityCollector.addActivity(this);
        initialise();
        GetDataByYear getDataByYear=new GetDataByYear(dateNow.getYear()+"");
        getTable=new GetTable();
        getTable.getBarChartWithYear(barChart,getDataByYear.getDataByYear(),"当年消费收入情况");
        salaryMoney.setText(getDataByYear.getSalaryMoneyOfYear());
        costMoney.setText(getDataByYear.getCostMoneyOfYear());
        selectDataByYear.setText(dateNow.getYear()+"年");
    }

    public void initialise()
    {
        barChart=(BarChart)findViewById(R.id.show_data_by_year_bar_chart);
        dateNow=new GetDate();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarTextView=(TextView)findViewById(R.id.toolbar_text_view);
        costMoney=(TextView)findViewById(R.id.bar_chart_cost_money_year);
        salaryMoney=(TextView)findViewById(R.id.bar_chart_salary_money_year);
        selectDataByYear=(TextView)findViewById(R.id.bar_chart_search_year);
        selectDataByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(ActivityShowDataByYear.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateTemp, View v) {
                        //date显示的是所有的数据情况，所以需要格式化输出
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
                        String year = simpleDateFormat.format(dateTemp);
                        selectDataByYear.setText(year+"年");
                        barChart.clear();
                        getDataByYear=null;
                        getDataByYear=new GetDataByYear(year);
                        getTable.getBarChartWithYear(barChart,getDataByYear.getDataByYear(),"该年收支情况");
                    }
                }).build();
                pvTime.show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
