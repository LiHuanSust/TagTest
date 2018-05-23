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
        //第二个参数为小数位数，第三个参数为小数模式，
        //ROUND_HALF_DOWN
        //若舍弃部分> .5，则作 ROUND_UP；否则，作 ROUND_DOWN 。
        //ROUND_DOWN :
        //从不在舍弃(即截断)的小数之前增加数字
        //ROUND_UP:
        //总是在非 0 舍弃小数(即截断)之前增加数字。
        return x.divide(y,10,BigDecimal.ROUND_HALF_DOWN).toString();
    }
}
