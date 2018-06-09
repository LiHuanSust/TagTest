package com.example.tagtest.tools;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by MyFirstPC on 2018/5/25.
 * Json字符串的解析，用到了官方提供的JsonObject
 */

public class UseJson {
    //解析服务器返回的数据
    public static Answer getCanLogin(final String json) {
        Answer answer=null;
        try {

                JSONObject jsonObject = new JSONObject(json);
                int resultCode =jsonObject.getInt("LoginRequest");
                answer=new Answer();
                Log.d("UseJson", resultCode+"");
                if(resultCode==CodeValue.SUCCESSFUL)
                {
                    answer.setResult(true);
                    answer.setText("登录成功");
                }
                else if(resultCode==CodeValue.PASSWORD_WRONG)
                {
                    answer.setResult(false);
                    answer.setText("账号/密码错误！！！");
                }
              else
              {
                answer.setResult(false);
                answer.setText("一些问题发生了呢");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }
    public static Answer getCanRegister(final String json) {
        Answer answer=null;
        try {

            JSONObject jsonObject = new JSONObject(json);
            int resultCode =jsonObject.getInt("RegisterRequest");
            answer=new Answer();
            Log.d("UseJson", resultCode+"");
            if(resultCode==CodeValue.SUCCESSFUL)
            {
                answer.setResult(true);
                answer.setText("注册成功");
            }
            else if(resultCode==CodeValue.ACCOUNT_EXITS)
            {
                answer.setResult(false);
                answer.setText("当前账号已被注册！！！");
            }
            else
                if(resultCode==CodeValue.CONNECTION_WRONG)
                {
                    answer.setResult(false);
                    answer.setText("当前网络较差！！！");
                }
            else
            {
                answer.setResult(false);
                answer.setText("一些问题发生了呢");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    //判断上传文件是否成功
    public static Answer getUploadFileResult(final String json) {
        Answer answer=null;
        try {

            JSONObject jsonObject = new JSONObject(json);
            int resultCode =jsonObject.getInt("UploadRequest");
            answer=new Answer();
            Log.d("UseJson", resultCode+"");
            if(resultCode==CodeValue.SUCCESSFUL)
            {
                answer.setResult(true);
                answer.setText("文件上传成功");
            }
            else
            {
                answer.setResult(false);
                answer.setText("一些问题发生了呢");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    public static Answer getDownLoadFileIsExit(final String json) {
        Answer answer=null;
        try {

            JSONObject jsonObject = new JSONObject(json);
            int resultCode =jsonObject.getInt("DownloadFileCheck");
            answer=new Answer();
            Log.d("UseJson", resultCode+"");
            if(resultCode==CodeValue.FILE_NOT_EXITS)
            {
                answer.setResult(false);
                answer.setText("文件不存在");
            }
            else
                if(resultCode==CodeValue.SUCCESSFUL)
            {
                answer.setResult(true);
                answer.setText("云端文件已备份");
            }
            else
                {
                    answer.setResult(false);
                    answer.setText("服务器找不到相应页面");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

}
