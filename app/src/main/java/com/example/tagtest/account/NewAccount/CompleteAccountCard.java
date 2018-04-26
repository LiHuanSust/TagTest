package com.example.tagtest.account.NewAccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.GetDate;
import com.example.tagtest.R;
import com.example.tagtest.account.Account;

import java.util.ArrayList;
import java.util.List;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/*
* 4.23
* 完成银行卡号信息的完善
* 之所以分两个页面来完善账户信息，
* 是因为银行卡需要实现扫描添加卡号的功能
*
 */

public class CompleteAccountCard extends AppCompatActivity implements View.OnClickListener {

    private EditText accountNumber; //银行卡号
    private ImageView scanAccount;  //扫描银行卡
    private EditText money; //预算金额
    private EditText remarks; //备注信息
    private TextView accountType; //账户类型
    private Button buttonClear;
    private Button buttonSave;
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private Spinner bankCardSelect;
    private String typeName;
    private int picId;
    private final  static String[] CARD_NAME={"招商银行","工商银行","农业银行","储蓄银行","中国银行","建设银行","交通银行","中信银行",
            "光大银行","广发银行","兴业银行","民生银行","浦发银行","其他"};
    private final static int[] CARD_ICON={R.drawable.zhao_shang,R.drawable.gong_shang,R.drawable.nong_ye,R.drawable.chu_xu,R.drawable.china_yin_hang,R.drawable.jian_she,R.drawable.jiao_tong,R.drawable.zhong_xing
    ,R.drawable.guang_da,R.drawable.guang_fa,R.drawable.xing_ye,R.drawable.ming_sheng,R.drawable.pu_fa,R.drawable.bank_other};
    private SpinnerAdapter mAdapter;
    private List<AccountSelect> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_account_card);
        inilitise();
    }

    public void inilitise() {
        toolBarTitle=(TextView)findViewById(R.id.toolbar_text_view);
        toolBarTitle.setText("账户信息完善");
        accountType = (TextView) findViewById(R.id.account_type_value);
        accountType.setText("银行卡");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        bankCardSelect=(Spinner)findViewById(R.id.bank_card_select);
        for(int i=0;i<CARD_NAME.length;i++)
        {
           AccountSelect accountSelect=new AccountSelect();
           accountSelect.setName(CARD_NAME[i]);
           accountSelect.setPicId(CARD_ICON[i]);
           list.add(accountSelect);
        }
        mAdapter=new SpinnerAdapter(this,list);
        bankCardSelect.setAdapter(mAdapter);
        accountNumber = (EditText) findViewById(R.id.complete_account_number);
        scanAccount = (ImageView) findViewById(R.id.get_account);
        money = (EditText) findViewById(R.id.complete_account_money);
        remarks = (EditText) findViewById(R.id.account_value_remarks);
        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonSave = (Button) findViewById(R.id.button_save);
        scanAccount.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        bankCardSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AccountSelect temp=list.get(position);
                typeName=temp.getName();
                picId=temp.getPicId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
   public void clearValue()
   {
       accountNumber.setText("");
       money.setText("");
       remarks.setText("");
       bankCardSelect.setSelection(0);

   }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_account:
                Intent scanIntent = new Intent(this, CardIOActivity.class);

                // customize these values to suit your needs.
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(scanIntent, 100);
                break;
            case R.id.button_clear:
                clearValue();
                break;
            case R.id.button_save:
                String mAccountNumber=accountNumber.getText().toString();
                String mMoney=money.getText().toString();
                if(TextUtils.isEmpty(mAccountNumber) || TextUtils.isEmpty(mMoney))
                {
                    Toast.makeText(this,"账号及银行卡号不能为空!",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    boolean isAccountInfor,isAccount; //判断添加数据是否成功
                    AccountInformation accountInformation=new AccountInformation();
                    accountInformation.setCard(true);
                    accountInformation.setDateAdd(new GetDate().allToString());
                    accountInformation.setDate("无");
                    accountInformation.setNum(0);
                    accountInformation.setSalary(0.0f);
                    accountInformation.setCost(0.0f);
                    accountInformation.setRemarks(remarks.getText().toString());
                    accountInformation.setMoney(Float.parseFloat(mMoney));
                    accountInformation.setAccountName(mAccountNumber);
                    isAccountInfor=accountInformation.save();
                    Account account=new Account();
                    account.setUser("L.H");
                    account.setType(typeName);
                    account.setAccountPicId(picId);
                    account.setAccountName(mAccountNumber);
                    account.setAccountInformation(accountInformation);
                    isAccount=account.save();
                    if(isAccountInfor && isAccount)
                    {
                        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(this,"一些问题发生了哦",Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
        default:
                break;

    }

}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            remarks.setText(resultDisplayStr);
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }

    class SpinnerAdapter extends BaseAdapter {
        private Context context;
        private List<AccountSelect> dataList;

        public SpinnerAdapter(Context context, List<AccountSelect> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_view_account_select_basic, null);
            }
            ImageView cardPic = (ImageView) convertView.findViewById(R.id.account_select_pic);
            TextView cardName = (TextView) convertView.findViewById(R.id.account_select_name);
            AccountSelect accountSelect= dataList.get(position);
            cardPic.setImageResource(accountSelect.getPicId());
            cardName.setText(accountSelect.getName());
            return convertView;

        }
    }
}
