package com.example.tagtest.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagtest.R;
import com.example.tagtest.account.Account;
import com.example.tagtest.tools.GetPictureId;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/5/2.
 */

public class AdapterAccountSelect extends BaseAdapter{

        private Context context;
        private List<Account> dataList;
        public AdapterAccountSelect(Context context, List<Account> dataList) {
            this.context = context;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold viewHold;
            View view;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.spinner_account_show_basic, null);
                viewHold=new ViewHold();
                viewHold.accountPic=convertView.findViewById(R.id.spinner_account_pic);
                viewHold.accountName=convertView.findViewById(R.id.spinner_account_name);
                viewHold.accountMoney=convertView.findViewById(R.id.spinner_account_money);
                convertView.setTag(viewHold);
                view=convertView;
            }
            else
                {
                viewHold = (ViewHold) convertView.getTag();
            }
            Account account=dataList.get(position);
            boolean flag=false;
            if(account.getAccountInformation()!=null) {
                if (account.getAccountInformation().isCard())
                    flag = true;
                viewHold.accountPic.setImageResource(GetPictureId.getId(account.getAccoutPicId(), flag));
                viewHold.accountName.setText(account.getAccountName());
                viewHold.accountMoney.setText(account.getAccountInformation().getMoney() + "");
            }
            else
                Log.d("AdapterAccountSelect","null");
            return convertView;
        }
        class ViewHold
        {
            ImageView accountPic; //账户图片
            TextView accountName; //账户名
            TextView accountMoney; //账户可用余额

        }
    }
