package com.example.tagtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class FragmentCost extends Fragment implements View.OnClickListener{
    private TextView cost_type=null;
    private TextView dateNow=null;
    private EditText cost_money=null;
    private EditText cost_remarks=null;
    protected LinearLayout bank_card_layout;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_10;
    private Button button_11;
    private Button button_12;
    private Button saveButton; //保存按钮
    private Button clearButton;//清空按钮
    protected Spinner spinner_cost_type; //消费类型下拉框
    protected Spinner spinner_bank_type; //银行下拉框
    private ArrayList<Button> list_button=null;
    public String cost_type_value=null;
    private String bank_select_value=null;
    private boolean flag=false;  //判断收入还是支出
    private GetDate myDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle bundle)
    {
        View view=inflater.inflate(R.layout.frament_cost_add,contain,false);
        return  view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initialise();
        costTypeSelect();



    }
    public void initialise()  //把所有按钮加入到ArrayList中
    {
        myDate=new GetDate();
        dateNow=getActivity().findViewById(R.id.time_now);
        dateNow.setOnClickListener(this);
        list_button=new ArrayList<>();
        button_1=getActivity().findViewById(R.id.button1);
        button_2=getActivity().findViewById(R.id.button2);
        button_3= getActivity().findViewById(R.id.button3);
        button_4=getActivity().findViewById(R.id.button4);
        button_5=getActivity().findViewById(R.id.button5);
        button_6=getActivity().findViewById(R.id.button6);
        button_7=getActivity().findViewById(R.id.button7);
        button_8=getActivity().findViewById(R.id.button8);
        button_9=getActivity().findViewById(R.id.button9);
        button_10=getActivity().findViewById(R.id.button10);
        button_11=getActivity().findViewById(R.id.button11);
        button_12=getActivity().findViewById(R.id.button12);
        saveButton=getActivity().findViewById(R.id.cost_add_save);
        clearButton=getActivity().findViewById(R.id.cost_add_cancel);
        cost_type=getActivity().findViewById(R.id.cost_type); //消费类型显示,TextView
        cost_remarks=getActivity().findViewById(R.id.cost_remarks); //备注，EditText
        cost_money=getActivity().findViewById(R.id.cost_money); //消费金额，TextView
        spinner_cost_type=(Spinner) getActivity().findViewById(R.id.spinner_cost_type);//消费类型选择，Spinner
        bank_card_layout=(LinearLayout)getActivity().findViewById(R.id.bank_card_select);//银行卡选择，Spinner
        spinner_bank_type=(Spinner)getActivity().findViewById(R.id.spinner_bank_select);

        list_button.add(button_1);
        list_button.add(button_2);
        list_button.add(button_3);
        list_button.add(button_4);
        list_button.add(button_5);
        list_button.add(button_6);
        list_button.add(button_7);
        list_button.add(button_8);
        list_button.add(button_9);
        list_button.add(button_10);
        list_button.add(button_11);
        list_button.add(button_12);
        list_button.add(saveButton);
        list_button.add(clearButton);
        for(Button button:list_button)
        {
            button.setOnClickListener(this);
        }
        button_1.setSelected(true);
        cost_type.setText(button_1.getText().toString());
        dateNow.setText(myDate.allToString());
    }
    public void costTypeSelect()
    {
        spinner_cost_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank_card_layout.setVisibility(View.GONE);
                cost_type_value=adapterView.getItemAtPosition(i).toString();
                if(cost_type_value!=null && cost_type_value.equals("银行卡"))
                {
                   bankCardSelected();
                   // Toast.makeText(getActivity(),(spinner_card_select.getVisibility())+" ",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    spinner_bank_type.setSelection(0);
                    bank_select_value="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void bankCardSelected()   //银行卡选择
    {
        bank_card_layout=(LinearLayout)getActivity().findViewById(R.id.bank_card_select);
        bank_card_layout.setVisibility(View.VISIBLE);
        spinner_bank_type=(Spinner)getActivity().findViewById(R.id.spinner_bank_select);
        spinner_bank_type.setSelection(0);
        bank_select_value=spinner_bank_type.getItemAtPosition(0).toString();
        spinner_bank_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank_select_value=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void clearValue() //清空所有内容
    {
        cost_type.setText(button_1.getText().toString());
        setNotSelected();
        button_1.setSelected(true);
        cost_money.setText("");
        cost_remarks.setText("");
        spinner_cost_type.setSelection(0);
        bank_card_layout.setVisibility(View.GONE);

    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.time_now:
                        //时间选择器
                        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                //date显示的是所有的数据情况，所以需要格式化输出
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String format = simpleDateFormat.format(date);
                                dateNow.setText(format);

                            }
                        }).setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                                .build();
                        pvTime.show();
                break;
            case R.id.button1:
                setNotSelected();
                cost_type.setText(button_1.getText().toString());
                button_1.setSelected(true);
                break;
            case R.id.button2:
                setNotSelected();
                cost_type.setText(button_2.getText().toString());
                button_2.setSelected(true);
                break;
            case R.id.button3:
                setNotSelected();
                cost_type.setText(button_3.getText().toString());
                button_3.setSelected(true);
                break;
            case R.id.button4:
                setNotSelected();
                cost_type.setText(button_4.getText().toString());
                button_4.setSelected(true);
                break;
            case R.id.button5:
                setNotSelected();
                cost_type.setText(button_5.getText().toString());
                button_5.setSelected(true);
                break;
            case R.id.button6:
                setNotSelected();
                cost_type.setText(button_6.getText().toString());
                button_6.setSelected(true);
                break;
            case R.id.button7:
                setNotSelected();
                cost_type.setText(button_7.getText().toString());
                button_7.setSelected(true);
                break;
            case R.id.button8:
                setNotSelected();
                cost_type.setText(button_8.getText().toString());
                button_8.setSelected(true);
                break;
            case R.id.button9:
                setNotSelected();
                cost_type.setText(button_9.getText().toString());
                button_9.setSelected(true);
                break;
            case R.id.button10:
                setNotSelected();
                cost_type.setText(button_10.getText().toString());
                button_10.setSelected(true);
                break;
            case R.id.button11:
                setNotSelected();
                cost_type.setText(button_11.getText().toString());
                button_11.setSelected(true);
                break;
            case R.id.button12:
                setNotSelected();
                cost_type.setText(button_12.getText().toString());
                button_12.setSelected(true);
                break;
            case R.id.cost_add_cancel:
                clearValue();
                break;
            case R.id.cost_add_save:
                String costMoneyValue=cost_money.getText().toString();
                if(!cost_type_value.equals("")  && !costMoneyValue.equals(""))
                {
                    //DataSupport.deleteAll(MyDate.class);
                    flag=true;
                    String typeSelect=cost_type.getText().toString(); //消费类型
                    String solution=cost_type_value; //付款类型
                    String bankCard=bank_select_value;
                    String remarks=cost_remarks.getText().toString();
                    GetDate dateNow=new GetDate();
                    int year=dateNow.getYear();
                    int month=dateNow.getMonth();
                    int day=dateNow.getDay();
                    String hourMinuteSecond=dateNow.hourMinuteSecondString();
                   // BigDecimal costMoney=new BigDecimal(costMoneyValue); //java提供的专门用于精确计算的类
                    //costMoney.setScale(2,BigDecimal.ROUND_HALF_UP); //四舍五入，小数保留两位
                    float costMoney=Float.parseFloat(costMoneyValue);
                    MyData newDate=new MyData("lihuan1994",true,typeSelect,solution,year,month,day,hourMinuteSecond,costMoney,bankCard,remarks);
                   if(newDate.save()) {
                       Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                       clearValue();
                   }
                   else
                   {
                       Toast.makeText(getContext(), "添加失败", Toast.LENGTH_SHORT).show();
                   }
                   // Toast.makeText(getContext(),"save successful",Toast.LENGTH_SHORT).show();
                  /* List<MyDate> list= DataSupport.findAll(MyDate.class);
                   for(MyDate date:list)
                   {
                      // Toast.makeText(getContext(),date.toString(),Toast.LENGTH_SHORT).show();
                     // Log.d("MainActivityTest",date.getData());
                       cost_remarks.append(date.toString());
                   }*/
                }
                else
                    Toast.makeText(getContext(),"请将数据填写完整",Toast.LENGTH_SHORT).show();
                 break;

                default:
                    break;

        }
    }
    public void setNotSelected() //使按钮不被选择
    {
        if(list_button!=null) {
            for (Button button : list_button) {
                button.setSelected(false);
            }
        }
    }
}