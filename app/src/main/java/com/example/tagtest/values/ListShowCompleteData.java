package com.example.tagtest.values;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.Alert_value;
import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.account.Account;
import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.tools.MyCalculate;

import org.litepal.crud.DataSupport;

/*显示数据详情
* 即：每条消费或支出的内容
*/
public class ListShowCompleteData extends AppCompatActivity implements View.OnClickListener{
    private TextView typeValue; //标题栏
    private TextView dateValue; //时间显示
    private TextView moneyValue; //金额显示
    private TextView payValue; //付款方式
    private TextView cardValue; //银行卡信息
    private TextView remarksValue; //备注
    private ImageView pictureValue; //图片资源
    private TextView picTextValue; //图片文字
    private Toolbar toolbar;  //顶部toolbar
    private Button buttonAlert; //修改按钮
    private Button buttonDrop; //删除按钮
    private MyData data;  //当前data详情
    private Account account; //当前记录归属账户
    private AccountInformation accountInformation; //账户详细信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_show_complete_data);
        initialise();
        setListener();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {

                //   Intent intent=new Intent();
                // intent.putExtra("IsCostAdd",flagCostIsAdd);
                //intent.putExtra("IsSalaryAdd",flagSalaryIsAdd);
                //setResult(RESULT_OK,intent);
                finish();
            }
        });
        long id=getIntent().getLongExtra("MyDataId",0);
        if(id==0)
        {
            Log.d("ListShowCompleteData","id is wrong!");
            finish();
        }
        data=DataSupport.find(MyData.class,id);
       // Account account=DataSupport.find(Account.class,data.getAccountId());
        if(data==null)
        {
            Log.d("ListShowCompleteData","data is null");
            finish();
        }
        if(data.getType())
        {

            typeValue.setText("消费详情");
        }
        else
        {
            typeValue.setText("收入详情");
        }
        account=DataSupport.find(Account.class,data.getAccountId());
        accountInformation=account.getAccountInformation();
        Log.d("LisgtShowCompleteData",accountInformation.toString());
        picTextValue.setText(data.getTypeSelect());
        dateValue.setText(data.dateToString());
        moneyValue.setText(data.getMoney()+"");
        payValue.setText(account.getType());
        if(accountInformation.isCard())
           cardValue.setText(account.getAccountName());
        else
            cardValue.setText("未使用银行卡");
        remarksValue.setText("    "+data.getRemarks());

    }
    public void initialise()
    {
        toolbar=findViewById(R.id.toolbar);
        typeValue=findViewById(R.id.toolbar_text_view);
        dateValue=findViewById(R.id.date_info);
        moneyValue=findViewById(R.id.money_info);
        payValue=findViewById(R.id.type_info);
        cardValue=findViewById(R.id.card_info);
        pictureValue=findViewById(R.id.picture_info);
        picTextValue=findViewById(R.id.picture_text_info);
        remarksValue=findViewById(R.id.remakrs_info);
        buttonAlert=findViewById(R.id.button_alert);
        buttonDrop=findViewById(R.id.button_drop);

    }
    public void setListener()
    {
        buttonAlert.setOnClickListener(this);
        buttonDrop.setOnClickListener(this);
    }
    public void setPicture(final String selected)
    {
        if(selected.equals("早餐"))
        {
            pictureValue.setImageResource(R.drawable.breakfast); //早餐图片
        }
        else if(selected.equals("午餐"))
        {
            pictureValue.setImageResource(R.drawable.lunch);  //午餐图片
        }
        else if(selected.equals("晚餐"))
        {
            pictureValue.setImageResource(R.drawable.dinner);
        }
        else if(selected.equals("烟酒"))
        {
            pictureValue.setImageResource(R.drawable.wine);
        }
        else if(selected.equals("零食"))
        {
            pictureValue.setImageResource(R.drawable.snack);
        }
        else if(selected.equals("水果")) {
            pictureValue.setImageResource(R.drawable.fruit);
        }
        else if(selected.equals("酒店"))
        {
            pictureValue.setImageResource(R.drawable.hotel);
        }
        else if(selected.equals("买菜"))
        {
            pictureValue.setImageResource(R.drawable.shopping);
        }
        else if(selected.equals("化妆品"))
        {
            pictureValue.setImageResource(R.drawable.cosmetic);
        }
        else if(selected.equals("旅行"))
        {
            pictureValue.setImageResource(R.drawable.trip);
        }
        else if(selected.equals("租房"))
        {
            pictureValue.setImageResource(R.drawable.tenant);
        }
        else if(selected.equals("饮品"))
        {
            pictureValue.setImageResource(R.drawable.drink);
        }
        else
            return;

    }
    @Override
    public void onClick(View v)
    {
       /* boolean type=data.getType();
        String date=data.dateToString();
        String money=data.getMoney()+"";
        String solution=data.getSolution();
        String bank=data.getBank();
        String remarks=data.getRemarks();




        MyData data=new MyData();*/
        switch (v.getId())
        {
            case R.id.button_alert:
                Bundle bundle=new Bundle();
                bundle.putSerializable("Infor",data);
                if(data.getType())
                {
                    Intent intent=new Intent(ListShowCompleteData.this,Alert_value.class);
                    intent.putExtras(bundle);
                    finish();
                    startActivity(intent);
                    break;
                }
            case R.id.button_drop:
                 String money,accountMoney;
                 String dataMoney=data.getMoney();
                //若是消费信息
                  if(data.getType())
                  {
                      money= MyCalculate.sub(accountInformation.getCost(),dataMoney);
                      accountInformation.setCost(money);
                  }
                  else
                  {
                      //收入信息
                      money=MyCalculate.sub(accountInformation.getSalary(),dataMoney);
                      accountInformation.setSalary(money);
                  }
                accountMoney=MyCalculate.add(accountInformation.getMoney(),dataMoney);
                accountInformation.setMoney(accountMoney);
                accountInformation.setDateAdd("无");
                accountInformation.setNum(accountInformation.getNum()-1);
                account.getList().remove(data);
                  if(accountInformation.save())
                  {
                      if(DataSupport.delete(MyData.class,data.getId())==1)
                      {
                          Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                          finish();
                      }
                      else
                      {
                          Toast.makeText(this,"删除失败",Toast.LENGTH_SHORT).show();
                          finish();
                      }
                  }
                  else
                  {

                      Toast.makeText(this,"关联信息修改失败",Toast.LENGTH_SHORT).show();
                  }
                //if(accountInformation.save())

                     break;
                //写个弹窗确认吧。。
                //改变账户的消费信息还有问题
                /*else
                    Toast.makeText(this,"一些存储的问题发生了！",Toast.LENGTH_SHORT).show();
                break;*/

                //DataSupport.deleteAll(MyData.class,"")

        }

    }

}
