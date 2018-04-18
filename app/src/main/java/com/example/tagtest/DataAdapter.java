package com.example.tagtest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class DataAdapter extends ArrayAdapter<MyData>
{
    private boolean flag=false; //判断是消费还是收入
    private int resourceId;
    public DataAdapter(Context context, int resourceId, List<MyData> object)
    {
        super(context,resourceId,object);
        this.resourceId=resourceId;
    }
    @Override
   public View getView(int position, View convertView, ViewGroup parent)
    {
        MyData data=getItem(position);
        View view;
        ViewHold viewHold;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
           TextView type=(TextView)view.findViewById(R.id.type);
           TextView date=(TextView)view.findViewById(R.id.date);
           TextView money=(TextView)view.findViewById(R.id.number);
           boolean flag=data.getType();
            //type.setText(date.getType());
            //money.setText(date.getMoney());
            viewHold=new ViewHold();
            viewHold.money=money;
            viewHold.type=type;
            viewHold.date=date;
            view.setTag(viewHold);


        }
        else {
            view = convertView;
            viewHold=(ViewHold)view.getTag();

        }
       viewHold.type.setText(data.getTypeSelect());
        viewHold.date.setText(data.getHourMinuteSecond());
        viewHold.money.setText(String.valueOf(data.getMoney()));
        if(flag)
        {
            viewHold.money.setTextColor(Color.RED);
        }
        else
        {
            viewHold.money.setTextColor(Color.GREEN);
        }
        return view;

    }
    private class ViewHold
    {
        public TextView type;
        public TextView date;
        public TextView money;

    }

}
