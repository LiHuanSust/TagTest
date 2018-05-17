package com.example.tagtest.tables;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.tagtest.R;
import com.example.tagtest.tools.GetDate;
import com.github.mikephil.charting.charts.PieChart;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by MyFirstPC on 2018/3/25.
 */

public class MyFragment4 extends Fragment implements View.OnClickListener{
    //显示当日消费数据
    private PieChart pieChart1;
    //显示当日收入数据
    private PieChart pieChart2;
    private Context context;
    private Typeface test;
    private Typeface mTfLight;
    private Typeface mTfRegular;
    private GetDate date;
    private GetTable table1;
    private GetTable table2;
    private ImageView toLastDay;
    private ImageView toNextDay;
    private TextView dayNow;
    private DateInfor dateInfor=null;
   @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourth_fragment, container, false);
        setHasOptionsMenu(true);
        pieChart1=view.findViewById(R.id.pie_chart1);
        pieChart2=view.findViewById(R.id.pie_chart2);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialise();
        GetDate date=new GetDate();
        getTable(date.getYear(),date.getMonth(),date.getDay());

   }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment4_menu,menu);
    }

    @Override
    public void onClick(View v) {
           switch (v.getId())
           {
               case R.id.to_left:
                   toLastDay(dayNow.getText().toString());
                   if(dateInfor!=null)
                   {
                       if(dateInfor.getMonth()<10)
                          dayNow.setText("0"+dateInfor.getMonth()+"月"+dateInfor.getDay()+"日");
                       else
                           dayNow.setText(dateInfor.getMonth()+"月"+dateInfor.getDay()+"日");
                       getTable(dateInfor.getYear(),dateInfor.getMonth(),dateInfor.getDay());
                   }
                   break;
               case R.id.to_right:
                   toNextDay(dayNow.getText().toString());
                   if(dateInfor!=null)
                   {
                       if(dateInfor.getMonth()<10)
                           dayNow.setText("0"+dateInfor.getMonth()+"月"+dateInfor.getDay()+"日");
                       else
                           dayNow.setText(dateInfor.getMonth()+"月"+dateInfor.getDay()+"日");
                       getTable(dateInfor.getYear(),dateInfor.getMonth(),dateInfor.getDay());
                   }
                   break;
               case R.id.day_now:
                   TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                       @Override
                       public void onTimeSelect(Date dateTemp, View v) {
                           //date显示的是所有的数据情况，所以需要格式化输出
                           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM,dd");
                           String format = simpleDateFormat.format(dateTemp);
                           String year;
                           String month;
                           String day;
                           String[] temp=format.split(","); //将年，月,日拿到
                           year=temp[0];
                           month=temp[1];
                           day=temp[2];
                           Log.d("TimePicker",year+","+month+","+day);
                           date.setYear(Integer.parseInt(temp[0]));
                           date.setMonth(Integer.parseInt(temp[1]));
                           date.setDay(Integer.parseInt(temp[2]));
                           dayNow.setText(temp[1]+"月"+temp[2]+"日");
                           dateInfor=null;
                           dateInfor=new DateInfor(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
                           getTable(dateInfor.getYear(),dateInfor.getMonth(),dateInfor.getDay());
                       }
                   }).build();
                   pvTime.show();
                   break;
               default:
                   break;
           }
    }

    private void initialise()
    {
        toLastDay=(ImageView)getActivity().findViewById(R.id.to_left);
        toNextDay=(ImageView)getActivity().findViewById(R.id.to_right);
        dayNow=(TextView)getActivity().findViewById(R.id.day_now);
        date=new GetDate();
        if(date.getDay()>10)
           dayNow.setText(date.getMonth()+"月"+date.getDay()+"日");
        else
            dayNow.setText("0"+date.getMonth()+"月"+date.getDay()+"日");
        toLastDay.setOnClickListener(this);
        toNextDay.setOnClickListener(this);
        dayNow.setOnClickListener(this);
    }
    private void toLastDay(String dayNowTemp)
    {
       // int year=date.getYear();
        //int month=date.getMonth();
        //int day=date.getDay();
        dateInfor=null;
        String[] monthAndDay=dayNowTemp.split("月");
        if(monthAndDay.length==2)
        {
            int month = Integer.parseInt(monthAndDay[0]);
            String[] dayTemp=monthAndDay[1].split("日");
            int day= Integer.parseInt(dayTemp[0]);
            Log.d("MyFragment",month+","+day);
            GetDataSomeMonth getDataSomeMonth;
            if(day==1)
            {
                //一月一号前一天，年都变了
                if(month==1)
                {
                    int newYear=date.getYear()-1;
                    int newMonth=12;
                    getDataSomeMonth=new GetDataSomeMonth(newYear+"",newMonth+"");
                    dateInfor=new DateInfor(newYear,newMonth,getDataSomeMonth.getDayNumberOfMonth());
                }
                //不是一月，移动不跨年
                else
                {
                    int newMonth=month-1;
                    getDataSomeMonth=new GetDataSomeMonth(date.getYear()+"",newMonth+"");
                    dateInfor=new DateInfor(date.getYear(),newMonth,getDataSomeMonth.getDayNumberOfMonth());
                }
            }
            else
            {
                dateInfor=new DateInfor(date.getYear(),month,day-1);
            }



        }
        else
        {
            Log.d("MyFragment4","dayNowTemp split is wrong");
        }

    }
    public void toNextDay(String dayNowTemp)
    {
        dateInfor=null;
        String[] monthAndDay=dayNowTemp.split("月");
        if(monthAndDay.length==2)
        {
            int month = Integer.parseInt(monthAndDay[0]);
            String[] dayTemp=monthAndDay[1].split("日");
            int day= Integer.parseInt(dayTemp[0]);
            Log.d("MyFragment",month+","+day);
            int year=date.getYear();
            GetDataSomeMonth getDataSomeMonth=new GetDataSomeMonth(year+"",month+"");
            if(day==getDataSomeMonth.getDayNumberOfMonth())
            {
                //12月最后一天，再过一天，年都变了
                if(month==12)
                {
                    int newYear=date.getYear()+1;
                    int newMonth=1;
                    dateInfor=new DateInfor(newYear,newMonth,1);
                }
                //不是12月，不跨年
                else
                {
                    dateInfor=new DateInfor(year,month+1,1);
                }
            }
            else
            {
                dateInfor=new DateInfor(year,month,day+1);
            }
        }
        else
        {
            Log.d("MyFragment4","dayNowTemp split is wrong");
        }
    }
    //获取表格，重新刷新表格内容
    public void getTable(int year,int month,int day)
    {
        pieChart1.clear();
        pieChart2.clear();
        table1=new GetTable();
        table2=new GetTable();
        GetDataSomeday getDataToday=new GetDataSomeday(year+"",month+"",day+"");
        Log.d("getDataToday",getDataToday.getCostData().size()+"");
        table1.getPieChartByToday(pieChart1,getDataToday,"今日消费信息视图",true);
        table2.getPieChartByToday(pieChart2,getDataToday,"今日支出视图",false);
    }

    //存储日期信息，方便返回
    class DateInfor
    {
        int year;
        int month;
        int day;
        public DateInfor()
        {}
        public DateInfor(int year,int month,int day)
        {
            this.year=year;
            this.month=month;
            this.day=day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
}