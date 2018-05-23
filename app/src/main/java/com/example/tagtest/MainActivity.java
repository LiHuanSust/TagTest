package com.example.tagtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import com.example.tagtest.account.MyFragment3;
import com.example.tagtest.drawer.ActivityExportData;
import com.example.tagtest.home.ActivityAdd;
import com.example.tagtest.home.MyFragmet1;
import com.example.tagtest.tables.ActivityShowDataByAccount;
import com.example.tagtest.tables.ActivityShowDataByMonth;
import com.example.tagtest.tables.ActivityShowDataByYear;
import com.example.tagtest.tables.MyFragment4;
import com.example.tagtest.values.ActivitySearch;
import com.example.tagtest.values.MyFrgment2;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tagHome;
    private TextView tagValues;
    private TextView tagAccount;
    private TextView tagTable;
    private FragmentManager fragmentManager;
    //private FragmentTransaction fragmentTransaction;
    private MyFragmet1 myFragmet1; //首页
    private MyFrgment2 myFrgment2; //详情页
    private MyFragment3 myFragment3; //账号管理页
    private MyFragment4 myFragment4; //图表展示页
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
        buildFragment();
        inilalise();
        tagHome.setSelected(true);


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
    private void inilalise()
    {
        //topBar=(TextView)this.findViewById(R.id.txt_top);
        tagHome=(TextView)findViewById(R.id.home);
        tagValues=(TextView)findViewById(R.id.values);
        tagAccount=(TextView)findViewById(R.id.account);
        tagTable=(TextView)findViewById(R.id.table);
        //cost_today=(TextView)findViewById();
//        topBar.setOnClickListener(this);
        tagHome.setOnClickListener(this);
        tagValues.setOnClickListener(this);
        tagAccount.setOnClickListener(this);
        tagTable.setOnClickListener(this);
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
        final NavigationView navigationView=(NavigationView)findViewById(R.id.drawer_view);
        //navigationView.setCheckedItem(R.id.push_data);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   switch(item.getItemId())
                   {
                       case R.id.push_data:
                          /* drawerLayout.closeDrawers();
                           PopupMenu popup = new PopupMenu(MainActivity.this,navigationView);
                           //Inflating the Popup using xml file
                           popup.getMenuInflater()
                                   .inflate(R.menu.select_how_to_export_data, popup.getMenu());

                           //registering popup with OnMenuItemClickListener
                           popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                               public boolean onMenuItemClick(MenuItem item) {

                                   return true;
                               }
                           });

                           popup.show(); //showing*/
                          Intent selectExportData=new Intent(MainActivity.this, ActivityExportData.class);
                          startActivity(selectExportData);
                           break;

                   }
                   return true;
            }
        });

        //addTextView.setOnClickListener(this);
    }
    public void selectedChange()  //改变tag的选中状态
    {
        tagHome.setSelected(false);
        tagValues.setSelected(false);
        tagAccount.setSelected(false);
        tagTable.setSelected(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public void onClick(View v)
    {
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        //FragmentTransaction只能提交一次事物，若多次调用，则会报错，所以此处采用临时变量
        //事务执行完毕后需要提交
        switch(v.getId())
        {
            case R.id.home:
                selectedChange();
                tagHome.setSelected(true);
                if(myFragmet1.isAdded())
                {
                    fragmentTransaction.show(myFragmet1);
                }
                else
                    fragmentTransaction.add(R.id.fragment_main,myFragmet1);

                hideFragment(myFrgment2,fragmentTransaction);
                hideFragment(myFragment3,fragmentTransaction);
                hideFragment(myFragment4,fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.values:
                selectedChange();
                tagValues.setSelected(true);
                if(myFrgment2.isAdded())
                {
                    fragmentTransaction.show(myFrgment2);
                }
                else
                    fragmentTransaction.add(R.id.fragment_main,myFrgment2);
                hideFragment(myFragmet1,fragmentTransaction);
                hideFragment(myFragment3,fragmentTransaction);
                hideFragment(myFragment4,fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.account:
                selectedChange();
                tagAccount.setSelected(true);
               if(myFragment3.isAdded())
               {
                   fragmentTransaction.show(myFragment3);
               }
               else
               {
                   fragmentTransaction.add(R.id.fragment_main,myFragment3);
               }
                hideFragment(myFragmet1,fragmentTransaction);
                hideFragment(myFrgment2,fragmentTransaction);
                hideFragment(myFragment4,fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.table:
                selectedChange();
                tagTable.setSelected(true);
                if(myFragment4.isAdded())
                {
                    fragmentTransaction.show(myFragment4);
                }
                else
                    fragmentTransaction.add(R.id.fragment_main,myFragment4);
                hideFragment(myFragmet1,fragmentTransaction);
                hideFragment(myFrgment2,fragmentTransaction);
                hideFragment(myFragment3,fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.add:
                Intent intent=new Intent(MainActivity.this,ActivityAdd.class);
                startActivity(intent);
                break;

        }
    }
    public void buildFragment()
    {
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        myFragmet1=new MyFragmet1();
        myFrgment2=new MyFrgment2();
        myFragment3=new MyFragment3();
        myFragment4=new MyFragment4();
       // fragmentTransaction.replace(R.id.fragment_main,myFragmet1);
        fragmentTransaction.add(R.id.fragment_main,myFragmet1).commit();
            //fragmentTransaction.replace(layout_id, f);
            //fragmentTransaction.commit();
    }

    //隐藏fragment
    public void hideFragment(Fragment fragment,FragmentTransaction fragmentTransaction)
    {
        if(fragment!=null && fragment.isAdded())
        {
            fragmentTransaction.hide(fragment);
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
            case R.id.item_data_with_month:
                //线性表的展示
                Intent showDataByMonth=new Intent(MainActivity.this,ActivityShowDataByMonth.class);
                startActivity(showDataByMonth);
                break;
            case R.id.item_data_with_account:
                //按账号类别进行统计，以条形图的形式展现
                Intent showDataByAccount=new Intent(MainActivity.this, ActivityShowDataByAccount.class);
                startActivity(showDataByAccount);
                break;
            case R.id.item_data_with_year:
                //以年为单位统计各月支出与收入
                Intent showDataByYear=new Intent(MainActivity.this, ActivityShowDataByYear.class);
                startActivity(showDataByYear);
                break;
            default:
        }
        return true;
    }

    }

