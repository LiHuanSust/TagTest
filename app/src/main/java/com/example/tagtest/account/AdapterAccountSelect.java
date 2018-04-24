package com.example.tagtest.account;

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
 * Created by MyFirstPC on 2018/4/24.
 */

public class AdapterAccountSelect extends ArrayAdapter<AccountSelect> {

    private int resourceId;
    public AdapterAccountSelect(Context context, int resourceId, List<AccountSelect> object)
    {
        super(context,resourceId,object);
        this.resourceId=resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       AccountSelect accountSelect=getItem(position);
        View view;
        ViewHold viewHold;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHold=new ViewHold();
            viewHold.accountName=view.findViewById(R.id.account_select_name);
            viewHold.accountPic=view.findViewById(R.id.account_select_pic);
            view.setTag(viewHold);

        }
        else {
            view = convertView;
            viewHold=(ViewHold)view.getTag();
        }
        viewHold.accountPic.setImageResource(accountSelect.getPicId());
        viewHold.accountName.setText(accountSelect.getName());
        return view;
    }
    private class ViewHold
    {
        ImageView accountPic;
        TextView accountName;
    }

}
