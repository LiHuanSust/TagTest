package com.example.tagtest.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tagtest.R;
import com.example.tagtest.drawer.User;

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
         //Account mAccount=new Account();
        mList=DataSupport.where("user=?",User.getNowUserName()).find(Account.class);
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
        Log.d("Fragment3","onResume");
        mList=null;
        mAdapter=null;
        mList=DataSupport.where("user=?", User.getNowUserName()).find(Account.class);
        mAdapter=new AdapterAccount(getActivity(),R.layout.list_view_account_basic,mList);
        listView.setAdapter(mAdapter);

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
        Account account=mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putSerializable("Account",account);
        Intent toShowAccountValue=new Intent(getActivity(), ShowAccountValue.class);
        toShowAccountValue.putExtras(bundle);
        startActivity(toShowAccountValue);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setTitle("警告");
        dialog.setMessage("你确定要删除该账号吗？该操作将删除与之相关的所有记录");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Account account=mList.get(position);
               int num=DataSupport.delete(Account.class,account.getId());  //只有用delete Support删才有级联删除的效果
                Log.d("AAAA",num+"");
              // mList.remove(position);
                mList=null;
                mAdapter=null;
                mList=DataSupport.findAll(Account.class);
                mAdapter=new AdapterAccount(getActivity(),R.layout.list_view_account_basic,mList);
                listView.setAdapter(mAdapter);
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        dialog.show();
        //return true ,不会触发点击事件
        return true;
    }
}
