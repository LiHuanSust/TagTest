package com.example.tagtest.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.R;
import com.example.tagtest.drawer.User;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.tools.GetDate;

import org.litepal.crud.DataSupport;

/*
* 普通账户内容完善Activity
*/
public class CompleteAccountCommon extends AppCompatActivity implements View.OnClickListener {
    private EditText accountName;
    private EditText accountMoney;
    private TextView accountType;
    private EditText remarks;
    private Button buttonClear;
    private Button buttonSave;
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private AccountSelect mAccountSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_account_common);
        ActivityCollector.addActivity(this);
        initialise();
        mAccountSelect=(AccountSelect)getIntent().getSerializableExtra("AccountSelect");
        accountType.setText(mAccountSelect.getName());
    }

    public void initialise()
    {

        accountName=(EditText) findViewById(R.id.complete_account_name);
        accountMoney=(EditText) findViewById(R.id.complete_account_money);
        accountType=(TextView)findViewById(R.id.account_value_type);
        remarks=(EditText)findViewById(R.id.account_value_remarks);
        buttonClear=(Button)findViewById(R.id.button_clear);
        buttonSave=(Button)findViewById(R.id.button_save);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolBarTitle=(TextView)findViewById(R.id.toolbar_text_view);
        toolBarTitle.setText("账户信息完善");
        accountMoney.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonClear.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }
    public void clearValue()  //清除页面上的内容
    {
        accountName.setText("");
        accountMoney.setText("");
        remarks.setText("");

    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_clear:

               break;
            case R.id.button_save:
               String name=accountName.getText().toString();
               String money=accountMoney.getText().toString();
               String mRemarks=remarks.getText().toString();
               //当账户或预算为空时，提醒用户
               if(TextUtils.isEmpty(name) || TextUtils.isEmpty(money))
               {
                   Toast.makeText(this,"请将账户名或预算填写完整!",Toast.LENGTH_SHORT).show();
                   break;
               }
               else
               {
                   //查询当前账户是否已经存在
                   Account account= DataSupport.where("accountName=?",name).findFirst(Account.class);
                   if(account!=null)
                   {
                       Toast.makeText(CompleteAccountCommon.this,"账户名已存在！！！",Toast.LENGTH_SHORT).show();
                   }
                   //同名账户不存在
                   else
                       {//AccountInformation依赖与Account,所以先保存AccountInformation再保存Account；
                       //判断是否保存成功
                       boolean accountInforSave, accountSave;
                       Account mAccount = new Account();
                       mAccount.setUser(User.getNowUserName());
                       mAccount.setAccountName(name);
                       mAccount.setType(mAccountSelect.getName());
                       mAccount.setAccoutPicId(mAccountSelect.getPicId());
                       //  mAccount.setAccountInformation(mAccountInformation);
                       accountSave = mAccount.save();
                       AccountInformation mAccountInformation = new AccountInformation();
                       mAccountInformation.setAccountName(name);
                       mAccountInformation.setUser(User.getNowUserName());
                       mAccountInformation.setRemarks(mRemarks);
                       mAccountInformation.setDate(new GetDate().allToString());
                       Log.d("Test", money);
                       mAccountInformation.setMoney(money);
                       mAccountInformation.setCost("0");
                       mAccountInformation.setSalary("0");
                       mAccountInformation.setNum(0);
                       mAccountInformation.setDateAdd("无");
                       //标志位，在common里都设为false;
                       mAccountInformation.setCard(false);
                       mAccountInformation.setAccount(mAccount);
                       accountInforSave = mAccountInformation.save();
                       if (accountInforSave && accountSave) {
                           Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                           clearValue();
                           break;
                       } else {
                           Toast.makeText(this, "一些问题发生，导致出错了！！！", Toast.LENGTH_SHORT).show();
                       }
                   }
                   break;
                   //噗~~~直接用sqlite3命令行删并不级联执行。。。无语凝噎啊啊啊啊啊
                   // 一对一的表间依赖如果是单向的依赖只有删除含有依赖的那个表项才会触发依赖项的自动删除
                   //要是想实现删除任意一个依赖表，它的依赖项或被依赖项就自动被删除是需要建立双向依赖的。
                   //int num= DataSupport.delete(AccountInformation.class,8);
                   //Toast.makeText(this,"num is"+num,Toast.LENGTH_SHORT).show();
                   //break;
               }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
