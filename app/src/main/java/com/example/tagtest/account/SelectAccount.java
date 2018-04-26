package com.example.tagtest.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tagtest.R;
import com.example.tagtest.account.NewAccount.AccountSelect;
import com.example.tagtest.account.NewAccount.AdapterAccountSelect;
import com.example.tagtest.account.NewAccount.CompleteAccountCard;
import com.example.tagtest.account.NewAccount.CompleteAccountCommon;

import java.util.ArrayList;
import java.util.List;

//添加账户的类型选择界面
public class SelectAccount extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView mListView;
    private TextView toolBarValue; //toolbar中的标题
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private List<AccountSelect> mList;
    private ArrayAdapter<AccountSelect> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);
        initialise();
         setListener();


    }
    public void initialise()
    {
        mList=new ArrayList<>();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        mListView=(ListView)findViewById(R.id.list_view_account_select);
        toolBarValue=(TextView)findViewById(R.id.toolbar_text_view);
        toolBarValue.setText("账户类型选择");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //每次执行时R.drawabled.id都是动态生成的，我的天，so stupid!
        mListView=findViewById(R.id.list_view_account_select);
        String[] accountType={"现金","银行卡","支付宝","微信","其他"};
        int[] pictureId={R.drawable.cash_account,R.drawable.bankcard_account,R.drawable.alipay_account,R.drawable.wechat_account,R.drawable.other_account};
             for(int i=0;i<5;i++) {
                 AccountSelect accountSelect = new AccountSelect();
                 accountSelect.setName(accountType[i]);
                 accountSelect.setPicId(pictureId[i]);
                 mList.add(accountSelect);
             }
            mAdapter=new AdapterAccountSelect(this,R.layout.list_view_account_select_basic,mList);
           mListView.setAdapter(mAdapter);
       }
    public void setListener()
    {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 AccountSelect mAccountSelect=mList.get(position);
                 switch(mAccountSelect.getName())
                 {
                     case "银行卡":
                         Intent intentAccountCard=new Intent(SelectAccount.this,CompleteAccountCard.class);
                         startActivity(intentAccountCard);
                         break;
                     default:
                         Bundle bundle=new Bundle();
                         bundle.putSerializable("AccountSelect",mAccountSelect);
                         Intent intent=new Intent(SelectAccount.this,CompleteAccountCommon.class);
                         intent.putExtras(bundle);  //把账户类型及图片id传给下一个页面
                         startActivity(intent);

                 }

            }
        });
    }
}
