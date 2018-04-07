package com.example.tagtest;
/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class MyDate {
    private String user; //用户id
    private boolean type; //消费或收入的标识 当type为true表示支出，当为false表示收入
    private String typeSelect; //消费或收入的具体类别
    private String solution; //付款方式或收入方式
    private String data; //日期
    private String money;//具体金额
    private String bank; //银行选择
    private String remarks;
    public MyDate()
    {}
    public MyDate(String typeSelect,String data,String money) //此接口是为首页展示提供数据
    {
        this.typeSelect=typeSelect;
        this.data=data;
        this.money=money;
    }
    public MyDate(String user,boolean type,String typeSelect,String solution,String data,String money,String bank,String remarks) //此接口是数据库存储调用
    {
        this.user=user;
        this.type=type;
        this.typeSelect=typeSelect;
        this.solution=solution;
        this.data=data;
        this.money=money;
        this.bank=bank;
        this.remarks=remarks;

    }
    public String getType()
    {
        return typeSelect;
    }
    public String getData()
    {
        return data;
    }
    public String getMoney()
    {
        return money;
    }
    public void setSelectType(String type)
    {
        this.typeSelect=type;
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

