package com.example.tagtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/25.
 */

public class MyFragmet1 extends Fragment implements View.OnClickListener{
    private List<MyData> list=new ArrayList<>();
    private TextView cost_today;
    private TextView salary_today;
    private TextView date_of_today;
    private TextView data_add;
    private ListView data_show_list;
    private float cost=0.0f;
    private float salary=0.0f;
    private GetDate dateNow; //当前时间
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.first_fragment,container,false);
        data_show_list=(ListView)getActivity().findViewById(R.id.list_today_data);
        data_show_list=(ListView)view.findViewById(R.id.list_today_data);
        dateNow=new GetDate();
        list= DataSupport.where("year=? and month=? and day=?",dateNow.getYear()+
                "",dateNow.getMonth()+"",dateNow.getDay()+"").order("id desc").find(MyData.class);
        DataAdapter adapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
        for(MyData temp:list)
        {
            if(temp.getType())
            {
                cost+=temp.getMoney();
            }
            else
                salary+=temp.getMoney();
        }
        data_show_list.setAdapter(adapter);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initialise();
        setListener();

    }
    @Override
    public void onResume() //重新可见时刷新listView中的数据
    {
        super.onResume();
        getTodayListView();
        setListener();

    }

    public void initialise()  //界面数据初始化
    {
        cost_today=(TextView)getActivity().findViewById(R.id.cost_of_day);
        salary_today=(TextView)getActivity().findViewById(R.id.salary_of_day);
        cost_today.setText(cost+"");
        salary_today.setText(salary+"");
        date_of_today=(TextView)getActivity().findViewById(R.id.date_of_today);
        data_add=(TextView)getActivity().findViewById(R.id.text_view_add);
        data_show_list=(ListView)getActivity().findViewById(R.id.list_today_data);
        GetDate dateOfToday=new GetDate();
        date_of_today.setText(dateOfToday.partToString());
       /*  list= DataSupport.findAll(MyData.class);
        DataAdapter adapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
       data_show_list.setAdapter(adapter);*/


    }
    public void setListener()//给当前listView item设置监听
    {
      data_add.setOnClickListener(this);
      data_show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              MyData data=(MyData) list.get(i);
             // if(data!=null)
                 // Toast.makeText(getContext(),"you click "+data.getTypeSelect(),Toast.LENGTH_SHORT).show();
             /* if(data.getType()==true)
              {
                  type="消费详情";
              }
              else
                  type="收入详情";
              String typeSelect=data.getTypeSelect();
              String date=data.dateToString();
              String money=String.valueOf(data.getMoney());
              String solution=data.getSolution();
              String cardInfor=null;
              if(data.getBank().equals(""))
              {
                  cardInfor="无银行卡记录";
              }
              else
                  cardInfor=data.getBank();
              String remarks=data.getRemarks();
              Bundle bundle=new Bundle();
              bundle.putString("Type",type);
              bundle.putString("TypeSelect",typeSelect);
              bundle.putString("Date",date);
              bundle.putString("Money",money);
              bundle.putString("Solution",solution);
              bundle.putString("CardInfo",cardInfor);
              bundle.putString("Remarks","       "+remarks);
              Intent intent=new Intent(getActivity(),ListShowCompleteData.class);
              intent.putExtras(bundle);*/
              Intent intent=new Intent(getActivity(),ListShowCompleteData.class);
              Bundle bundle=new Bundle();
              bundle.putSerializable("Infor",data);
              intent.putExtras(bundle);
              startActivity(intent);
          }
      });
    }
    public void getTodayListView() //获得每日消费情况，按添加顺序倒序
    {
        List<MyData> list= DataSupport.where("year=? and month=? and day=?",dateNow.getYear()+
                "",dateNow.getMonth()+"",dateNow.getDay()+"").order("id desc").find(MyData.class);
        cost=0.0f;
        salary=0.0f;
        for(MyData temp:list)
        {
            if(temp.getType())
            {
                cost+=temp.getMoney();
            }
            else
                salary+=temp.getMoney();
        }
        cost_today.setText(cost+"");
        salary_today.setText(salary+"");
        DataAdapter adapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
        data_show_list.setAdapter(adapter);

    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.text_view_add:
                Intent intentAdd=new Intent(getActivity(),ActivityAdd.class);
                startActivity(intentAdd);
                break;
            default:

        }

    }
}
