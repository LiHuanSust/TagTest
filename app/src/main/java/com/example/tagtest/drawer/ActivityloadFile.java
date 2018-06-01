package com.example.tagtest.drawer;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.account.Account;
import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.tools.Answer;
import com.example.tagtest.tools.UseJson;
import com.example.tagtest.tools.UseOkHttp;

import org.litepal.crud.DataSupport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ActivityloadFile extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolBarText;
    private ListView listView;
    private final String fileTempName = "dataTemp.txt";
    private String response="";

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 3:
                    response=(String)msg.obj;
                    Log.d("Handle","我被调用了"+response);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_file);
        initialise();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarText.setText("数据同步方式选择");
        ArrayList<String> list = new ArrayList<>();
        list.add("数据上传");
        list.add("数据恢复");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //数据上传
                    // getMyData();
                    //getAccount();
                    //getAccountInformation();

                            if (getMyData() && getAccount() && getAccountInformation()) {
                                Toast.makeText(ActivityloadFile.this,"文件开始上传，结果请查看通知",Toast.LENGTH_SHORT).show();
                                onLoadFile();
                                Log.d("GetResult", "true");
                            }
                            else
                                Log.d("GetResult", "false");
                      }
                if (position == 1) {
                    //数据恢复
                }
            }
        });
    }

    public void initialise() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarText = (TextView) findViewById(R.id.toolbar_text_view);
        listView = (ListView) findViewById(R.id.list_view_load_file);

    }

    //获取MyData数据的文本文件
    public boolean getMyData() {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        List<MyData> myDataList = DataSupport.where("user=?", User.getNowUserName()).find(MyData.class);
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String fileName = Environment.getExternalStorageDirectory().toString() + "/" + fileTempName;
                File file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                writer = new BufferedWriter(fileWriter);
                for (MyData data : myDataList) {
                    String value = "";
                    // String
                    //用户名，消费/收入，具体名称，year,month,day,hourMinuteSecond,money,remarks,accountId;
                    String user = data.getUser();
                    int type = (data.getType() == true ? 1 : 0);
                    String select = data.getTypeSelect();
                    int year = data.getYear();
                    int month = data.getMonth();
                    int day = data.getDay();
                    String hourMinuteSecond = data.getHourMinuteSecond();
                    String money = data.getMoney();
                    String remarks = data.getRemarks();
                    long accountId = data.getAccountId();
                    value += user + "|" + type + "|" + select + "|" + year + "|" + month + "|" + day + "|" + hourMinuteSecond + "|"
                            + money + "|" + remarks + "|" + accountId + "\r\n";
                    writer.append(value);
                }
                writer.close();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //获取account的文本文件
    public boolean getAccount() {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        List<Account> accountList = DataSupport.where("user=?", User.getNowUserName()).find(Account.class);
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String fileName = Environment.getExternalStorageDirectory().toString() + "/" + fileTempName;
                File file = new File(fileName);
                if (!file.exists())
                    return false;
                //追加写
                FileWriter fileWriter = new FileWriter(fileName, true);
                writer = new BufferedWriter(fileWriter);
                writer.write("#######################\r\n");
                for (Account account : accountList) {
                    String value = "";
                    // String
                    //user,accountName,type,accountPicId,accountInformation;
                    String user = account.getUser();
                    String accountName = account.getAccountName();
                    String type = account.getType();
                    String accountPicId = account.getAccoutPicId() + "";
                    value = user + "|" + accountName + "|" + type + "|" + accountPicId + "\r\n";
                    writer.append(value);
                }
                writer.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //获取accountInformation文本
    public boolean getAccountInformation() {
        List<AccountInformation> accountInformationList = DataSupport.where("user=?", User.getNowUserName())
                .find(AccountInformation.class);
        BufferedWriter writer = null;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String fileName = Environment.getExternalStorageDirectory().toString() + "/" + fileTempName;
                File file = new File(fileName);
                if (!file.exists()) {
                    return false;
                }

                FileWriter fileWriter = new FileWriter(fileName, true);
                writer = new BufferedWriter(fileWriter);
                writer.write("####################\r\n");
                for (AccountInformation accountInformation : accountInformationList) {
                    String value = "";
                    // String
                    //user,accountName,dateAdd,date,remarks,cost,salary,money,num,isCard;
                    String user = accountInformation.getUser();
                    String accountName = accountInformation.getAccountName();
                    String dateAdd = accountInformation.getDateAdd();
                    String date = accountInformation.getDate();
                    String remarks = accountInformation.getRemarks();
                    String cost = accountInformation.getCost();
                    String salary = accountInformation.getSalary();
                    String money = accountInformation.getMoney();
                    String num = accountInformation.getNum() + "";
                    String isCard = accountInformation.isCard() + "";
                    value += user + "|" + accountName + "|" + dateAdd + "|" + date + "|" + remarks + "|" + cost + "|"
                            + salary + "|" + money + "|" + num + "|" + isCard + "\r\n";
                    writer.write(value);

                }
                writer.close();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //文件上传
    public void onLoadFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path=Environment.getExternalStorageDirectory().toString()+"/"+fileTempName;
            String type="text/xml;charset=utf-8";
            String address="http://10.0.2.2:8080/TagTest/UploadFile";
            UseOkHttp.httpRequestLoadFile(address,path,type, new okhttp3.Callback()
            {
                @Override
                public void onFailure(Call call, IOException e) {
                      Log.e("LoadFile","fail");
                      checkResponseCode();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e("LoadFile","successful");
                    Message message=new Message();
                    message.what=3;
                    //该方法只能调用一次，然后底层的实现是之后默认关闭的
                    message.obj=response.body().string();
                    handler.sendMessage(message);
                    checkResponseCode();
                }
            });
        }
    }
    public void checkResponseCode()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(response)) {
                    Log.d("Result is sb", response);
                    //Toast.makeText(ActivityloadFile.this, "一些问题发生了哦！！！", Toast.LENGTH_SHORT).show();
                    getNotification(3,"文件上传失败，服务器不理我呐!!!");
                } else {
                    Log.d("Result is sb", response);
                    //将json字符串解析
                    Answer answer = UseJson.getUploadFileResult(response);
                    if (answer == null) {
                        getNotification(4,"文件上传失败，服务器坏掉了！！！");

                    } else {
                        if (answer.getResult()) {
                            //上传成功
                            getNotification(4,"文件上传成功，再也不怕文件丢了呢~");


                        } else {
                            getNotification(5,"文件上传失败，"+answer.getText());
                        }
                    }
                }
            }
        });
        }
        //设置通知
        public void getNotification(int code,final String des)
        {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("通知：")
                    .setContentText(des)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    //.setContentIntent(pi)
                    .build();
            manager.notify(code, notification);
        }
    }


