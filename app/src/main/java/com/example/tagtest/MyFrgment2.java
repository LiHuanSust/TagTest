package com.example.tagtest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by MyFirstPC on 2018/4/13.
 */

public class MyFrgment2 extends Fragment{
    //private List<MyData> list=new ArrayList<>();
    private ListView data_show_list;
    private PtrClassicFrameLayout mPtrFrame;
    private List<MyData> list;
    private DataAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.test2,container,false);
        setHasOptionsMenu(true);
       // data_show_list=(ListView)getActivity().findViewById(R.id.list_today_data);
        //data_show_list=(ListView)view.findViewById(R.id.list_today_data);
       // list=DataSupport.findAll(MyData.class);
        //DataAdapter adapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
        initialise();
        list= DataSupport.findAll(MyData.class);
        data_show_list=(ListView)view.findViewById(R.id.sb_list_view);
        mAdapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
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
      //  getTodayListView();
        setListener();

    }

    public void initialise()  //界面数据初始化
    {
        data_show_list=(ListView)getActivity().findViewById(R.id.sb_list_view);
        //swipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mPtrFrame=(PtrClassicFrameLayout)getActivity().findViewById(R.id.pf);
    }
    public void setListener()//给当前listView item设置监听
    {
        //data_add.setOnClickListener(this);
       /* data_show_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyData data = (MyData) list.get(i);
                Intent intent = new Intent(getActivity(), ListShowCompleteData.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Infor", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/

        //下拉刷新支持时间
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        //下拉刷新一些设置 详情参考文档
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        //进入Activity就进行自动下拉刷新
      /*  mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);*/
        //下拉刷新
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {


            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //模拟数据

                //模拟联网 延迟更新列表
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                       list=DataSupport.findAll(MyData.class,1,2,3,5,6);
                        mAdapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
                        data_show_list.setAdapter(mAdapter);
                        mPtrFrame.refreshComplete();
                        mPtrFrame.setLoadMoreEnable(true);

                    }
                }, 1000);


            }
        });
        //上拉加载

        //上拉加载
        mPtrFrame.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {

                //模拟联网延迟更新数据
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        //模拟数据
                        list=DataSupport.findAll(MyData.class,6,9,11,10);
                        mAdapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
                        data_show_list.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        mPtrFrame.loadMoreComplete(true);

                    }
                }, 1000);


            }
        });



    }
    public void addNextMonthData()
    {

      //  list=DataSupport.findAll(MyData.class,1,2,3,4,5);
       // DataAdapter adapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
        //data_show_list.setAdapter(adapter);

    }
    public void getTodayListView() //获得每日消费情况，按添加顺序倒序
    {
     //   list=DataSupport.findAll(MyData.class);
       // DataAdapter adapter=new DataAdapter(getActivity(),R.layout.layout_list_show,list);
        //data_show_list.setAdapter(adapter);

    }
   /* @Override
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

    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       menu.clear();
       inflater.inflate(R.menu.second_fragment_menu,menu);
    }
}
