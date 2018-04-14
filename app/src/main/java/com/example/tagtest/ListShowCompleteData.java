package com.example.tagtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

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
        data=(MyData)getIntent().getSerializableExtra("Infor");
        if(data!=null && data.getType()==true)
        {
            typeValue.setText("消费详情");
        }
        else
        {
            typeValue.setText("收入详情");
        }
        dateValue.setText(data.dateToString());
        moneyValue.setText(data.getMoney()+"");
        payValue.setText(data.getSolution());
        if(data.getBank()!=null && !data.getBank().equals(""))
        {
            cardValue.setText(data.getBank());
        }
        else
        {
            cardValue.setText("无银行卡消费记录");
        }
        payValue.setText(data.getSolution());
        remarksValue.setText("    "+data.getRemarks());
        String picSelected=data.getTypeSelect();
        picTextValue.setText(picSelected);
     //   setPicture(picSelected);
       /* Bundle bundle=getIntent().getExtras();
        typeValue.setText(bundle.getString("Type"));
        dateValue.setText(bundle.getString("Date"));
        moneyValue.setText(bundle.getString("Money"));
        payValue.setText(bundle.getString("Solution"));
        cardValue.setText(bundle.getString("CardInfo"));
        remarksValue.setText(bundle.getString("Remarks"));
       String picSelected=bundle.getString("TypeSelect");
       dataNow=new MyData();

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


        }*/
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
              //  Toast.makeText(this,"hello+ "+data.getId(),Toast.LENGTH_SHORT).show();
                DataSupport.delete(MyData.class,data.getId());
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                //写个弹窗确认吧。。
                break;

                //DataSupport.deleteAll(MyData.class,"")

        }

    }

}
