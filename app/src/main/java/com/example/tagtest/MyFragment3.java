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

import com.example.tagtest.account.Account;
import com.example.tagtest.account.AdapterAccount;
import com.example.tagtest.account.SelectAccount;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/25.
 */

public class MyFragment3 extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private ListView listView;
    private TextView cost; //花费
    private TextView budget; //预算
    private TextView total; //统计（净资产）
    private TextView addAccount;
    private List<Account> mList;
    private AdapterAccount mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState)
    {


        View view=inflater.inflate(R.layout.third_fragment,container,false);
        listView=view.findViewById(R.id.list_view_account);
       // Account mAccount=new Account();
        mList= DataSupport.findAll(Account.class);
        mAdapter=new AdapterAccount(getActivity(),R.layout.list_view_account_basic,mList);
        listView.setAdapter(mAdapter);
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
        mList=DataSupport.findAll(Account.class);
       mAdapter.notifyDataSetChanged();

    }
    public void initialise()
    {
        cost=getActivity().findViewById(R.id.cost_number);
        budget=getActivity().findViewById(R.id.budget);
        total=getActivity().findViewById(R.id.total);
        addAccount=getActivity().findViewById(R.id.add_account);
    }
    public void setListener()
    {
      addAccount.setOnClickListener(this);
      listView.setOnItemClickListener(this);
      listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.add_account:
                Intent intent=new Intent(getActivity(),SelectAccount.class);
                startActivity(intent);
                 break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Account mAccount=mList.get(position);
        
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
