package com.example.tagtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/4/13.
 */

public class MyFrgment2 extends Fragment{
    private ListView data_show_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView salaryOfMonth;
    private TextView costOfMonth;
    private TextView dateOfMonth;
    private GetDate dateNow;
    private TextView listViewEmpty; //当listView为空时显示它
    private Button buttonCancel;
    private Button buttonDelect;
    private TextView selectNum;
    private LinearLayout mLinearLayout;
    private List<MyData> list;
    private int numberOfSelect=0;
    private List<Integer> selectId = new ArrayList<>();
    private boolean isDeleteMode = false; //是否多选
    private MyAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.second_fragment,container,false);
        setHasOptionsMenu(true);
        data_show_list=view.findViewById(R.id.list_view);
        dateNow=new GetDate();
        list=DataSupport.where("year=? and month=?",dateNow.getYear()+"",dateNow.getMonth()+""
                ).find(MyData.class);
        mAdapter=new MyAdapter();
        data_show_list.setAdapter(mAdapter);
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
        list=DataSupport.where("year=? and month=?",dateNow.getYear()+"",dateNow.getMonth()+""
        ).find(MyData.class);
        mAdapter.notifyDataSetChanged();

    }

    public void initialise()  //控件初始化
    {
       // listViewEmpty=(TextView)getActivity().findViewById(R.id.list_view_empty);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh);
        costOfMonth=(TextView)getActivity().findViewById(R.id.cost_of_month);
        salaryOfMonth=(TextView)getActivity().findViewById(R.id.salary_of_month);
        dateOfMonth=(TextView)getActivity().findViewById(R.id.date_of_month);
        dateOfMonth.setText(dateNow.getYearMonth());
        buttonCancel=getActivity().findViewById(R.id.button_cancel);
        buttonDelect=getActivity().findViewById(R.id.button_delete);
        selectNum=getActivity().findViewById(R.id.button_sum);
        mLinearLayout=getActivity().findViewById(R.id.linearLayout_hide);
        buttonDelect=getActivity().findViewById(R.id.button_delete);
        buttonCancel=getActivity().findViewById(R.id.button_cancel);
    }
    public void setListener()//给当前控件设置监听
    {
        //listView点击事件
       data_show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MyData data = (MyData) list.get(i);
                    Intent intent = new Intent(getActivity(), ListShowCompleteData.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Infor", data);
                    intent.putExtras(bundle);
                    startActivity(intent);
            }
        });
       //长按事件
       data_show_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               isDeleteMode = true;
               selectId.clear();
              mLinearLayout.setVisibility(View.VISIBLE);
              mAdapter.notifyDataSetChanged();
               return true;
           }
       });
       //滑动事件
       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               addNextMonthData();
           }
       });

       //日期选择器
        dateOfMonth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //时间选择器，第三方库TimePicker的使用
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        //date显示的是所有的数据情况，所以需要格式化输出
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy,MM");
                        String format = simpleDateFormat.format(date);
                        String[] temp=format.split(","); //将年，月拿到
                        refreshData(temp[0],temp[1]);
                        dateOfMonth.setText(format);

                    }
                }).build();
                pvTime.show();

            }
            /*private  DatePickerDialog.OnDateSetListener mDateListen=new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Log.d("hello world",year+","+month+","+dayOfMonth);
                }
            };*/

        });
        //删除item的按钮添加监听
        buttonDelect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //弹出对话框
                if(selectId.size()==0)
                {
                    Toast.makeText(getActivity(),"请先进行选择",Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("提示");
                dialog.setMessage("你确定要删除所选项吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          long[] temp={};
                          MyData data;
                          int len=0;
                          for(int i:selectId)
                          {
                            data=list.get(i);
                            list.remove(i);
                            //因为data是持久化对象（已经存在于数据库中），所以可以调用delect方法直接删除
                            data.delete();

                          }
                        //将selectId里面的内容清空
                        selectId.clear();
                          //将控件隐藏
                        mLinearLayout.setVisibility(View.GONE);
                        //切换删除选项
                        isDeleteMode=false;
                        //通知适配器更新数据
                        mAdapter.notifyDataSetChanged();

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                             return;
                    }
                });
                dialog.show();
                }
            }
        });
       buttonCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                isDeleteMode=false;
                mLinearLayout.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    public void addNextMonthData()
    {
               String[] x;
               x=dateOfMonth.getText().toString().split(",");
               int year=Integer.parseInt(x[0]);
               int month=Integer.parseInt(x[1]);
                if(month+1>12)
                {
                    year+=1;
                    month=1;
                }
                else
                    month+=1;
                Log.d("HelloWorld",year+","+month);
                list=DataSupport.where("year=? and month=?",year+"",month+"").find(MyData.class);
                String temp=year+","+month;
                dateOfMonth.setText(temp);
                mAdapter.notifyDataSetChanged();
                //刷新完毕
                swipeRefreshLayout.setRefreshing(false);

    }
    public void refreshData(String year,String month) //根据年，月刷新数据
    {
        list=DataSupport.where("year=? and month=?",year,month).find(MyData.class);
        mAdapter.notifyDataSetChanged();;
    }

    //menu的获取
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       menu.clear();
       inflater.inflate(R.menu.second_fragment_menu,menu);
    }

     //使用内部类可以方便的获取数据，对外部类的控件直接进行操作
    class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater = null;

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.layout_list_view_second_basic, null);
                holder = new ViewHolder();
                holder.type= (TextView) convertView.findViewById(R.id.type_value);
                holder.date=(TextView)convertView.findViewById(R.id.date);
                holder.money=(TextView)convertView.findViewById(R.id.money_number);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.check_box);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.type.setText(list.get(position).getTypeSelect());
            holder.date.setText(list.get(position).getMonthDay());
            holder.money.setText(list.get(position).getMoney()+"");
            holder.checkBox.setChecked(false);
            if (isDeleteMode) {
                holder.checkBox.setVisibility(View.VISIBLE);
            } else {
                holder.checkBox.setVisibility(View.INVISIBLE);
            }
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.checkBox.isChecked()) {
                        selectId.add(position);
                        ++numberOfSelect;
                        selectNum.setText("共选择了"+numberOfSelect+"项");
                        Log.d("tag","添加"+position);
                    }else{
                        if(selectId.contains(position))
                             selectId.remove((Integer)position);
                        if(selectId.size()!=0)
                        {
                            --numberOfSelect;
                            selectNum.setText("共选择了"+numberOfSelect+"项");
                        }
                        else
                        {
                            numberOfSelect=0;
                            selectNum.setText("共选择了"+numberOfSelect+"项");
                        }

                    }
                    Log.d("tag",position+"被选中");
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView type;
            TextView date;
            TextView money;
            CheckBox checkBox;
        }
    }
}
