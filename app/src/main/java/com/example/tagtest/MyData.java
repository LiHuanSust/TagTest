package com.example.tagtest;

import com.example.tagtest.account.Account;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class MyData extends DataSupport implements Serializable{
    public static final long serialVersionUID=1L; // 设置序列化id
    private transient String user; //用户名；不用被序列化
    private boolean type; //消费或收入的标识 当type为true表示支出，当为false表示收入
    private String typeSelect; //消费或收入的具体选择
    private int year; //年
    private int month; //月
    private int day;//日
    private String hourMinuteSecond; //时分秒的字符串形式
    private String money;//具体金额,
    private String remarks; //备注
    private long id; //加id是为了方便进行查找，删除，注意：此处即使加了id，并重新加了值，进入数据库中还是会变主键id的值
    private long accountId;  //加入accountId;
    private Account account;//消费明细和使用的账号一对一
    private String accountName; //相应的accountName
    private int picId;//数据库存的图片id。
    public MyData()
    {}
    public MyData(String user,boolean type,String typeSelect,int year,int month,int day,String hourMinuteSecond,String money,String remarks) //此接口是数据库存储调用
    {
        this.user=user;
        this.type=type;
        this.typeSelect=typeSelect;
        this.year=year;
        this.month=month;
        this.day=day;
        this.hourMinuteSecond=hourMinuteSecond;
        this.money=money;
        this.remarks=remarks;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTypeSelect() {
        return typeSelect;
    }

    public void setTypeSelect(String typeSelect) {
        this.typeSelect = typeSelect;
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

    public String getHourMinuteSecond() {
        return hourMinuteSecond;
    }

    public void setHourMinuteSecond(String hourMinuteSecond) {
        this.hourMinuteSecond = hourMinuteSecond;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String dateToString()
    {
        return getYear()+"."+getMonth()+"."+getDay()+" "+getHourMinuteSecond();
    }
    @Override
    public String toString()
    {

       return getUser()+","+","+getTypeSelect()+","+getMoney()+
              ","+getRemarks()+","+getYear()+","+getMonth()+","+getDay()+","+getHourMinuteSecond()+"\n";
    }
    public String getMonthDay()
    {
        return getMonth()+","+getDay();
    }

}

