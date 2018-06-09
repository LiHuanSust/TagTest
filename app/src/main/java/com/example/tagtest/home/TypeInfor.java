package com.example.tagtest.home;

import org.litepal.crud.DataSupport;

/**
 * Created by MyFirstPC on 2018/6/8.
 * 消费，收入各种类别的详细信息
 * 的数据库文件
 */

public class TypeInfor extends DataSupport{
    //主键id
    private long id;
    //所属的大类id
    private int typeId;
    //具体的名字
    private String name;
    //是不是消费的内容
    private boolean isCost;
    private boolean isSave;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCost() {
        return isCost;
    }

    public void setCost(boolean cost) {
        isCost = cost;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }
}
