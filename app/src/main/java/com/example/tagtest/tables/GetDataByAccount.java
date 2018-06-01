package com.example.tagtest.tables;

import com.example.tagtest.MyData;
import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.drawer.User;
import com.example.tagtest.tools.MyCalculate;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MyFirstPC on 2018/5/17.
 */

public class GetDataByAccount{
    private String year;
    private String month;
    private int accountNum;  //账户的数量
    private String costMoney="0.0";
    private String salaryMoney="0.0";
    private String costTotal="0"; //账户总计花费
    private String salaryTotal="0"; //账户总计收入
    private String aveCost="0"; //每个账户平均花费
    private int useNumMax=0; //账户使用次数最多
    private float oneUseCostMax=0f;//单笔花费最多
    private float oneUseSalaryMax=0f; //单笔收入最多
    private int costUseNum=0;
    private int salaryUseNum=0;
    public GetDataByAccount(final String year,final String month)
    {
        this.year=year;
        this.month=month;
    }
    public ArrayList<AccountDataBase> getData()
    {
        ArrayList<AccountDataBase> dataList=null;
        List<AccountInformation> accountInformationList= DataSupport.where("user=?",User.getNowUserName()).find(AccountInformation.class);
        List<MyData> myDataList=DataSupport.where("user=? and year=? and month=?", User.getNowUserName(),year,month).find(MyData.class);
        accountNum=accountInformationList.size();
        Map<Long,AccountDataBase> dataBaseMap;
        if(myDataList.size()!=0) {
            dataBaseMap=new HashMap<>();
            for(AccountInformation accountInformation:accountInformationList)
            {
                AccountDataBase accountDataBase=new AccountDataBase();
                accountDataBase.name=accountInformation.getAccountName();
                accountDataBase.costMoney="0";
                accountDataBase.salaryMoney="0";
                dataBaseMap.put(accountInformation.getId(),accountDataBase);
            }
            for(MyData myData:myDataList)
            {
                long id=myData.getAccountId();
                AccountDataBase accountDataBase=dataBaseMap.get(id);
                if(myData.getType())//为消费数据
                {
                    costUseNum+=1;
                    String tempCostMoney=myData.getMoney();
                    accountDataBase.costMoney = MyCalculate.add(accountDataBase.costMoney, tempCostMoney);
                    costMoney=MyCalculate.add(costMoney,tempCostMoney);
                }
                else
                {
                    salaryUseNum+=1;
                    String tempSalaryMoney=myData.getMoney();
                    accountDataBase.salaryMoney = MyCalculate.add(accountDataBase.salaryMoney, tempSalaryMoney);
                    salaryMoney=MyCalculate.add(salaryMoney,tempSalaryMoney);
                }
                dataBaseMap.put(id,accountDataBase);
            }
            dataList=new ArrayList<AccountDataBase>(dataBaseMap.values());
        }
        return dataList;
    }
    public List<AccountHorizontalDataBase> getHorbiziontalData()
    {
      MyData dataCostMax=DataSupport.order("money desc").where("user=? and type=?",User.getNowUserName(),"1").findFirst(MyData.class);
      MyData dataSalaryMax=DataSupport.order("money desc").where("user=? and type=?",User.getNowUserName(),"0").findFirst(MyData.class);
      List<AccountInformation> accountInformations=DataSupport.where("user=?",User.getNowUserName()).find(AccountInformation.class);
      //计算所有的花费与收入
        for(AccountInformation tempAccountInfor:accountInformations)
        {

          if(useNumMax<tempAccountInfor.getNum())
              useNumMax=tempAccountInfor.getNum();
            costTotal=MyCalculate.add(costTotal,tempAccountInfor.getCost());
            salaryTotal=MyCalculate.add(salaryMoney,tempAccountInfor.getSalary());
          }
      if(dataCostMax!=null)
          oneUseCostMax=Float.parseFloat(dataCostMax.getMoney());
      if(dataSalaryMax!=null)
          oneUseSalaryMax=Float.parseFloat(dataSalaryMax.getMoney());
       List<AccountHorizontalDataBase> list=new ArrayList<>();
       list.add(new AccountHorizontalDataBase("账户单笔消费最多",oneUseCostMax));
       list.add(new AccountHorizontalDataBase("账户单笔收入最多",oneUseSalaryMax));

       if(costUseNum!=0)
       {
           float aveCost=Float.parseFloat(MyCalculate.div(costTotal,costUseNum+""));
           list.add(new AccountHorizontalDataBase("账号平均花费",aveCost));

       }
       if(salaryUseNum!=0)
       {
           float aveSalary=Float.parseFloat(MyCalculate.div(salaryTotal,salaryUseNum+""));
           list.add(new AccountHorizontalDataBase("账号平均收入",aveSalary));
       }
        list.add(new AccountHorizontalDataBase("使用次数最多",useNumMax));
        list.add(new AccountHorizontalDataBase("账号总收入",Float.parseFloat(salaryTotal)));
        list.add(new AccountHorizontalDataBase("账号总支出",Float.parseFloat(costTotal)));
        return list;
    }



    public int getAccountNum()
    {
        return accountNum;
    }
    public String getCostMoney()
    {
        return costMoney;
    }
    public String getSalaryMoney()
    {
        return salaryMoney;
    }
    //数据返回的形式
    class AccountDataBase
    {
        String name; //帐户名
        String costMoney; //账户当月消费
        String salaryMoney; //账户当月收入
    }
    //horizontal barChart dataBase
    class AccountHorizontalDataBase
    {
        String name;
        float num;
        public AccountHorizontalDataBase()
        {}
        public AccountHorizontalDataBase(String name,float num)
        {
            this.name=name;
            this.num=num;
        }
    }
}
