package com.example.tagtest.tables;

import android.util.Log;

import com.example.tagtest.MyData;
import com.example.tagtest.drawer.User;
import com.example.tagtest.tools.MyCalculate;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/5/13.
 * 由年月获取数据，用以支持线性表所需数据，即实现按月统计功能
 */

public class GetDataSomeMonth
{
    private String year; //年
    private String month; //月
    private int dayNumber;  //获取某月具体有几天
    private String costMoney; //花费
    private String salaryMoney; //收入
    public GetDataSomeMonth(final String year,final String month)
    {
        this.year=year;
        this.month=month;
        costMoney="0.0";
        salaryMoney="0.0";

    }
    public List<String> getCostData() {
        dayNumber=getDayNumberOfMonth();
        List<String> dataList=new ArrayList<>();
        for(int i=1;i<=dayNumber;i++)
        {
          //要先初始化
          dataList.add("0.0");
        }
        List<MyData> list= DataSupport.where("user=? and year=? and month=? and type=?", User.getNowUserName(),year,month,"1").find(MyData.class);
            for(MyData temp:list)
            {
                int pos=temp.getDay()-1;
                Log.d("Test",pos+","+temp.getMoney());
                String tempMoney=temp.getMoney();
                costMoney=MyCalculate.add(costMoney,tempMoney);
                dataList.set(pos,MyCalculate.add(dataList.get(pos),tempMoney));
            }
        Log.d("GetDataSomeMonth","data.length"+dataList.size()+"");
       return dataList;
    }
    public List<String> getSalaryData() {
        dayNumber=getDayNumberOfMonth();
        List<String> dataList=new ArrayList<>();
        for(int i=1;i<=dayNumber;i++)
        {
            //要先初始化
            dataList.add("0.0");
        }
        List<MyData> list= DataSupport.where("user=? and year=? and month=? and type=?",User.getNowUserName(),year,month,"0").find(MyData.class);
        for(MyData temp:list)
        {
            int pos=temp.getDay()-1;
            Log.d("Test",pos+","+temp.getMoney());
            String tempMoney=temp.getMoney();
            salaryMoney=MyCalculate.add(salaryMoney,tempMoney);
            dataList.set(pos,MyCalculate.add(dataList.get(pos),tempMoney));
        }
        Log.d("GetDataSomeMonth","data.length"+dataList.size()+"");
        return dataList;
    }


    public String getCostMoney() {
        return costMoney;
    }
    public String getSalaryMoney() {
        return salaryMoney;
    }
    //获取某月具体有几天，主要考虑2月天数的不同，若为平年29，闰年28
    public int getDayNumberOfMonth()
    {
      int tempYear=Integer.parseInt(year);
      int tempMonth=Integer.parseInt(month);
      if(tempMonth==1 || tempMonth==3 || tempMonth==5 || tempMonth==7 || tempMonth==8 || tempMonth==10 ||
              tempMonth==12)
          return 31;
      else
          if(tempMonth==4 || tempMonth==6 || tempMonth==9 || tempMonth==11)
              return 30;
      else
        if(tempYear%4==0 && tempYear%100!=0 || tempYear%400==0)
         {
             return 29;
         }
         return 28;
    }
    public int getDayNumber()
    {
        return dayNumber;
    }
}
