package com.example.tagtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListShowCompleteData extends AppCompatActivity {
    private TextView typeValue; //标题栏
    private TextView dateValue; //时间显示
    private TextView moneyValue; //金额显示
    private TextView payValue; //付款方式
    private TextView cardValue; //银行卡信息
    private TextView remarksValue; //备注
    private ImageView pictureValue; //图片资源
    private TextView picTextValue; //图片文字
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_show_complete_data);
        initialise();
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
        Bundle bundle=getIntent().getExtras();
        typeValue.setText(bundle.getString("Type"));
        dateValue.setText(bundle.getString("Date"));
        moneyValue.setText(bundle.getString("Money"));
        payValue.setText(bundle.getString("Solution"));
        cardValue.setText(bundle.getString("CardInfo"));
        remarksValue.setText(bundle.getString("Remarks"));
       String picSelected=bundle.getString("TypeSelect");
        switch(picSelected)
        {
            case "早餐":
                pictureValue.setImageResource(R.drawable.breakfast);
                picTextValue.setText("早餐");
                break;
            case "午餐":
                pictureValue.setImageResource(R.drawable.lunch);
                picTextValue.setText("午餐");
                break;
            case "晚餐":
                pictureValue.setImageResource(R.drawable.dinner);
                picTextValue.setText("晚餐");
                break;
            case "烟酒":
                pictureValue.setImageResource(R.drawable.wine);
                picTextValue.setText("烟酒");
                break;
            case "零食":
                pictureValue.setImageResource(R.drawable.snack);
                picTextValue.setText("零食");
                break;
            case "水果":
                pictureValue.setImageResource(R.drawable.fruit);
                picTextValue.setText("水果");
                break;
            case "酒店":
                pictureValue.setImageResource(R.drawable.hotel);
                picTextValue.setText("酒店");
                break;
            case "买菜":
                pictureValue.setImageResource(R.drawable.shopping);
                picTextValue.setText("买菜");
                break;
            case "化妆品":
                pictureValue.setImageResource(R.drawable.cosmetic);
                picTextValue.setText("化妆品");
                break;
            case "旅行":
                pictureValue.setImageResource(R.drawable.trip);
                picTextValue.setText("旅行");
                break;
            case "租房":
                pictureValue.setImageResource(R.drawable.tenant);
                picTextValue.setText("租房");
                break;
            case "饮品":
                pictureValue.setImageResource(R.drawable.drink);
                picTextValue.setText("饮品");
                break;
             default:
                 break;


        }
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

    }
}
