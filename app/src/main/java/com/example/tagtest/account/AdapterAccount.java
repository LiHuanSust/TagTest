package com.example.tagtest.account;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagtest.R;
import com.example.tagtest.tools.GetPictureId;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/4/24.
 */

public class AdapterAccount extends ArrayAdapter<Account>
    {
        private int resourceId;
        public AdapterAccount(Context context, int resourceId, List<Account> object)
        {
            super(context,resourceId,object);
            this.resourceId=resourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Account mAccount=getItem(position);
            View view;
            ViewHold viewHold;
            if(convertView==null)
            {
                view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
                viewHold=new ViewHold();
                viewHold.accountName=view.findViewById(R.id.account_name);
                viewHold.accountPic=view.findViewById(R.id.account_pic);
                viewHold.money=view.findViewById(R.id.surplus);
                view.setTag(viewHold);

            }
            else {
                view = convertView;
                viewHold=(ViewHold)view.getTag();
            }
            boolean flag=mAccount.getAccountInformation().isCard();
            viewHold.accountPic.setImageResource(GetPictureId.getId(mAccount.getAccoutPicId(),flag));
            viewHold.accountName.setText(mAccount.getAccountName());
            viewHold.money.setText(mAccount.getAccountInformation().getMoney()+"");
            return view;
        }
        private class ViewHold
        {
            ImageView accountPic;
            TextView accountName;
            TextView money;  //此处显示的是净资产

        }
    }
