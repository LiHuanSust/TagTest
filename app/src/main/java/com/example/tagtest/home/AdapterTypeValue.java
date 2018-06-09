package com.example.tagtest.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagtest.R;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/6/8.
 * 按大类分的listView的adapter
 * 内容便是图片+具体的类别
 */

public class AdapterTypeValue extends ArrayAdapter<String>{
    private int resourceId;
    public AdapterTypeValue(Context context, int resourceId, List<String> object)
    {
        super(context,resourceId,object);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       String str=getItem(position);
        View view;
        ViewHold viewHold;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHold=new ViewHold();
            viewHold.pic=view.findViewById(R.id.list_view_type_pic);
            viewHold.value=view.findViewById(R.id.list_view_type_infor);
            view.setTag(viewHold);

        }
        else {
            view = convertView;
            viewHold=(ViewHold)view.getTag();
        }

            // viewHold..setImageResource(getTypeValue.getImageId());
             //viewHold.typeName.setText(getTypeValue.getName());
        return view;
    }
    private class ViewHold
    {
        TextView value;
        ImageView pic;
    }
}
