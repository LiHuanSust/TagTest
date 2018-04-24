package com.example.tagtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.account.Account;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topBar;
    private TextView tabDeal;
    private TextView tabPoi;
    private TextView tabMore;
    private TextView tabUser;
    //add data
    private FragmentManager fragmentManager;
    private MyFragmet1 myFragmet1;
    private  MyFrgment2 myFrgment2;
    private MyFragment3 myFragment3;
    private MyFragment4 myFragment4;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    static final String USER_NAME="Hello";
 //   private boolean flag=false; //判断需不需要重新加载数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connector.getDatabase();//数据库初始化
        if(DataSupport.find(Account.class,1)==null)
        {
            /*Account account=new Account();
            account.setName("默认账户");
            account.setDate(new GetDate().allToString());
            account.setMoneySalary(0.0f);
            account.setMoneyCost(0.0f);
            account.setUser("lihuan");
            account.setPicId(R.drawable.breakfast);
            account.save();*/
            Toast.makeText(this, "empty ,empty ,empty", Toast.LENGTH_SHORT).show();
        }
        bindView();
        tabDeal.setSelected(true);
        getFragment(1);
     //   initeDate();
    }
   /* @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch (requestCode)
        {
            case 1:
                if(resultCode==RESULT_CANCELED)
                {
                    flag=data.getBooleanExtra("IsCostAdd",false);
                }
                break;

        }
    }*/
    @Override
    protected  void onRestart()  //重新启动的时候，暂且不做什么
    {
        super.onRestart();
        //getFragment(1);

         /*   ListView list=(ListView)findViewById(R.id.list_view_day);
            List<MyDate> myDateByDay=DataSupport.findAll(MyDate.class);

            DateAdapter adapter=new DateAdapter(MainActivity.this,R.layout.layout_date_show,myDateByDay);
            ListView listView=(ListView)findViewById(R.id.show_today);
            listView.setAdapter(adapter);*/
    }
  /*  private void initeDate()
    {
        GetData time_now=new GetData();
        String time_part=time_now.getYear()+","+time_now.getMonth()+","+time_now.getDay();
      //  List<MyDate> myDateByDay=DataSupport.where("data like ?",time_part+"%").find(MyDate.class);
       List<MyDate> myDateByDay=DataSupport.findAll(MyDate.class);

        DateAdapter adapter=new DateAdapter(MainActivity.this,R.layout.layout_date_show,myDateByDay);
        ListView listView=(ListView)findViewById(R.id.show_today);
        listView.setAdapter(adapter);


    }*/
    private void bindView()
    {
        //topBar=(TextView)this.findViewById(R.id.txt_top);
        tabDeal=(TextView)findViewById(R.id.txt_deal);
        tabPoi=(TextView)findViewById(R.id.txt_poi);
        tabMore=(TextView)findViewById(R.id.txt_more);
        tabUser=(TextView)findViewById(R.id.txt_user);
        //cost_today=(TextView)findViewById();
//        topBar.setOnClickListener(this);
        tabDeal.setOnClickListener(this);
        tabPoi.setOnClickListener(this);
        tabMore.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.category);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        //addTextView.setOnClickListener(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,ActivityAdd.class);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
            case  R.id.search:
               Intent newIntent=new Intent(MainActivity.this,ActivitySearch.class);
               startActivity(newIntent);
               break;
            default:
        }
        return true;
    }

    }

