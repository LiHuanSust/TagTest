package com.example.tagtest.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.account.Account;
import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.tools.GetDate;
import com.example.tagtest.tools.MyCalculate;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class FragmentCost extends Fragment implements View.OnClickListener{
    private TextView costType=null;
    private TextView dateNow=null;
    private EditText costMoney=null;
    private EditText costRemarks=null;
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
    private boolean flag=false;  //判断收入还是支出
    private GetDate myDate;
    private AdapterAccountSelect adapterAccountSelect;
    private List<Account> mList;  //spinner的list数据
    private Account mAccount;//相应的账户
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
        costType=getActivity().findViewById(R.id.cost_type); //消费类型显示,TextView
        costRemarks=getActivity().findViewById(R.id.cost_remarks); //备注，EditText
        costMoney=getActivity().findViewById(R.id.cost_money); //消费金额，TextView
        spinner_cost_type=(Spinner) getActivity().findViewById(R.id.spinner_cost_type);//消费时账户类型选择，Spinner
        bank_card_layout=(LinearLayout)getActivity().findViewById(R.id.bank_card_select);//银行卡选择，Spinner
        spinner_bank_type=(Spinner)getActivity().findViewById(R.id.spinner_bank_select);
        mList= DataSupport.findAll(Account.class);
        adapterAccountSelect=new AdapterAccountSelect(getActivity(),mList);
        spinner_cost_type.setAdapter(adapterAccountSelect);

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
        costType.setText(button_1.getText().toString());
        dateNow.setText(myDate.allToString());
    }
    public void costTypeSelect()
    {
        spinner_cost_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank_card_layout.setVisibility(View.GONE);
                mAccount=mList.get(i);

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
       // bank_select_value=spinner_bank_type.getItemAtPosition(0).toString();
       // spinner_bank_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         /*   @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank_select_value=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }
    public void clearValue() //清空所有内容
    {
        costType.setText(button_1.getText().toString());
        setNotSelected();
        button_1.setSelected(true);
        costMoney.setText("");
        costRemarks.setText("");
        spinner_cost_type.setSelection(0);
        bank_card_layout.setVisibility(View.GONE);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                costType.setText(button_1.getText().toString());
                button_1.setSelected(true);
                break;
            case R.id.button2:
                setNotSelected();
                costType.setText(button_2.getText().toString());
                button_2.setSelected(true);
                break;
            case R.id.button3:
                setNotSelected();
                costType.setText(button_3.getText().toString());
                button_3.setSelected(true);
                break;
            case R.id.button4:
                setNotSelected();
                costType.setText(button_4.getText().toString());
                button_4.setSelected(true);
                break;
            case R.id.button5:
                setNotSelected();
                costType.setText(button_5.getText().toString());
                button_5.setSelected(true);
                break;
            case R.id.button6:
                setNotSelected();
                costType.setText(button_6.getText().toString());
                button_6.setSelected(true);
                break;
            case R.id.button7:
                setNotSelected();
                costType.setText(button_7.getText().toString());
                button_7.setSelected(true);
                break;
            case R.id.button8:
                setNotSelected();
                costType.setText(button_8.getText().toString());
                button_8.setSelected(true);
                break;
            case R.id.button9:
                setNotSelected();
                costType.setText(button_9.getText().toString());
                button_9.setSelected(true);
                break;
            case R.id.button10:
                setNotSelected();
                costType.setText(button_10.getText().toString());
                button_10.setSelected(true);
                break;
            case R.id.button11:
                setNotSelected();
                costType.setText(button_11.getText().toString());
                button_11.setSelected(true);
                break;
            case R.id.button12:
                setNotSelected();
                costType.setText(button_12.getText().toString());
                button_12.setSelected(true);
                break;
            case R.id.cost_add_cancel:
                clearValue();
                break;
            case R.id.cost_add_save:
                String date = dateNow.getText().toString();
                String money = costMoney.getText().toString();
                String type = costType.getText().toString();
                String remarks = costRemarks.getText().toString();
                if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(type)) {
                    String[] dateInformation = date.split(" "); //把年月日与时分秒分开
                    String[] yearMonthDay = dateInformation[0].split("-");//将年月日分开
                    int year = Integer.parseInt(yearMonthDay[0]);
                    int month = Integer.parseInt(yearMonthDay[1]);
                    int day = Integer.parseInt(yearMonthDay[2]);
                    //float Money = Float.parseFloat(money);
                    MyData myData = new MyData();
                    myData.setUser("user");
                    myData.setType(true);
                    myData.setTypeSelect(type);
                    myData.setYear(year);
                    myData.setMonth(month);
                    myData.setDay(day);
                    myData.setHourMinuteSecond(dateInformation[1]);
                    myData.setAccount(mAccount);
                    myData.setAccountId(mAccount.getId());
                    myData.setRemarks(remarks);
                    Log.d("Hello",type);
                    myData.setMoney(money);
                    if (myData.save()) {
                        mAccount.getList().add(myData);
                        AccountInformation accountInformation = DataSupport.find(AccountInformation.class, mAccount.getId());
                        Log.d("Right", accountInformation.toString());
                        String accountCost = MyCalculate.add(accountInformation.getCost(),money);
                        String accountMoney = MyCalculate.sub(accountInformation.getMoney(),money);
                        int accountNum = accountInformation.getNum() + 1;
                        accountInformation.setCost(accountCost);
                        accountInformation.setMoney(accountMoney);
                        accountInformation.setNum(accountNum);
                        accountInformation.setDateAdd(date);
                        if (accountInformation.save()) {
                            Log.d("Right", accountInformation.getId()+"");
                            Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                            clearValue();
                        }
                        else
                            Toast.makeText(getActivity(), "关联表更新失败！", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"数据保存失败",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    Toast.makeText(getActivity(),"请将数据填写完整",Toast.LENGTH_SHORT).show();
                    break;
                }
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


 /*if(myData.save())
                    {
                        //Account里面添加相应的data数据
                        mAccount.getList().add(myData);
                        AccountInformation accountInformation=mAccount.getAccountInformation();
                        float accountCost=accountInformation.getCost()+Money;
                        float accountMoney=accountInformation.getMoney()-accountCost;
                        int num=accountInformation.getNum()+1;
                        Log.d("Test",accountCost+"||"+accountMoney);
                        accountInformation.setCost(accountCost);
                        accountInformation.setMoney(accountMoney);
                        accountInformation.setNum(num);
                        accountInformation.setDateAdd(date);
                        if(accountInformation.save() && mAccount.save())
                        {
                            Toast.makeText(getActivity(),"保存成功！",Toast.LENGTH_SHORT).show();
                            clearValue();
                        }
                         else
                            Toast.makeText(getActivity(),"关联表更新失败！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"数据保存失败",Toast.LENGTH_SHORT).show();

                    break;
                }
                else
                {
                    Toast.makeText(getActivity(),"请将数据填写完整",Toast.LENGTH_SHORT).show();
                    break;
                }*/