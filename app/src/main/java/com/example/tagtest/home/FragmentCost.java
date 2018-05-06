package com.example.tagtest.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.tagtest.MyData;
import com.example.tagtest.R;
import com.example.tagtest.account.Account;
import com.example.tagtest.account.AccountInformation;
import com.example.tagtest.tools.GetDate;
import com.example.tagtest.tools.MyCalculate;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class FragmentCost extends Fragment implements View.OnClickListener{
    private TextView costType=null;
    private TextView dateNow=null;
    private EditText costMoney=null;
    private EditText costRemarks=null;
    protected LinearLayout bank_card_layout;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_10;
    private Button button_11;
    private Button button_12;
    private Button saveButton; //保存按钮
    private Button clearButton;//清空按钮
    protected Spinner spinner_cost_type; //消费类型下拉框
    protected Spinner spinner_bank_type; //银行下拉框
    private ArrayList<Button> list_button=null;
    private boolean flag=false;  //判断收入还是支出
    private GetDate myDate;
    private AdapterAccountSelect adapterAccountSelect;
    private List<Account> mList;  //spinner的list数据
    private Account mAccount;//相应的账户
    private UseXunFei useXunFei; //调用讯飞
    private ImageView speech;
    private static final int REQUEST_CODE=1;   //权限请求的返回值
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle bundle)
    {
        View view=inflater.inflate(R.layout.frament_cost_add,contain,false);
        return  view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        requestPermissions();
         useXunFei=new UseXunFei(getActivity());
        useXunFei.inilite();
        initialise();
        costTypeSelect();
    }
  /*  public void getPermission()
    {
        if(Build.VERSION.SDK_INT>=23)
        {
            int checkAudioPermission= ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
            int checkPhoneStatePermission=ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE);
            if(checkAudioPermission!=PackageManager.PERMISSION_GRANTED || checkPhoneStatePermission!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE},REQUEST_CODE);
                return;
            }
            else
            {
                //已经获得权限
                // initSpeech() ;
              //  useXunFei.initSpeech();
            }
        }
        else
        {
            //android版本在6.0以下
            //initSpeech() ;
            //useXunFei.initSpeech(); ;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_CODE:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(getActivity(),"获取权限成功",Toast.LENGTH_SHORT).show();
                    // initSpeech() ;
                    //useXunFei.initSpeech(); ;
                }
                else
                {
                    Toast.makeText(getActivity(),"获取权限失败",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }*/
  private void requestPermissions(){
      try {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
              int permission = ActivityCompat.checkSelfPermission(getActivity(),
                      Manifest.permission.WRITE_EXTERNAL_STORAGE);
              if(permission!= PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions(getActivity(),new String[]
                          {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                  Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                                  Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                                  Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
              }

              if(permission != PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions(getActivity(),new String[] {
                          Manifest.permission.ACCESS_COARSE_LOCATION,
                          Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
              }
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void initialise()  //把所有按钮加入到ArrayList中
    {
        //useXunFei=new UseXunFei(getActivity());
        myDate=new GetDate();
        dateNow=getActivity().findViewById(R.id.time_now);
        dateNow.setOnClickListener(this);
        list_button=new ArrayList<>();
        button_1=getActivity().findViewById(R.id.button1);
        button_2=getActivity().findViewById(R.id.button2);
        button_3= getActivity().findViewById(R.id.button3);
        button_4=getActivity().findViewById(R.id.button4);
        button_5=getActivity().findViewById(R.id.button5);
        button_6=getActivity().findViewById(R.id.button6);
        button_7=getActivity().findViewById(R.id.button7);
        button_8=getActivity().findViewById(R.id.button8);
        button_9=getActivity().findViewById(R.id.button9);
        button_10=getActivity().findViewById(R.id.button10);
        button_11=getActivity().findViewById(R.id.button11);
        button_12=getActivity().findViewById(R.id.button12);
        saveButton=getActivity().findViewById(R.id.cost_add_save);
        clearButton=getActivity().findViewById(R.id.cost_add_cancel);
        costType=getActivity().findViewById(R.id.cost_type); //消费类型显示,TextView
        costRemarks=getActivity().findViewById(R.id.cost_remarks); //备注，EditText
        costMoney=getActivity().findViewById(R.id.cost_money); //消费金额，TextView
        spinner_cost_type=(Spinner) getActivity().findViewById(R.id.spinner_cost_type);//消费时账户类型选择，Spinner
        bank_card_layout=(LinearLayout)getActivity().findViewById(R.id.bank_card_select);//银行卡选择，Spinner
       // spinner_bank_type=(Spinner)getActivity().findViewById(R.id.spinner_bank_select);
        mList= DataSupport.findAll(Account.class);
        adapterAccountSelect=new AdapterAccountSelect(getActivity(),mList);
        spinner_cost_type.setAdapter(adapterAccountSelect);
        speech=(ImageView)getActivity().findViewById(R.id.speech);
        speech.setOnClickListener(this);
        list_button.add(button_1);
        list_button.add(button_2);
        list_button.add(button_3);
        list_button.add(button_4);
        list_button.add(button_5);
        list_button.add(button_6);
        list_button.add(button_7);
        list_button.add(button_8);
        list_button.add(button_9);
        list_button.add(button_10);
        list_button.add(button_11);
        list_button.add(button_12);
        list_button.add(saveButton);
        list_button.add(clearButton);
        for(Button button:list_button)
        {
            button.setOnClickListener(this);
        }
        button_1.setSelected(true);
        costType.setText(button_1.getText().toString());
        dateNow.setText(myDate.allToString());
    }
    public void costTypeSelect()
    {
        spinner_cost_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                bank_card_layout.setVisibility(View.GONE);
                mAccount=mList.get(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void bankCardSelected()   //银行卡选择
    {
        bank_card_layout=(LinearLayout)getActivity().findViewById(R.id.bank_card_select);
        bank_card_layout.setVisibility(View.VISIBLE);
        spinner_bank_type=(Spinner)getActivity().findViewById(R.id.spinner_bank_select);
        spinner_bank_type.setSelection(0);
       // bank_select_value=spinner_bank_type.getItemAtPosition(0).toString();
       // spinner_bank_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         /*   @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank_select_value=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

    }
    public void clearValue() //清空所有内容
    {
        costType.setText(button_1.getText().toString());
        setNotSelected();
        button_1.setSelected(true);
        costMoney.setText("");
        costRemarks.setText("");
        spinner_cost_type.setSelection(0);
//        bank_card_layout.setVisibility(View.GONE);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_now:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        //date显示的是所有的数据情况，所以需要格式化输出
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String format = simpleDateFormat.format(date);
                        dateNow.setText(format);

                    }
                }).setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                        .build();
                pvTime.show();
                break;
            case R.id.button1:
                setNotSelected();
                costType.setText(button_1.getText().toString());
                button_1.setSelected(true);
                break;
            case R.id.button2:
                setNotSelected();
                costType.setText(button_2.getText().toString());
                button_2.setSelected(true);
                break;
            case R.id.button3:
                setNotSelected();
                costType.setText(button_3.getText().toString());
                button_3.setSelected(true);
                break;
            case R.id.button4:
                setNotSelected();
                costType.setText(button_4.getText().toString());
                button_4.setSelected(true);
                break;
            case R.id.button5:
                setNotSelected();
                costType.setText(button_5.getText().toString());
                button_5.setSelected(true);
                break;
            case R.id.button6:
                setNotSelected();
                costType.setText(button_6.getText().toString());
                button_6.setSelected(true);
                break;
            case R.id.button7:
                setNotSelected();
                costType.setText(button_7.getText().toString());
                button_7.setSelected(true);
                break;
            case R.id.button8:
                setNotSelected();
                costType.setText(button_8.getText().toString());
                button_8.setSelected(true);
                break;
            case R.id.button9:
                setNotSelected();
                costType.setText(button_9.getText().toString());
                button_9.setSelected(true);
                break;
            case R.id.button10:
                setNotSelected();
                costType.setText(button_10.getText().toString());
                button_10.setSelected(true);
                break;
            case R.id.button11:
                setNotSelected();
                costType.setText(button_11.getText().toString());
                button_11.setSelected(true);
                break;
            case R.id.button12:
                setNotSelected();
                costType.setText(button_12.getText().toString());
                button_12.setSelected(true);
                break;
            case R.id.speech:
                useXunFei.show();
                break;
            case R.id.cost_add_cancel:
                clearValue();
                break;
            case R.id.cost_add_save:
                String date = dateNow.getText().toString();
                String money = costMoney.getText().toString();
                String type = costType.getText().toString();
                String remarks = costRemarks.getText().toString();
                if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(type)) {
                    String[] dateInformation = date.split(" "); //把年月日与时分秒分开
                    String[] yearMonthDay = dateInformation[0].split("-");//将年月日分开
                    int year = Integer.parseInt(yearMonthDay[0]);
                    int month = Integer.parseInt(yearMonthDay[1]);
                    int day = Integer.parseInt(yearMonthDay[2]);
                    //float Money = Float.parseFloat(money);
                    MyData myData = new MyData();
                    myData.setUser("user");
                    myData.setType(true);
                    myData.setTypeSelect(type);
                    myData.setYear(year);
                    myData.setMonth(month);
                    myData.setDay(day);
                    myData.setHourMinuteSecond(dateInformation[1]);
                    myData.setAccount(mAccount);
                    myData.setAccountId(mAccount.getId());
                    myData.setRemarks(remarks);
                    Log.d("Hello",type);
                    myData.setMoney(money);
                    if (myData.save()) {
                        mAccount.getList().add(myData);
                        AccountInformation accountInformation = DataSupport.find(AccountInformation.class, mAccount.getId());
                        Log.d("Right", accountInformation.toString());
                        String accountCost = MyCalculate.add(accountInformation.getCost(),money);
                        String accountMoney = MyCalculate.sub(accountInformation.getMoney(),money);
                        int accountNum = accountInformation.getNum() + 1;
                        accountInformation.setCost(accountCost);
                        accountInformation.setMoney(accountMoney);
                        accountInformation.setNum(accountNum);
                        accountInformation.setDateAdd(date);
                        if (accountInformation.save()) {
                            Log.d("Right", accountInformation.getId()+"");
                            Toast.makeText(getActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                            clearValue();
                        }
                        else
                            Toast.makeText(getActivity(), "关联表更新失败！", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"数据保存失败",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    Toast.makeText(getActivity(),"请将数据填写完整",Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
        }
    }
    public void setNotSelected() //使按钮不被选择
    {
        if(list_button!=null) {
            for (Button button : list_button) {
                button.setSelected(false);
            }
        }
    }

    public class UseXunFei
    {
        private Context context;
        // private static String TAG = ge.class.getSimpleName();
        // 语音听写对象
        private SpeechRecognizer mIat;
        // 语音听写UI
        private RecognizerDialog mIatDialog;
        private InitListener mInitListener = new InitListener() {

            @Override
            public void onInit(int code) {
                Log.d("Test", "SpeechRecognizer init() code = " + code);
                if (code != ErrorCode.SUCCESS) {
                    showTip("初始化失败，错误码：" + code);
                }
            }
        };

        /**
         * 上传联系人/词表监听器。
         */

        /**
         * 听写监听器。
         */
        private RecognizerListener mRecognizerListener = new RecognizerListener() {

            @Override
            public void onBeginOfSpeech() {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
                showTip("开始说话");
            }

            @Override
            public void onError(SpeechError error) {
                // Tips：
                // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
                if(mTranslateEnable && error.getErrorCode() == 14002) {
                    showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
                } else {
                    showTip(error.getPlainDescription(true));
                }
            }

            @Override
            public void onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                showTip("结束说话");
            }

            @Override
            public void onResult(RecognizerResult results, boolean isLast) {
                Log.d("Test", results.getResultString());
                if( mTranslateEnable ){
                    //printTransResult( results );
                }else{
                    printResult(results);
                }

                if (isLast) {
                    // TODO 最后的结果
                }
            }

            @Override
            public void onVolumeChanged(int volume, byte[] data) {
                showTip("当前正在说话，音量大小：" + volume);
                Log.d("Test", "返回音频数据："+data.length);
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                // 若使用本地能力，会话id为null
                //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                //		Log.d(TAG, "session id =" + sid);
                //	}
            }
        };
        // 用HashMap存储听写结果
        private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
        private Toast mToast;
        // 引擎类型
        private String mEngineType = SpeechConstant.TYPE_CLOUD;

        private boolean mTranslateEnable = false;
        public UseXunFei(final Context context)
        {
            this.context=context;
        }
        public void inilite()
        {
            SpeechUtility.createUtility(context,"appid="+"5ade9ef5");
            mIat = SpeechRecognizer.createRecognizer(context, mInitListener);

            // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
            // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
            mIatDialog = new RecognizerDialog(context,mInitListener);
        }
        public void show()
        {
            setParam();
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            showTip("begin");
        }

        private void printResult(RecognizerResult results) {
            String text = com.example.tagtest.tools.JsonParser.parseIatResult(results.getResultString());

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(results.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }

            costRemarks.setText("    "+resultBuffer.toString());
            costRemarks.setSelection(resultBuffer.length());
        }

        /**
         * 听写UI监听器
         */
        private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
            public void onResult(RecognizerResult results, boolean isLast) {
                if( mTranslateEnable ){
                    //printTransResult( results );
                }else{
                    printResult(results);
                }

            }

            /**
             * 识别回调错误.
             */
            public void onError(SpeechError error) {
                if(mTranslateEnable && error.getErrorCode() == 14002) {
                    showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
                } else {
                    showTip(error.getPlainDescription(true));
                }
            }

        };

        /**
         * 获取联系人监听器。
         */


        private void showTip(final String str) {
            //  mToast.setText(str);
            //mToast.show();
        }

        /**
         * 参数设置
         *
         * @return
         */
        public void setParam() {
            // 清空参数
            if(mIat!=null) {
                mIat.setParameter(SpeechConstant.PARAMS, null);

                // 设置听写引擎
                mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
                // 设置返回结果格式
                mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
                mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            }
            else
            {
                Toast.makeText(context,"mIat is null!!!",Toast.LENGTH_SHORT).show();
            }
            // this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
        /*if( mTranslateEnable ){
            Log.i( TAG, "translate enable" );
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }*/

//        String lag = mSharedPreferences.getString("iat_language_preference",
            //  "mandarin");
            //      if (lag.equals("en_us")) {
            // 设置语言
            //   mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            //  mIat.setParameter(SpeechConstant.ACCENT, null);

            //if( mTranslateEnable ){
            //mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
            //mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            //}
            //} else {
            // 设置语言
            // 设置语言区域
            //  mIat.setParameter(SpeechConstant.ACCENT, lag);

            //if( mTranslateEnable ){
            //    mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
            //  mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            //}
            //此处用于设置dialog中不显示错误码信息
            //mIat.setParameter("view_tips_plain","false");

            // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
//        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

            // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
            //      mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

            // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
            //    mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

            // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
            // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
            //mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
            //mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
        }
        public void destory()
        {
            if( null != mIat ){
                // 退出时释放连接
                mIat.cancel();
                mIat.destroy();
            }
        }

    }
    @Override
    //fragment 与activity解除联系的时候调用
    public void onDetach() {
        super.onDetach();
        useXunFei.destory();
    }
}


 /*if(myData.save())
                    {
                        //Account里面添加相应的data数据
                        mAccount.getList().add(myData);
                        AccountInformation accountInformation=mAccount.getAccountInformation();
                        float accountCost=accountInformation.getCost()+Money;
                        float accountMoney=accountInformation.getMoney()-accountCost;
                        int num=accountInformation.getNum()+1;
                        Log.d("Test",accountCost+"||"+accountMoney);
                        accountInformation.setCost(accountCost);
                        accountInformation.setMoney(accountMoney);
                        accountInformation.setNum(num);
                        accountInformation.setDateAdd(date);
                        if(accountInformation.save() && mAccount.save())
                        {
                            Toast.makeText(getActivity(),"保存成功！",Toast.LENGTH_SHORT).show();
                            clearValue();
                        }
                         else
                            Toast.makeText(getActivity(),"关联表更新失败！",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(),"数据保存失败",Toast.LENGTH_SHORT).show();

                    break;
                }
                else
                {
                    Toast.makeText(getActivity(),"请将数据填写完整",Toast.LENGTH_SHORT).show();
                    break;
                }*/