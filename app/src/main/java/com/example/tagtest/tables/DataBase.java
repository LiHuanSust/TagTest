package com.example.tagtest.tables;

/**
 * Created by MyFirstPC on 2018/5/6.
 * GetData将查到的数据返回的类型
 */

public class DataBase {
    //type为true表示消费
    //type为false表示收入
    private long id;
    private boolean type;
    private String typeName;
    private String money;
    public DataBase()
    {}
    public DataBase(final long id,final boolean type,final String typeName,final String money)
    {
        this.id=id;
        this.type=type;
        this.typeName=typeName;
        this.money=money;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isType() {
        return type;
    }

    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
    //比较原则，先比较hash值是否相等，若相等，则调用equals方法
    //相等返回true；
    //不等返回false;
    //重写两个方法，实现比较按typeName
    @Override
    //如果是消费就返回1，收入返回0
    //不同的对象可以含有相同的hash值，
    //hash值相同就去掉equals,
    public int hashCode() {
        return this.getType()==true ? 1:0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DataBase)
            return false;
        if(obj==this)
            return true;
        return this.typeName.equals(((DataBase)obj).getTypeName());
    }
}
