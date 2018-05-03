package com.example.tagtest.tools;

import java.math.BigDecimal;

/**
 * Created by MyFirstPC on 2018/5/3.
 * 实现科学计算，float,double自身有精度问题
 */

public class MyCalculate {
    //加法
    public static String add(String a,String b)
    {
        BigDecimal x=new BigDecimal(a);
        BigDecimal y=new BigDecimal(b);
        return x.add(y).toString();
    }
    //减法
    public static String sub(String a,String b)
    {
        BigDecimal x=new BigDecimal(a);
        BigDecimal y=new BigDecimal(b);
        return x.subtract(y).toString();
    }
    //乘法
    public static String mul(String a,String b)
    {
        BigDecimal x=new BigDecimal(a);
        BigDecimal y=new BigDecimal(b);
        return x.multiply(y).toString();
    }
    //除法
    public static String div(String a,String b)
    {
        BigDecimal x=new BigDecimal(a);
        BigDecimal y=new BigDecimal(b);
        return x.divide(y).toString();
    }
}
