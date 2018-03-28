package com.example.tagtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabPoi;
    private TextView tabMore;
    private TextView tabUser;
    //add data
    private List<MyDate> list=new ArrayList<>();
    private TextView dataShow;
    private FragmentManager fragmentManager;
    private MyFragmet1 myFragmet1;
    private  MyFrgment2 myFrgment2;
    private MyFragment3 myFragment3;
    private MyFragment4 myFragment4;
    private  TextView addTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataShow=(TextView)findViewById(R.id.data);
        GetData data=new GetData();
        dataShow.setText(data.partToString());
        bindView();
        initeDate();
    }
    private void initeDate()
    {
      for(int i=0;i<10;i++)
      {
          MyDate date=new MyDate("早餐","","10");
          list.add(date);
      }
      DateAdapter adapter=new DateAdapter(MainActivity.this,R.layout.layout_date_show,list);
        ListView listView=(ListView)findViewById(R.id.show_today);
        listView.setAdapter(adapter);


    }
    private void bindView()
    {
        //topBar=(TextView)this.findViewById(R.id.txt_top);
        tabDeal=(TextView)findViewById(R.id.txt_deal);
        tabPoi=(TextView)findViewById(R.id.txt_poi);
        tabMore=(TextView)findViewById(R.id.txt_more);
        tabUser=(TextView)findViewById(R.id.txt_user);
        addTextView=(TextView)findViewById(R.id.add);
//        topBar.setOnClickListener(this);
        tabDeal.setOnClickListener(this);
        tabPoi.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        addTextView.setOnClickListener(this);
    }
    public void selected()
    {
       // topBar.setSelected(false);
        tabDeal.setSelected(false);
        tabPoi.setSelected(false);
        tabMore.setSelected(false);
        tabUser.setSelected(false);
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.txt_deal:
                selected();
                tabDeal.setSelected(true);
                getFragment(1);

                break;
            case R.id.txt_more:
                selected();
                tabMore.setSelected(true);
                getFragment(4);
                break;
            case R.id.txt_poi:
                selected();
                tabPoi.setSelected(true);
                getFragment(2);
                break;
            case R.id.txt_user:
                selected();
                tabUser.setSelected(true);
                getFragment(3);
                break;
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,ActivityAdd.class);
                startActivity(intent);
                break;

        }
    }
    public void getFragment(int id)
    {
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        Fragment f=null;
        int layout_id;
        layout_id=R.id.fragment_container;
        switch(id)
        {
            case 1:
                f=new MyFragmet1();
                break;
            case 2:
                f=new MyFrgment2();
                break;
            case 3:
                f=new MyFragment3();
                break;
            case 4:
                f=new MyFragment4();
                break;
        }

        if(f!=null) {
            fragmentTransaction.replace(layout_id, f);
            fragmentTransaction.commit();
        }
    }
}
