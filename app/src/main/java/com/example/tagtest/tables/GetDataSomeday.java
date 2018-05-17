package com.example.tagtest.tables;

import com.example.tagtest.MyData;
import com.example.tagtest.tools.MyCalculate;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MyFirstPC on 2018/5/6.
 * 由年，月，日获取数据，即
 * 某一天的消费或收入数据
 * 提供给PipeChat展示
 */

public class GetDataSomeday implements GetData {
    private String year;
    private String month;
    private String day;
    private String costMoney="0"; //消费金额
    private String salaryMoney="0";//收入金额
    private boolean type=false;
    public GetDataSomeday(final String year,final String month,final String day)
    {
        this.year=year;
        this.month=month;
        this.day=day;
    }

    @Override
    public Map<String, DataBase> getCostData() {
        Map<String,DataBase> mapList=new HashMap<>();
        //根据当前时间查询消费数据
        //true or false在数据库中是以1和0存储的
        List<MyData> dataTodayCost= DataSupport.where("year=? and month=? and day=? and type=?",year,
                month,day,"1").find(MyData.class);
        if(dataTodayCost.size()==0)
            return mapList;
        else
            for(MyData dataTemp:dataTodayCost)
            {
                costMoney=MyCalculate.add(costMoney,dataTemp.getMoney());
                DataBase dataBaseTemp=new DataBase(dataTemp.getId(),dataTemp.getType(),dataTemp.getTypeSelect(),dataTemp.getMoney());
                if(mapList.containsKey(dataBaseTemp.getTypeName()))
                {
                    DataBase oldDataBase=mapList.get(dataBaseTemp.getTypeName());
                    String money= MyCalculate.add(oldDataBase.getMoney(),dataBaseTemp.getMoney());
                    oldDataBase.setMoney(money);
                    mapList.put(dataBaseTemp.getTypeName(),oldDataBase);
                }
                else
                    mapList.put(dataBaseTemp.getTypeName(),dataBaseTemp);
            }
        return mapList;
    }

    //获取收入的数据
    @Override
    public Map<String, DataBase> getSalaryData() {
        Map<String,DataBase> mapList=new HashMap<>();
        //根据当前时间查询消费数据
        //true or false在数据库中是以1和0存储的
        List<MyData> dataTodayCost= DataSupport.where("year=? and month=? and day=? and type=?",year,
                month,day,"0").find(MyData.class);
        if(dataTodayCost.size()==0)
            return mapList;
        else
            for(MyData dataTemp:dataTodayCost)
            {
                salaryMoney=MyCalculate.add(salaryMoney,dataTemp.getMoney());
                DataBase dataBaseTemp=new DataBase(dataTemp.getId(),dataTemp.getType(),dataTemp.getTypeSelect(),dataTemp.getMoney());
                if(mapList.containsKey(dataBaseTemp.getTypeName()))
                {
                    DataBase oldDataBase=mapList.get(dataBaseTemp.getTypeName());
                    String money= MyCalculate.add(oldDataBase.getMoney(),dataBaseTemp.getMoney());
                    oldDataBase.setMoney(money);
                    mapList.put(dataBaseTemp.getTypeName(),oldDataBase);
                }
                else
                    mapList.put(dataBaseTemp.getTypeName(),dataBaseTemp);
            }
        return mapList;
    }

    @Override
    public String getCostMoney() {
        return costMoney;
    }

    @Override
    public String getSalaryMoney() {
        return salaryMoney;
    }
}
