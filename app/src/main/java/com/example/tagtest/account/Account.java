package com.example.tagtest.account;

import com.example.tagtest.MyData;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/4/21.
 * 用来记录用户的账户信息，可以增删查改，不用共享参数
 * 是因为共享参数存储的信息有限
 */

public class Account extends DataSupport implements Serializable{
    private long id; //存储id,方便增删查改
    private int picId; //图片id
    private String user; //用户名，区分不同用户
    private String name; //账户名字
    private String type; //账户类型
    private float moneyCost; //消费金额
    private float moneySalary; //收入金额
    private String date; //添加时间
    private String accountRemarks; //账户备注信息
    private List<MyData> myDataList=new ArrayList<>(); //添加表间依赖//账户和消费记录是一对多的依赖关系
    public void setMyDataList(List<MyData> myDataList) {
        this.myDataList = myDataList;
    }
    public List<MyData> getMyDataList() {
        return myDataList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(float moneyCost) {
        this.moneyCost = moneyCost;
    }

    public float getMoneySalary() {
        return moneySalary;
    }

    public void setMoneySalary(float moneySalary) {
        this.moneySalary = moneySalary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountRemarks() {
        return accountRemarks;
    }

    public void setAccountRemarks(String accountRemarks) {
        this.accountRemarks = accountRemarks;
    }
}
