package com.example.tagtest.drawer;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.account.Account;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ActivityExportData extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolBarText;
    private ListView listView;
    //excel表的对象
    private WritableWorkbook writableWorkbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarText = (TextView) findViewById(R.id.toolbar_text_view);
        listView = (ListView) findViewById(R.id.list_view_export_select);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarText.setText("数据导出方式选择");
        ArrayList<String> list = new ArrayList<>();
        list.add("将数据导出为txt文档");
        list.add("将数据导出为excel文档");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //将数据导出为txt格式
                if (position == 0) {
                    Toast.makeText(ActivityExportData.this, "开始导出，请等待", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            exportDataToText();
                        }
                    }).start();
                }
                //导出为excel
                if (position == 1) {
                    Toast.makeText(ActivityExportData.this, "开始导出，请等待", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            exportDataToExcel();
                        }
                    }).start();
                }


            }
        });
    }

    public void exportDataToText() {
       FileOutputStream out = null;
        //标志位，判断导出是否成功
        boolean flag=false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                 File file = new File(Environment.getExternalStorageDirectory().toString()+"/data.txt");
                 StatFs stat = new StatFs(file.getPath());
                 //获取可用字节数
                 long availableBlocks = stat.getAvailableBlocksLong();
                 out=new FileOutputStream(file);
                 String title="                  消费收入详情\n";
                 int len=0;
                 List<MyData> list= DataSupport.findAll(MyData.class);
                 Map<Long,String>  accountName=new HashMap<>();
                 List<Account> accounts=DataSupport.findAll(Account.class);
                 for(Account account:accounts)
                 {
                    accountName.put(account.getId(),account.getAccountName());
                 }
                String values=title;
                for(MyData tempData:list)
                 {
                     //时间信息
                     String date=tempData.dateToString();
                     //花费的具体名称
                     String name=tempData.getTypeSelect();
                     //花费金额
                     String type=(tempData.getType()==true? "消费数据":"收入数据");
                     String money=tempData.getMoney();
                     String accountNameTemp=accountName.get(tempData.getAccountId());
                     values+=date+","+name+","+type+","+money+","+accountNameTemp+"\n";
                 }

                 if(values.getBytes().length<availableBlocks)
                 {
                     //知道具体的长度就可以直接写了
                     //弃用BufferedWriter是担心最后一次性写入value太大，造成缓冲区溢出
                     out.write(values.getBytes(),0,values.getBytes().length);
                     flag=true;
                 }
                 //数据过大，可用存储空间不足
                 else
                 {
                     Toast.makeText(ActivityExportData.this,"可用内存不足，无法导出",Toast.LENGTH_SHORT).show();
                 }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    //若导出成功，则显示成功的通知
                    if(flag) {
                       getNotification(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //导出为excel
    public void exportDataToExcel()
    {
        FileOutputStream out = null;
        //标志位，判断导出是否成功
        boolean flag=false;
        Workbook workbook=null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Environment.getExternalStorageDirectory().toString()+"/mydata.xls");
           try
           {
               if(!file.exists())
                     file.createNewFile();
               StatFs stat = new StatFs(file.getPath());
               long availableBlocks = stat.getAvailableBlocksLong();
               //简单的判断内存情况
               if(availableBlocks<10*1024)
               {
                   Toast.makeText(ActivityExportData.this,"可用存储空间不足10M,导出失败",Toast.LENGTH_SHORT).show();
                   return;
               }
               //获取工作表对象
              // workbook=Workbook.getWorkbook(file);
               //获取工作表，工厂模式获得
               writableWorkbook=Workbook.createWorkbook(file);
               //设置工作簿
               WritableSheet writableSheet=writableWorkbook.createSheet("用户消费支出数据",0);
               //设置每个单元格的默认宽度
               writableSheet.getSettings().setDefaultColumnWidth(10);
               //设置帐户名列的默认宽度，因为账户名有可能比较长。
               writableSheet.setColumnView(5,20);
               String[] titles={"年份","时间","内容","类型","金额","帐户名"};
               //5:单元格
                Label label=null;
                 //6:给第一行设置列名
                for(int i=0;i<titles.length;i++) {
                    //x,y,第一行的列名
                    label = new Label(i, 0, titles[i]);
                    //7：添加单元格
                    writableSheet.addCell(label);
                }
               setValueIntoCell(writableSheet);
               writableWorkbook.write();
               writableWorkbook.close();
           }catch (Exception e)
           {
               e.printStackTrace();
           }
           finally {
               if(workbook!=null)
                   workbook.close();
              // Toast.makeText(ActivityExportData.this,"成功",Toast.LENGTH_SHORT).show();
               getNotification(2);
           }
        }

    }
    //设置每个单元格数据
    public void setValueIntoCell(WritableSheet writableSheet)
    {
       List<MyData> dataList=DataSupport.findAll(MyData.class);
       if(dataList.size()==0)
           return;
        Map<Long,String>  accountName=new HashMap<>();
        List<Account> accounts=DataSupport.findAll(Account.class);
        for(Account account:accounts)
        {
            accountName.put(account.getId(),account.getAccountName());
        }
        //第一行有标题栏了，所以第二行开始
        int row=1;
        try
        {
        for(MyData data:dataList)
        {
            String yearMonthDay=data.getYear()+"-"+data.getMonth()+"-"+data.getDay();
            String time=data.getHourMinuteSecond();
            String typeName=data.getTypeSelect();
            String type=(data.getType()==true ? "消费记录":"收入记录");
            String money=data.getMoney();
            String account=accountName.get(data.getAccountId());

               Label label=new Label(0,row,yearMonthDay);
               writableSheet.addCell(label);
               Label label1=new Label(1,row,time);
               writableSheet.addCell(label1);
               Label label2=new Label(2,row,typeName);
               writableSheet.addCell(label2);
               Label label3=new Label(3,row,type);
               writableSheet.addCell(label3);
               Label label4=new Label(4,row,money);
               writableSheet.addCell(label4);
               Label label5=new Label(5,row,account);
               writableSheet.addCell(label5);
               row++;
           }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //获取通知
    /*
     *@param code 通知的标识符，每个通知标识符都应是唯一的
     */
    public void getNotification(int code)
    {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("通知：")
                .setContentText("数据导出成功！具体位置：" + Environment.getExternalStorageDirectory().toString()/*getApplicationContext().getFilesDir().getAbsolutePath()*/)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                //.setContentIntent(pi)
                .build();
        manager.notify(code, notification);

    }
}
