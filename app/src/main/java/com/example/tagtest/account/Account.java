package com.example.tagtest.account;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by MyFirstPC on 2018/4/24.
 * 帐户表
 */

public class Account extends DataSupport implements Serializable{
    private long id; //账户id
    private String user;//用户
    private String accountName;//帐户名
    private String type;//账户类型
    private int accountPicId; //账户图片id
    private AccountInformation accountInformation;  //一对一关系

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

    public int getAccountId() {
        return accountPicId;
    }

    public void setAccountId(int accountId) {
        this.accountPicId = accountId;
    }

    public int getAccountPicId() {
        return accountPicId;
    }

    public void setAccountPicId(int accountPicId) {
        this.accountPicId = accountPicId;
    }

    public AccountInformation getAccountInformation() {

        return DataSupport.where("account_id=?",String.valueOf(getId())).findFirst(AccountInformation.class);
    }

    public void setAccountInformation(AccountInformation accountInformation) {
        this.accountInformation = accountInformation;
    }
}
