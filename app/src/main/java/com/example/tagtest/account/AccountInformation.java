package com.example.tagtest.account;

import org.litepal.crud.DataSupport;

/**
 * Created by MyFirstPC on 2018/4/24.
 */

public class AccountInformation extends DataSupport{
    private long id;
    private String accountName; //主键
    private String dateAdd;  //最近一笔消费时间
    private String date;  //账户创建时间
    private String remarks; //账号备注
    private float cost; //记录总支出
    private float salary; //记录总收入
    private float money; //账户净资产
    private int num; //记录消费次数
    private boolean isCard; //标志位判断是不是银行卡 。。。
    private Account account; //一对一关系

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCard() {
        return isCard;
    }

    public void setCard(boolean card) {
        isCard = card;
    }

    public Account getAccount() {
        return DataSupport.where("accountinformation_id=?",String.valueOf(getId())).findFirst(Account.class);
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
