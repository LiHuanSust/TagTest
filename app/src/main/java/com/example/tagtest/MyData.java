package com.example.tagtest;

import org.litepal.crud.DataSupport;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class MyData extends DataSupport {
    private String user; //用户id
    private boolean type; //消费或收入的标识 当type为true表示支出，当为false表示收入
    private String typeSelect; //消费或收入的具体类别
    private String solution; //付款方式或收入方式
    private int year; //年
    private int month; //月
    private int day;//日
    private String hourMinuteSecond; //时分秒的字符串形式
    private float money;//具体金额,
    private String bank; //银行选择
    private String remarks;
   /* public MyData(String user,boolean type,String typeSelect,String hourMinuteSecond,float money)
    {
        this.user=user;
        this.type=type;

    }*/
    public MyData()
    {}
    public MyData(String user,boolean type,String typeSelect,String solution,int year,int month,int day,String hourMinuteSecond,float money,String bank,String remarks) //此接口是数据库存储调用
    {
        this.user=user;
        this.type=type;
        this.typeSelect=typeSelect;
        this.solution=solution;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hourMinuteSecond=hourMinuteSecond;
        this.money=money;
        this.bank=bank;
        this.remarks=remarks;

    }
    public String getUser()
    {
        return user;
    }
    public boolean getType()
    {
        return type;
    }
    public String getTypeSelect()
    {
        return typeSelect;
    }
    public String getSolution()
    {
        return solution;
    }
    public int getYear(){return year;}
    public int getMonth(){return month;}
    public int getDay(){return day;}
    public String getHourMinuteSecond()
    {
        return hourMinuteSecond;
    }
    public float getMoney()
    {
        return money;
    }
    public String getBank()
    {
        return bank;
    }
    public String getRemarks()
    {
        return remarks;
    }
    public void setUser(String user)
    {
        this.user=user;
    }
    public void setType(boolean type)
    {
        this.type=type;
    }
    public void setTypeSelect(String type)
    {
        this.typeSelect=type;
    }
    public void setSolution(String solution)
    {
        this.solution=solution;
    }
    public void setYear(int year)
    {
        this.year=year;
    }
    public void setMonth(int month){this.month=month;}
    public void setDay(int day){this.day=day;}
    public void setMoney(float money)
    {
        this.money=money;
    }
    public void setBank(String bank)
    {
        this.bank=bank;
    }
    public void setRemarks(String remarks)
    {
        this.remarks=remarks;
    }
    public String dateToString()
    {
        return getYear()+"."+getMonth()+"."+getDay()+" "+getHourMinuteSecond();
    }
    @Override
    public String toString()
    {

       return getUser()+","+getType()+","+getTypeSelect()+","+getSolution()+","+getMoney()+
              ","+getBank()+","+getRemarks()+","+getYear()+","+getMonth()+","+getDay()+","+getHourMinuteSecond()+"\n";
    }

}

