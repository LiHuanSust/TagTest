package com.example.tagtest;

import java.util.Calendar;

/**
 * Created by MyFirstPC on 2018/3/27.
 * add data for year,month,day,hour,minute,second
 */

public class GetDate {
    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    public GetDate()
    {
        calendar=Calendar.getInstance();
    }
    public int getYear()
    {
        return calendar.get(Calendar.YEAR);
    }
    public int getMonth()
    {
        return calendar.get(Calendar.MONTH)+1;
    }
    public int getDay()
    {
        return calendar.get(Calendar.DATE);
    }
    public int getHour()
    {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    public int getMinutes()
    {
        return calendar.get(Calendar.MINUTE);
    }
    public int getSeconds()
    {
        return calendar.get(Calendar.SECOND);
    }
    public String getMonthDay() //返回月与日
    {
        return getMonth()+"-"+getDay();
    }
    public String partToString()  //年月日 String化
    {
        int month=calendar.get(Calendar.MONTH)+1;
        return calendar.get(Calendar.YEAR)+"-"+ month+"-"+calendar.get(Calendar.DATE);
    }
    public String hourMinuteSecondString() //时，分，秒 String化
    {
        return getHour()+":"+getMinutes()+":"+getSeconds();
    }
    public String allToString() //年月日，时分秒 String化
    {
        return partToString()+" "+hourMinuteSecondString()+"  ";
    }
    public String getYearMonth()
    {
        return getYear()+","+getMonth();
    }

}
