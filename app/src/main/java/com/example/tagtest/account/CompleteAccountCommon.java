package com.example.tagtest.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.GetDate;
import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityVoice;

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
        accountName.setText("");
        remarks.setText("");

    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_clear:
               //clearValue();
                Intent voiceIntent=new Intent(CompleteAccountCommon.this, ActivityVoice.class);
                startActivity(voiceIntent);
               break;
            case R.id.button_save:
                Account mAccount=new Account();
                mAccount.setUser("");
                mAccount.setName(accountName.getText().toString());
                mAccount.setMoneyCost(0.0f);
                mAccount.setMoneySalary(Float.parseFloat(accountMoney.getText().toString()));
                mAccount.setDate(new GetDate().allToString());
                mAccount.setPicId(mAccountSelect.getPicId());
                mAccount.setType(mAccountSelect.getName());
                mAccount.setAccountRemarks(remarks.getText().toString());
                if(mAccount.save())
                {
                    Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
