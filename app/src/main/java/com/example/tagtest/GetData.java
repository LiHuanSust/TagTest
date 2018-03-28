package com.example.tagtest;

import java.util.Calendar;

/**
 * Created by MyFirstPC on 2018/3/27.
 * add data for year,month,day,hour,minute,second
 */

public class GetData {
    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    public GetData()
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
        return calendar.get(Calendar.HOUR);
    }
    public int getMinutes()
    {
        return calendar.get(Calendar.MINUTE);
    }
    public int getSeconds()
    {
        return calendar.get(Calendar.SECOND);
    }
    public String partToString()
    {
        return ""+calendar.get(Calendar.YEAR)+" "+calendar.get(Calendar.MONTH)+1+" "+calendar.get(Calendar.DATE);
    }
    public String allToString()
    {
        return partToString()+" "+calendar.get(Calendar.HOUR)+" "+calendar.get(Calendar.MINUTE)+" "+calendar.get(Calendar.SECOND);
    }

}
