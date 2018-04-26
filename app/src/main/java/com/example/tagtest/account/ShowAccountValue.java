package com.example.tagtest.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tagtest.R;

import org.litepal.crud.DataSupport;

/*
*该活动用来显示账号的详细信息
*
 */
public class ShowAccountValue extends AppCompatActivity implements View.OnClickListener{
    private TextView accountName; //账号名
    private TextView accountType; //账号类型
    private TextView accountCost; //账号消费（总）
    private TextView accountSalary;//账号收入（总）
    private TextView useNumber; //使用次数
    private TextView addTime; //账户添加日期
    private TextView nearUserTime; //账户最近使用
    private TextView remarks; //账户备注
    private Toolbar toolbar;
    private TextView toolBarTitle; //toolbar显示的标题
    private Button buttonAlter;
    private Button buttonDelete;
    private Account account; //账户类
    private AccountInformation accountInformation;//账户信息详情类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_account_value);
        account=(Account) getIntent().getSerializableExtra("Account");
        accountInformation= DataSupport.find(AccountInformation.class,account.getId());
        initialiise();
        setListener();
        onLoadData();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.account_value_button_alter:
                  break;
            case R.id.account_value_button_delete:
                break;
            default:
                break;

        }
    }

    public void initialiise()  //初始化
    {
        accountName=(TextView)findViewById(R.id.account_name);
        accountType=(TextView)findViewById(R.id.account_type);
        accountCost=(TextView)findViewById(R.id.account_cost);
        accountSalary=(TextView)findViewById(R.id.account_salary);
        useNumber=(TextView)findViewById(R.id.account_use_number);
        addTime=(TextView)findViewById(R.id.account_add_time);
        nearUserTime=(TextView)findViewById(R.id.account_near_use);
        remarks=(TextView)findViewById(R.id.account_remark);
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        buttonAlter=(Button)findViewById(R.id.account_value_button_alter);
        buttonDelete=(Button)findViewById(R.id.account_value_button_delete);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarTitle=findViewById(R.id.toolbar_text_view);
        toolBarTitle.setText("账户详情页");

    }
    public void setListener()
    {
        buttonAlter.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }
    //加载数据
    public void onLoadData()
    {
        accountName.setText(account.getAccountName());
        accountType.setText(account.getType());
        accountCost.setText(accountInformation.getCost()+"");
        accountSalary.setText(accountInformation.getSalary()+"");
       useNumber.setText(accountInformation.getNum()+"");
        //Log.d("Test:",accountInformation.getNum()+"");
       addTime.setText(accountInformation.getDate());
       nearUserTime.setText(accountInformation.getDateAdd());
       remarks.setText("      "+accountInformation.getRemarks());
       Log.d("ThisIsTest",accountInformation.getRemarks());


    }

}
