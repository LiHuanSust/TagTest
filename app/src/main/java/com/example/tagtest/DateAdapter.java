package com.example.tagtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class DateAdapter extends ArrayAdapter<MyDate>
{
    private int resourceId;
    public DateAdapter(Context context, int resourceId, List<MyDate> object)
    {
        super(context,resourceId,object);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MyDate date=getItem(position);
        View view;
        ViewHold viewHold;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView type=(TextView)view.findViewById(R.id.type);
            TextView money=(TextView)view.findViewById(R.id.number);
            //type.setText(date.getType());
            //money.setText(date.getMoney());
            viewHold=new ViewHold();
            viewHold.money=money;
            viewHold.type=type;
            view.setTag(viewHold);


        }
        else {
            view = convertView;
            viewHold=(ViewHold)view.getTag();

        }
       viewHold.type.setText(date.getType());
        viewHold.money.setText(date.getMoney());
        return view;

    }
    class ViewHold
    {
        public TextView type;
        public TextView money;
    }

}
