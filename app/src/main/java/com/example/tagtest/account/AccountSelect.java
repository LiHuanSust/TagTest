package com.example.tagtest.account;

import java.io.Serializable;

/**
 * Created by MyFirstPC on 2018/4/24.
 * 这是账户的具体选择页。。。是固定的，中间没理清
 * 结构，误删了一次，我的天
 */

public class AccountSelect implements Serializable {
    private String name;
    private int picId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }
}
