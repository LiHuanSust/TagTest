package com.example.tagtest.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagtest.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_account_card);
        inilitise();
    }

    public void inilitise() {
        toolBarTitle=(TextView)findViewById(R.id.toolbar_text_view);
        toolBarTitle.setText("账户信息完善");
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
        accountNumber = (EditText) findViewById(R.id.complete_account_number);
        scanAccount = (ImageView) findViewById(R.id.get_account);
        money = (EditText) findViewById(R.id.complete_account_money);
        accountType = (TextView) findViewById(R.id.account_type_value);
        remarks = (EditText) findViewById(R.id.account_value_remarks);
        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonSave = (Button) findViewById(R.id.button_save);
        scanAccount.setOnClickListener(this);
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
}
