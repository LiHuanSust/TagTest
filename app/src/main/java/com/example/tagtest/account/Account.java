package com.example.tagtest.account;

import com.example.tagtest.MyData;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/4/24.
 * 帐户表
 */

public class Account extends DataSupport implements Serializable{
    private long id; //账户id
    private String user;//用户
    private String accountName;//帐户名
    private String type;//账户类型
    private int accoutPicId; //账户图片名称
    private AccountInformation accountInformation;  //一对一关系，一个账户对应一个账户信息
    private List<MyData> list=new ArrayList<>(); //多对一关系，一个账号对应多条消费记录

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAccoutPicId() {
        return accoutPicId;
    }

    public void setAccoutPicId(int accoutPicId) {
        this.accoutPicId = accoutPicId;
    }

    public AccountInformation getAccountInformation() {

        return DataSupport.where("account_id=?",getId()+"").findFirst(AccountInformation.class);
    }

    public void setAccountInformation(AccountInformation accountInformation) {
        this.accountInformation = accountInformation;
    }

    public List<MyData> getList() {
        return list;
    }

    public void setList(List<MyData> list) {
        this.list = list;
    }
}
