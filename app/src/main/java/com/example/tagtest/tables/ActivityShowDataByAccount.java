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
import com.example.tagtest.tools.GetDate;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityShowDataByAccount extends AppCompatActivity {
    private GetDate dateNow;
    private BarChart barChart;
    private HorizontalBarChart barChart1;
    private Toolbar toolbar;
    private TextView toolBarText;
    private TextView costMoney;
    private TextView salaryMoney;
    private TextView searchByMonth;
    private String year;
    private String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_by_account);
        initialise();
        GetTable getTable=new GetTable();
        GetDataByAccount getDataByAccount=new GetDataByAccount(dateNow.getYear()+"",dateNow.getMonth()+"");
        getTable.getBarChatByAccount(barChart,getDataByAccount.getData());
        costMoney.setText(getDataByAccount.getCostMoney());
        salaryMoney.setText(getDataByAccount.getSalaryMoney());
      //  List<GetDataByAccount.AccountHorizontalDataBase> list=getDataByAccount.getHorbiziontalData();
        getTable.getHorizontalBarChart(barChart1,getDataByAccount.getHorbiziontalData());


    }
    private void initialise()
    {
        dateNow=new GetDate();
        barChart=(BarChart)findViewById(R.id.bar_chart);
        barChart1=(HorizontalBarChart)findViewById(R.id.horizaontal_bar_chart);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarText=(TextView)findViewById(R.id.toolbar_text_view);
        costMoney=(TextView)findViewById(R.id.bar_chart_cost_money);
        salaryMoney=(TextView)findViewById(R.id.bar_chart_salary_money);
        searchByMonth=(TextView)findViewById(R.id.bar_chart_month);
        toolBarText.setText("当月各账户消费/收入情况统计");
        searchByMonth.setText(dateNow.getYear()+"-"+dateNow.getMonth());
        searchByMonth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(ActivityShowDataByAccount.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date dateTemp, View v) {
                        //date显示的是所有的数据情况，所以需要格式化输出
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                        String format = simpleDateFormat.format(dateTemp);
                        String[] temp=format.split("-"); //将年，月,日拿到
                        year=temp[0];
                        month=temp[1];
                        searchByMonth.setText(format);
                        barChart.clear();
                        GetTable getTable=new GetTable();
                        GetDataByAccount getDataByAccount=new GetDataByAccount(year,month);
                        ArrayList<GetDataByAccount.AccountDataBase> dataList=getDataByAccount.getData();
                        costMoney.setText(getDataByAccount.getCostMoney());
                        salaryMoney.setText(getDataByAccount.getSalaryMoney());
                        getTable.getBarChatByAccount(barChart,dataList);

                    }
                }).build();
                pvTime.show();

            }
        });
    }
}
