package com.example.tagtest.tables;

import com.example.tagtest.MyData;
import com.example.tagtest.tools.MyCalculate;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/5/19.
 */

public class GetDataByYear {
    private String year;  //通过年来统计
    private String totalCostMoneyOfYear="0.0"; //年支出
    private String totalSalaryMoneyOfYear="0.0";//年收入
    public GetDataByYear(final String year)
    {
        this.year=year;
    }
    List<MyData> myDataList;
    List<ShowDataByYearDataBase> dataList=new ArrayList<>();
    public List<ShowDataByYearDataBase> getDataByYear()
    {
     myDataList= DataSupport.where("year=?",year).find(MyData.class);
     if(myDataList.size()==0)
         return null;
     for(int month=1;month<=12;month++)
     {
         ShowDataByYearDataBase temp=new ShowDataByYearDataBase();
         temp.setMonth(month);
         temp.setCostMoney("0.0");
         temp.setSalaryMoney("0.0");
         dataList.add(temp);
     }
     for(MyData myData:myDataList)
     {
         int myDataMonth=myData.getMonth()-1;
          ShowDataByYearDataBase tempDataBase=dataList.get(myDataMonth);
          //如果是消费数据
          if(myData.getType())
          {
              String money=myData.getMoney();
              tempDataBase.costMoney= MyCalculate.add(tempDataBase.costMoney,money);
              totalCostMoneyOfYear=MyCalculate.add(totalCostMoneyOfYear,money);
          }
          else
          {
              String money=myData.getMoney();
              tempDataBase.salaryMoney=MyCalculate.add(tempDataBase.salaryMoney,money);
              totalSalaryMoneyOfYear=MyCalculate.add(totalSalaryMoneyOfYear,money);
          }
          dataList.set(myDataMonth,tempDataBase);
     }
     return dataList;
    }
    public String getCostMoneyOfYear()
    {
        return totalCostMoneyOfYear;
    }
    public String getSalaryMoneyOfYear()
    {
        return totalSalaryMoneyOfYear;
    }

    class ShowDataByYearDataBase
    {
        private int month; //标记是1到12月中的哪一个月
        private String costMoney;//当月总花费
        private String salaryMoney;//当月总收入
        public ShowDataByYearDataBase()
        {}
        public ShowDataByYearDataBase(final int month,final String costMoney,final String salaryMoney)
        {
            this.month=month;
            this.costMoney=costMoney;
            this.salaryMoney=salaryMoney;
        }
        public int getMonth()
        {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getCostMoney() {
            return costMoney;
        }

        public void setCostMoney(String costMoney) {
            this.costMoney = costMoney;
        }

        public String getSalaryMoney() {
            return salaryMoney;
        }

        public void setSalaryMoney(String salaryMoney) {
            this.salaryMoney = salaryMoney;
        }
    }
}
