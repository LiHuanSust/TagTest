package com.example.tagtest.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tagtest.DataAdapter;
import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.tools.GetDate;
import com.example.tagtest.tools.MyCalculate;
import com.example.tagtest.values.ListShowCompleteData;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/25.
 */

public class MyFragmet1 extends Fragment implements View.OnClickListener{
    private List<MyData> list;
    private TextView cost_today;
    private TextView salary_today;
    private TextView date_of_today;
    private TextView data_add;
    private ListView data_show_list;
    private String cost="0";
    private String salary="0";
    private GetDate dateNow; //当前时间
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.first_fragment,container,false);
      //  data_show_list=(ListView)getActivity().findViewById(R.id.list_today_data);
        data_show_list=(ListView)view.findViewById(R.id.list_today_data);
        dateNow=new GetDate();
        //DataSupport方法会返回一个arrayList,它不会为空
        list= DataSupport.where("year=? and month=? and day=?",dateNow.getYear()+
                "",dateNow.getMonth()+"",dateNow.getDay()+"").order("id desc").find(MyData.class);
        DataAdapter adapter=new DataAdapter(getActivity(),R.layout.list_view_basic_show,list);
        for(MyData temp:list)
        {
            if(temp.getType())
            {
                cost= MyCalculate.add(cost,temp.getMoney());
            }
            else
               salary=MyCalculate.add(salary,temp.getMoney());
        }

        data_show_list.setAdapter(adapter);

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initialise();
         //getTodayListView();
        setListener();

    }
    @Override
    public void onResume() //重新可见时刷新listView中的数据
    {
        Log.d("HelloWold","onResume()调用");
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
      data_show_list=getActivity().findViewById(R.id.list_today_data);
      data_show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //list一定要是更新后的
              Intent intent=new Intent(getActivity(),ListShowCompleteData.class);
              intent.putExtra("MyDataId",list.get(i).getId());
              startActivity(intent);
          }
      });
    }
    public void getTodayListView() //获得每日消费情况，按添加顺序倒序
    {
        list= DataSupport.where("year=? and month=? and day=?",dateNow.getYear()+
                "",dateNow.getMonth()+"",dateNow.getDay()+"").order("id desc").find(MyData.class);
        cost="0";
        salary="0";
        for(MyData temp:list)
        {
            if(temp.getType())
            {
                cost=MyCalculate.add(cost,temp.getMoney());
            }
            else
                salary=MyCalculate.add(salary,temp.getMoney());
        }
        cost_today.setText(cost);
        salary_today.setText(salary);
        DataAdapter adapter=new DataAdapter(getActivity(),R.layout.list_view_basic_show,list);
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
