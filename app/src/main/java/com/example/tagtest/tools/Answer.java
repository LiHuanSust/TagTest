package com.example.tagtest.tools;

/**
 * Created by MyFirstPC on 2018/5/25.
 * 基本类型，返回登录，注册，上传，下载等事件的结果
 *
 */

public class Answer {
       public Answer()
       {
           result=false;
           text="一些问题发生了";
       }
        private boolean result;  //返回是否成功
        private String text; //返回描述性文字

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
