package com.example.tagtest.drawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;

/*
*该页面用来重置密码。方法是回答申请账号时选择的问题
*
 */

public class ActivityForgetPassWd extends AppCompatActivity {

    private EditText account;
    private Button find;
    private Toolbar toolbar;
    private TextView toolBarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_wd);
        ActivityCollector.addActivity(this);
        account=(EditText)findViewById(R.id.account_find_passwd);
        find=(Button)findViewById(R.id.button_find_passwd);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolBarText=(TextView)findViewById(R.id.toolbar_text_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolBarText.setText("重置密码");
       account.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               //
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                      if(s.length()>=7)
                      {
                          find.setClickable(true);
                      }
                      else
                      {
                          find.setClickable(false);
                      }
           }

           @Override
           public void afterTextChanged(Editable s) {


           }
       });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
