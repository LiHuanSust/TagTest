package com.example.tagtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.account.Account;
import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.account.MyFragment3;
import com.example.tagtest.drawer.ActivityExportData;
import com.example.tagtest.drawer.ActivityLogin;
import com.example.tagtest.drawer.ActivityUserInfor;
import com.example.tagtest.drawer.ActivityloadFile;
import com.example.tagtest.drawer.User;
import com.example.tagtest.home.ActivityAdd;
import com.example.tagtest.home.MyFragment1;
import com.example.tagtest.tables.ActivityShowDataByAccount;
import com.example.tagtest.tables.ActivityShowDataByMonth;
import com.example.tagtest.tables.ActivityShowDataByYear;
import com.example.tagtest.tables.MyFragment4;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.values.ActivitySearch;
import com.example.tagtest.values.MyFragment2;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tagHome;
    private TextView tagValues;
    private TextView tagAccount;
    private TextView tagTable;
    private FragmentManager fragmentManager;
    //private FragmentTransaction fragmentTransaction;
    private MyFragment1 myFragment1; //首页
    private MyFragment2 myFragment2; //详情页
    private MyFragment3 myFragment3; //账号管理页
    private MyFragment4 myFragment4; //图表展示页
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private static String USER_NAME = "temp";
    //存放user信息的sharedPreference的文件名
    private static final String USER_URL = "userData";
    private String user;
    private TextView userAccount; //显示drawerLayout中的账号登录信息
    //drawerLayout中的图片
    private de.hdodenhof.circleimageview.CircleImageView userPicture;


    //   private boolean flag=false; //判断需不需要重新加载数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //恢复信息
        if(savedInstanceState!=null)
        {
            myFragment1=(MyFragment1)getSupportFragmentManager().getFragment(savedInstanceState,"MyFragment1");
            myFragment2=(MyFragment2)getSupportFragmentManager().getFragment(savedInstanceState,"MyFragment2");
            myFragment3=(MyFragment3)getSupportFragmentManager().getFragment(savedInstanceState,"MyFragment3");
            myFragment4=(MyFragment4)getSupportFragmentManager().getFragment(savedInstanceState,"MyFragment4");
        }
        buildFragment();
        user = getIntent().getStringExtra("UserName");
        //如果是被另一个活动唤起的
        // userAccount=(TextView)findViewById(R.id.user_account);
        if (user != null) {
            USER_NAME = user;
            SharedPreferences.Editor editor = getSharedPreferences(USER_URL, MODE_PRIVATE).edit();
            editor.putString("name", user);
            editor.putBoolean("isTemp", false);
            editor.apply();
            User.setNowUserName(user);
            User.setUserIsTemp(false);
            //  userAccount.setText(user);
        }
        //如果是自己启动的，则读取信息给user中的信息进行赋值
        //之后所有操作都是围绕该用户展开的
        else {
            SharedPreferences preferences = getSharedPreferences(USER_URL, MODE_PRIVATE);
            String name = preferences.getString("name", USER_NAME);
            boolean isTemp = preferences.getBoolean("isTemp", true);
            User.setNowUserName(name);
            User.setUserIsTemp(isTemp);
            //userAccount.setText(name);
        }
        ActivityCollector.addActivity(this);
        Connector.getDatabase();//数据库初始化
        //判断是否有数据需要更新
        updateData();
        inilalise();
        tagHome.setSelected(true);


        //   initeDate();
    }

    @Override
    protected void onRestart()  //重新启动的时候，暂且不做什么
    {
        super.onRestart();

    }

    private void inilalise() {
        //topBar=(TextView)this.findViewById(R.id.txt_top);
        tagHome = (TextView) findViewById(R.id.home);
        tagValues = (TextView) findViewById(R.id.values);
        tagAccount = (TextView) findViewById(R.id.account);
        tagTable = (TextView) findViewById(R.id.table);
        //cost_today=(TextView)findViewById();
//        topBar.setOnClickListener(this);
        tagHome.setOnClickListener(this);
        tagValues.setOnClickListener(this);
        tagAccount.setOnClickListener(this);
        tagTable.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.category);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        final NavigationView navigationView = (NavigationView) findViewById(R.id.drawer_view);
        View drawerHeadView = navigationView.inflateHeaderView(R.layout.drawer_layout_head);
        userPicture = drawerHeadView.findViewById(R.id.user_picture);
        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserInfo = new Intent(MainActivity.this, ActivityUserInfor.class);
                startActivity(intentUserInfo);
            }
        });


        //navigationView.setCheckedItem(R.id.push_data);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.push_data:
                        Intent selectExportData = new Intent(MainActivity.this, ActivityExportData.class);
                        startActivity(selectExportData);
                        break;
                    case R.id.login:
                        Intent loginIntent = new Intent(MainActivity.this, ActivityLogin.class);
                        startActivity(loginIntent);
                        break;
                    case R.id.load_data:
                        Intent loadIntent = new Intent(MainActivity.this, ActivityloadFile.class);
                        startActivity(loadIntent);
                        break;
                    case R.id.cancel:
                        if(User.getUserIsTemp())
                        {
                            Toast.makeText(MainActivity.this,"您还没有登录，无法注销呢！！！",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            drawerLayout.closeDrawers();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("通知：");
                            dialog.setMessage("您确定要注销吗？注销前请记得把数据备份哦~");
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("我手滑了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    drawerLayout.openDrawer(GravityCompat.START);
                                }
                            });
                            //注销的话将修改配置文件信息
                            dialog.setNegativeButton("立即注销", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences(USER_URL, MODE_PRIVATE).edit();
                                    editor.putString("name", USER_NAME);
                                    editor.putBoolean("isTemp", true);
                                    editor.apply();
                                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                    ActivityCollector.finishAll();
                                    startActivity(intent);
                                    Toast.makeText(MainActivity.this,"注销成功！",Toast.LENGTH_SHORT).show();

                                }

                            });
                            dialog.show();
                        }
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //FragmentTransaction只能提交一次事物，若多次调用，则会报错，所以此处采用临时变量
        //事务执行完毕后需要提交
        switch (v.getId()) {
            case R.id.home:
                selectedChange();
                tagHome.setSelected(true);
                if (myFragment1.isAdded()) {
                    fragmentTransaction.show(myFragment1);
                } else
                    fragmentTransaction.add(R.id.fragment_main, myFragment1);

                hideFragment(myFragment2, fragmentTransaction);
                hideFragment(myFragment3, fragmentTransaction);
                hideFragment(myFragment4, fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.values:
                selectedChange();
                tagValues.setSelected(true);
                if (myFragment2.isAdded()) {
                    fragmentTransaction.show(myFragment2);
                } else
                    fragmentTransaction.add(R.id.fragment_main, myFragment2);
                hideFragment(myFragment1, fragmentTransaction);
                hideFragment(myFragment3, fragmentTransaction);
                hideFragment(myFragment4, fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.account:
                selectedChange();
                tagAccount.setSelected(true);
                if (myFragment3.isAdded()) {
                    fragmentTransaction.show(myFragment3);
                } else {
                    fragmentTransaction.add(R.id.fragment_main, myFragment3);
                }
                hideFragment(myFragment1, fragmentTransaction);
                hideFragment(myFragment2, fragmentTransaction);
                hideFragment(myFragment4, fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.table:
                selectedChange();
                tagTable.setSelected(true);
                if (myFragment4.isAdded()) {
                    fragmentTransaction.show(myFragment4);
                } else
                    fragmentTransaction.add(R.id.fragment_main, myFragment4);
                hideFragment(myFragment1, fragmentTransaction);
                hideFragment(myFragment2, fragmentTransaction);
                hideFragment(myFragment3, fragmentTransaction);
                fragmentTransaction.commit();
                break;
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, ActivityAdd.class);
                startActivity(intent);
                break;

        }
    }

    public void buildFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(myFragment1==null)
        myFragment1 = new MyFragment1();
        if(myFragment2==null)
        myFragment2 = new MyFragment2();
        if(myFragment3==null)
        myFragment3 = new MyFragment3();
        if(myFragment4==null)
        myFragment4 = new MyFragment4();
        // fragmentTransaction.replace(R.id.fragment_main,myFragmet1);
        fragmentTransaction.add(R.id.fragment_main, myFragment1).commit();
        //fragmentTransaction.replace(layout_id, f);
        //fragmentTransaction.commit();
    }

    //隐藏fragment
    public void hideFragment(Fragment fragment, FragmentTransaction fragmentTransaction) {
        if (fragment != null && fragment.isAdded()) {
            fragmentTransaction.hide(fragment);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, ActivityAdd.class);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.search:
                Intent newIntent = new Intent(MainActivity.this, ActivitySearch.class);
                startActivity(newIntent);
                break;
            case R.id.item_data_with_month:
                //线性表的展示
                Intent showDataByMonth = new Intent(MainActivity.this, ActivityShowDataByMonth.class);
                startActivity(showDataByMonth);
                break;
            case R.id.item_data_with_account:
                //按账号类别进行统计，以条形图的形式展现
                Intent showDataByAccount = new Intent(MainActivity.this, ActivityShowDataByAccount.class);
                startActivity(showDataByAccount);
                break;
            case R.id.item_data_with_year:
                //以年为单位统计各月支出与收入
                Intent showDataByYear = new Intent(MainActivity.this, ActivityShowDataByYear.class);
                startActivity(showDataByYear);
                break;
            default:
        }
        return true;
    }
    //如果Activity被回收，但是fragment实例还在，就保存它们的状态信息，防止出现重影的状态
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(myFragment1!=null && myFragment1.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,"MyFragment1",myFragment1);
        }
        if(myFragment2!=null && myFragment2.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,"MyFragment2",myFragment2);
        }
        if(myFragment3!=null && myFragment3.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,"MyFragment3",myFragment3);
        }
        if(myFragment4!=null && myFragment4.isAdded())
        {
            getSupportFragmentManager().putFragment(outState,"MyFragment4",myFragment4);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //当用户切换账号，或者数据下载成功。需要更新数据库中的内容
    public void updateData() {
        SharedPreferences pref = getSharedPreferences("IsNeedUpddateData", MODE_PRIVATE);
        String fileName="";
        if (pref != null) {
            boolean isNeedUpdateData = pref.getBoolean("isNeedUpdate", false);
            //如果配置文件显示不用更新。
            if (isNeedUpdateData == false)
                return;
                //需要更新数据
            else {
                try {
                    fileName = pref.getString("fileName", "");
                    FileInputStream in = openFileInput(fileName);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    int flag = 1;
                    ArrayList<MyData> dataArrayList = new ArrayList<>();
                    ArrayList<Account> accountArrayList = new ArrayList<>();
                    ArrayList<AccountInformation> informationArrayList = new ArrayList<>();
                    while ((line = reader.readLine()) != null) {
                        if (line.contains("####")) {
                            flag++;
                            continue;
                        }
                    if (flag == 1) {
                        //"\"为转义字符，需要双反斜杠转义。。
                        String[] temp = line.split("\\|");
                        if (temp.length == 10) {
                            MyData myData = new MyData();
                            myData.setUser(temp[0]);
                            if (temp[1].equals("0"))
                                myData.setType(false);
                            else
                                myData.setType(true);
                            myData.setTypeSelect(temp[2]);
                            myData.setYear(Integer.parseInt(temp[3]));
                            myData.setMonth(Integer.parseInt(temp[4]));
                            myData.setDay(Integer.parseInt(temp[5]));
                            myData.setHourMinuteSecond(temp[6]);
                            myData.setMoney(temp[7]);
                            myData.setRemarks(temp[8]);
                            myData.setAccountName(temp[9]);
                            dataArrayList.add(myData);
                            // Log.d("TestSb",temp[0]+"--user-type--"+temp[2]+"----"+temp[6]+"---"+temp[7]+"----"+temp[8]+"---"+temp[9]);
                        }
                    }
                   else
                       if (flag == 2) {
                        String[] temp1 = line.split("\\|");
                        if (temp1.length ==4) {
                            Account account = new Account();
                            account.setUser(temp1[0]);
                            account.setAccountName(temp1[1]);
                            account.setType(temp1[2]);
                            account.setAccoutPicId(Integer.parseInt(temp1[3]));
                            accountArrayList.add(account);
                        }

                    }
                    else
                    if (flag == 3) {
                        String temp2[] = line.split("\\|");
                        if (temp2.length == 10) {
                            AccountInformation accountInformation = new AccountInformation();
                            accountInformation.setUser(temp2[0]);
                            accountInformation.setAccountName(temp2[1]);
                            accountInformation.setDateAdd(temp2[2]);
                            accountInformation.setDate(temp2[3]);
                            accountInformation.setRemarks(temp2[4]);
                            accountInformation.setCost(temp2[5]);
                            accountInformation.setSalary(temp2[6]);
                            accountInformation.setMoney(temp2[7]);
                            accountInformation.setNum(Integer.parseInt(temp2[8]));
                            if (temp2[9].equals("true"))
                                accountInformation.setCard(true);
                            else
                                accountInformation.setCard(false);
                            informationArrayList.add(accountInformation);

                        }
                    }
                }
                  //删除原有的数据
                    DataSupport.deleteAll(MyData.class);
                    DataSupport.deleteAll(Account.class);
                    DataSupport.deleteAll(AccountInformation.class);
                    int len=accountArrayList.size();
                    for(int i=0;i<len;i++)
                    {
                        AccountInformation accountInformation=informationArrayList.get(i);
                        Account account=accountArrayList.get(i);
                        boolean accountInforFlag=accountInformation.save();
                        account.setAccountInformation(accountInformation);
                        boolean accountFlag=account.save();
                        if(accountFlag && accountInforFlag)
                        {
                            Log.d("MainActivity","save account and accountinformation");
                        }
                        else
                        {
                            Log.d("MainActivity","save account and accountinformation fail");
                        }
                    }
                    for(MyData myData:dataArrayList)
                    {
                        String accountName=myData.getAccountName();
                        Account account=DataSupport.where("accountName=?",accountName).findFirst(Account.class);
                        if(account!=null)
                        {
                            myData.setAccount(account);
                            myData.setAccountId(account.getId());
                        }
                    }
                    DataSupport.saveAll(dataArrayList);
                    SharedPreferences.Editor editor=getSharedPreferences("IsNeedUpddateData",MODE_PRIVATE).edit();
                    editor.putString("fileName","");
                    editor.putBoolean("isNeedUpdate",false);
                    editor.apply();
                    File file=new File("/data/data/com.example.tagtest/"+fileName);
                    if(file.exists())
                        file.delete();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }
    }
}
