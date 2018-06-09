package com.example.tagtest.drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tagtest.MainActivity;
import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.tools.Answer;
import com.example.tagtest.tools.UseJson;
import com.example.tagtest.tools.UseOkHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText account;
    private EditText password;
    private Button login;
    private Button register;
    private String returnData="";
    private String user;
    private String passWd;
   // private static final String LoginAddress="http://10.0.2.2:8080/TagTest/LoginServlet";
    private static final String LoginAddress="http://192.168.43.235:8080/TagTest/LoginServlet";
    private ProgressDialog progressDialog=null;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what)
           {
               case 0:
                   returnData=(String)msg.obj;
                   Log.d("Handle","我被调用了"+returnData);

           }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);
        initialise();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void initialise() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        account = (EditText) findViewById(R.id.user_account);
        password = (EditText) findViewById(R.id.user_passwd);
        login = (Button) findViewById(R.id.user_login);
        register = (Button) findViewById(R.id.user_register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forget_passwd_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.forget_passwd:
                Intent findPassWd=new Intent(ActivityLogin.this,ActivityForgetPassWd.class);
                startActivity(findPassWd);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_login:
                 user = account.getText().toString();
                passWd = password.getText().toString();
                //判断账户，密码不为空
                if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(passWd)) {
                    //判断当前是否有网络连接
                    if (isNetworkAvalible(this)) {
                        progressDialog = new ProgressDialog(ActivityLogin.this);
                        progressDialog.setTitle("登录");
                        progressDialog.setMessage("正在努力的登录...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        UseOkHttp.httpRequestLogin(LoginAddress, user, passWd, new okhttp3.Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        Log.d("OkHttp", "wrong");
                                        loginCheck();
                                        //setReturnData("");
                                        //progressDialog.dismiss();
                                        //Toast.makeText(ActivityLogin.this,"一些问题发生了",Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        String data = response.body().string();
                                        Message message = new Message();
                                        message.what = 0;
                                        message.obj = data;
                                        handler.sendMessage(message);
                                        //只有等获得服务器相应数据后再去判断是否成功登录
                                        //要是不放在这里，主线程不会等子线程完成就返回了，造成返回数据为空
                                        loginCheck();
                                        //非主线程里面不能调用Toast
                                        //Toast.makeText(ActivityLogin.this, "return data is" + returnData, Toast.LENGTH_SHORT).show();

                                    }
                                }
                        );
                    } else {
                        Toast.makeText(ActivityLogin.this, "当前没有网络！！！", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(ActivityLogin.this, "账号/密码不能为空！！！", Toast.LENGTH_SHORT).show();

                break;
            case R.id.user_register:
                Intent registerIntent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(registerIntent);
                break;
        }
    }
    //判断网络是否连接
    public boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //根据服务器返回的数据判断是否登录成功
    public void loginCheck()
    {
      runOnUiThread(new Runnable() {
          @Override
          public void run() {
              if(TextUtils.isEmpty(returnData))
              {
               Log.d("Result is sb",returnData);
                  Toast.makeText(ActivityLogin.this,"一些问题发生了哦！！！",Toast.LENGTH_SHORT).show();
              }
              else {
                  Log.d("Result is sb",returnData);
                  //将json字符串解析
                  Answer answer = UseJson.getCanLogin(returnData);
                  if (answer == null) {
                      Toast.makeText(ActivityLogin.this, "一些问题发生了", Toast.LENGTH_SHORT).show();
                  } else {
                      if (answer.getResult()) {
                          //登录成功
                          Toast.makeText(ActivityLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                          Intent mainIntent=new Intent(ActivityLogin.this, MainActivity.class);
                          mainIntent.putExtra("UserName",user);
                          ActivityCollector.finishAll();
                          startActivity(mainIntent);
                      } else {
                          //progressDialog.dismiss();
                          Toast.makeText(ActivityLogin.this, answer.getText(), Toast.LENGTH_SHORT).show();
                      }
                  }
              }
              if(progressDialog!=null)
                  progressDialog.dismiss();
          }
      });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
