package com.example.tagtest;
/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class MyDate {
    private String type;
    private String data;
    private String money;
    public MyDate()
    {}
    public MyDate(String type,String data,String money)
    {
        this.type=type;
        this.data=data;
        this.money=money;
    }
    public String getType()
    {
        return type;
    }
    public String getData()
    {
        return data;
    }
    public String getMoney()
    {
        return money;
    }
    public void setType(String type)
    {
        this.type=type;
    }
    public void setData(String data)
    {
        this.data=data;
    }
    public void setMoney(String money)
    {
        this.money=money;
    }

}

