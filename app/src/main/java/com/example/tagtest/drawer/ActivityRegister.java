package com.example.tagtest.drawer;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.tools.Answer;
import com.example.tagtest.tools.UseJson;
import com.example.tagtest.tools.UseOkHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class ActivityRegister extends AppCompatActivity{
    private Toolbar toolbar;
    private TextView toolBarText;
    private EditText accountName;
    private EditText accountPassWd;
    private EditText accountPassWdRepeat;
    private Spinner questionSelect;
    private EditText questionAnswer;
    private Button registerButton;
    private RadioGroup radioGroup;
    private String question="";
    private String sex="男";
    private String account="";
    private String password="";
    private String passWordRepeat="";
    private String answer="";
    private String returnData="";
    //private final static String RegisterURL="http://10.0.2.2:8080/TagTest/RegisterServlet";
    private final static String RegisterURL="http://192.168.43.235:8080/TagTest/RegisterServlet";
    private ProgressDialog progressDialog;
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    returnData=(String)msg.obj;
                    Log.d("Handle","我被调用了"+returnData);

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollector.addActivity(this);
        initialise();
        setListener();
    }
    private void initialise()
    {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolBarText=(TextView)findViewById(R.id.toolbar_text_view);
        accountName=(EditText)findViewById(R.id.register_account_name);
        accountPassWd=(EditText)findViewById(R.id.register_account_passwd);
        accountPassWdRepeat=(EditText)findViewById(R.id.register_account_passwd_repeat);
        questionSelect=(Spinner)findViewById(R.id.question_select);
        questionAnswer=(EditText)findViewById(R.id.account_question_answer);
        registerButton=(Button)findViewById(R.id.button_register);
        radioGroup=(RadioGroup)findViewById(R.id.radio_group);
        toolBarText.setText("用户注册页");
    }
    private void setListener()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        questionSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 question=(String)questionSelect.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.checkbox_sex_man:
                        sex="男";
                        break;
                    case R.id.checkbox_sex_woman:
                        sex="女";
                        break;
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                account=accountName.getText().toString();
                password=accountPassWd.getText().toString();
                passWordRepeat=accountPassWdRepeat.getText().toString();
                answer=questionAnswer.getText().toString();
                //判断注册内容是否填写完整
                if(TextUtils.isEmpty(account) || TextUtils.isEmpty(password) ||TextUtils.isEmpty(passWordRepeat)
                || TextUtils.isEmpty(answer) || TextUtils.isEmpty(sex))
                {
                    Toast.makeText(ActivityRegister.this,"请将数据填写完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    //判断密码长度是否过短
                    if(password.length()<7)
                    {
                        Toast.makeText(ActivityRegister.this,"密码长度不能小于7位！！！",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //判断两次输入密码是否相同
                        if(!password.equals(passWordRepeat))
                        {
                            Toast.makeText(ActivityRegister.this,"两次密码不同！！！",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //判断账户和密码是否过长
                            if(account.length()>15 || password.length()>15 )
                            {
                                Toast.makeText(ActivityRegister.this,"帐户名或密码长度不能大于15位",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //判断网络情况
                                if(isNetworkAvalible(ActivityRegister.this))
                                {
                                    //条件满足
                                    //开始注册
                                    Toast.makeText(ActivityRegister.this,"准备注册",Toast.LENGTH_SHORT).show();
                                    progressDialog = new ProgressDialog(ActivityRegister.this);
                                    progressDialog.setTitle("注册");
                                    progressDialog.setMessage("正在努力的注册...");
                                    progressDialog.setCancelable(false);
                                    progressDialog.show();
                                    User user=new User();
                                    user.setUserName(account);
                                    user.setUserPassWd(password);
                                    user.setSex(sex);
                                    user.setQuestion(question);
                                    user.setAnswer(answer);
                                    Log.d("Sex=",sex);
                                    UseOkHttp.httpRequestRegister(RegisterURL,user, new okhttp3.Callback()
                                    {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                                registerCheck();
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            String data = response.body().string();
                                            Message message = new Message();
                                            message.what = 1;
                                            message.obj = data;
                                            handler.sendMessage(message);
                                            registerCheck();
                                        }
                                    });

                                }
                                //没有获得网络情况
                                else
                                {
                                    Toast.makeText(ActivityRegister.this,"当前没有网络！！！",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    }

                }

            }
        });
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
    public void registerCheck() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(returnData)) {
                    Log.d("Result is sb", returnData);
                    Toast.makeText(ActivityRegister.this, "一些问题发生了哦！！！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Result is sb", returnData);
                    //将json字符串解析
                    Answer answer = UseJson.getCanRegister(returnData);
                    if (answer == null) {
                        Toast.makeText(ActivityRegister.this, "一些问题发生了", Toast.LENGTH_SHORT).show();
                    } else {
                        if (answer.getResult()) {
                            //登录成功
                            Toast.makeText(ActivityRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                           // Intent loginIntent = new Intent(ActivityLogin.this, MainActivity.class);
                            //mainIntent.putExtra("UserName", user);
                            //ActivityCollector.finishAll();
                            //startActivity(mainIntent);
                        } else {
                            //出问题了直接获得返回码信息
                            //progressDialog.dismiss();
                            Toast.makeText(ActivityRegister.this, answer.getText(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (progressDialog != null)
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
