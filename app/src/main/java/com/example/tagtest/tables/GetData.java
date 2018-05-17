package com.example.tagtest.tables;

import java.util.Map;

/**
 * Created by MyFirstPC on 2018/5/6.
 * 封装的类用于返回想要的MyData数据，即对数据库操作进行了封装
 */
public interface GetData
{
    public abstract Map<String,DataBase> getCostData(); //获取消费数据
    public abstract Map<String,DataBase> getSalaryData(); //获取支出数据
    public abstract String getCostMoney(); //获取支出金额
    public abstract String getSalaryMoney(); //获取收入金额
}
