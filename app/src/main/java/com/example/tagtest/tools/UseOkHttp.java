package com.example.tagtest.tools;

import android.util.Log;

import com.example.tagtest.drawer.User;

import java.io.File;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by MyFirstPC on 2018/5/25.
 */

public class UseOkHttp {
    public static void httpRequestLogin(String address,final String name,final String passWd,okhttp3.Callback callback)
    {
        OkHttpClient httpClientConnection=null;
        try
        {
            URL url=new URL(address);
            httpClientConnection=new OkHttpClient();
            RequestBody requestBody=new FormBody.Builder().add("accountName",name)
                    .add("accountPassWord",User.getMd5Value(passWd)).build();
            okhttp3.Request request=new Request.Builder().url(url).post(requestBody).build();
            httpClientConnection.newCall(request).enqueue(callback);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void httpRequestRegister(final String address, final User user,okhttp3.Callback callback)
    {
        OkHttpClient httpClientConnection=null;
        try
        {
            URL url=new URL(address);
            httpClientConnection=new OkHttpClient();
            //构建post的内容，其中passwd与answer采用md5加密处理
            RequestBody requestBody=new FormBody.Builder().add("accountName",user.getUserName())
                    .add("accountPassWord",user.getMd5Value(user.getUserPassWd()))
                    .add("sex",user.getSex())
                    .add("question",user.getQuestion())
                    .add("answer",user.getMd5Value(user.getMd5Value(user.getAnswer())))
                    .build();
            okhttp3.Request request=new Request.Builder().url(url).post(requestBody).build();
            httpClientConnection.newCall(request).enqueue(callback);
            // String responseDate=response.body().string();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //上传文件
    //address:服务器提交地址
    //dataPath:上传的数据的本地路径
    //type:具体的数据，MediaType类型：txt的类型:text/plain;charset=utf-8
    //callback访问结束时的回调
    public static void httpRequestLoadFile(final String address,final String dataPath,final String TYPE,okhttp3.Callback callback)
    {
        OkHttpClient okHttpClient=new OkHttpClient();
        //多个图片文件列表
        //多文件表单上传构造器
        // MultipartBody.Builder builder=new MultipartBody.Builder();
        ///builder.setType(MultipartBody.FORM);
        //builder.addFormDataPart("User",User.getNowUserName());


        MediaType type=MediaType.parse(TYPE);//"text/xml;charset=utf-8"
        //builder.addFormDataPart("File",User.getNowUserName()+"_mydata.txt",RequestBody.create(type,testFile));
        //RequestBody requestBody=builder.build();

       File file=new File(dataPath);
       if(!file.exists())
       {
           Log.d("UseOkHttp","上传文件不存在");
           return;
       }
        Request request=new Request.Builder().url(address)
                .post(RequestBody.create(type,file))
                .addHeader("User",User.getNowUserName())
                //传参数、文件或者混合，改一下就行请求体就行
                .build();
        okHttpClient.newCall(request).enqueue(callback);

    }
    public static boolean accountIsExits(final String address,final String account)
    {
        boolean flag=false;


       return flag;

    }

}
