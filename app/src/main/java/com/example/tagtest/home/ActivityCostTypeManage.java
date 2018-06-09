package com.example.tagtest.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;
import com.example.tagtest.tools.GetTypePic;
import com.example.tagtest.tools.GetTypeValue;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ActivityCostTypeManage extends AppCompatActivity implements View.OnClickListener{
    private ListView listViewValue; //显示当前大类下的具体内容
    private List<TypeValueBasic> eatValueList;
    private List<TypeValueBasic> trafficList;
    private List<TypeValueBasic> shoppingList;
    private List<TypeValueBasic> playList;
    private List<TypeValueBasic> medicineList;
    private List<TypeValueBasic> educationList;
    private List<TypeValueBasic> otherList;
    private AdapterTypeValue adapterTypeValue;
    private List<TypeValueBasic> valueList;//显示的小类内容
    private TextView textViewEat;
    private TextView textViewTraffic;
    private TextView textViewShopping;
    private TextView textViewPlay;
    private TextView textViewMedicine;
    private TextView textViewEducation;
    private TextView textViewOther;
    private ImageView imgViewReturn;
    private ImageView imgViewAdd;
    private int typeNum=0;//记录大类的变量，0，餐饮；1，交通。。。
    private int saveNum=0; //收藏的数量，收藏之后在主页会显示
    private ArrayList<TextView> textViewList=new ArrayList<>();
    //要在主页上显示的内容
    private ArrayList<TypeValueBasic> saveList=new ArrayList<>();
    private CallbackListener myListener=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_type_manage);
        ActivityCollector.addActivity(this);
        initialise();
        textViewEat.setSelected(true);
        typeNum=0;
        valueList=new ArrayList<>();
        valueList.addAll(eatValueList);
        adapterTypeValue=new AdapterTypeValue();
        listViewValue.setAdapter(adapterTypeValue);
        myListener=new CallbackListener() {
            @Override
            public void callBackItem(int positon, boolean flag) {
                Log.d("Manage:",":"+positon);
                View view=listViewValue.getChildAt(positon);
                if(view!=null)
                {
                    ImageView imageView=view.findViewById(R.id.list_view_type_pic);
                    imageView.setSelected(flag);
                    valueList.get(positon).isSeclect=flag;
                    Log.d("TestTestTest",valueList.get(positon).isSeclect+"");
                }

            }
        };
       adapterTypeValue.setmListener(myListener);
        listViewValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //adapterTypeValue.notifyDa();
            }
        });
    }
    public void initialise()
    {
        listViewValue=findViewById(R.id.list_view_cost_value);
        textViewEat=(TextView)findViewById(R.id.cost_type_eat);
        textViewTraffic=(TextView)findViewById(R.id.cost_type_traffic);
        textViewShopping=(TextView)findViewById(R.id.cost_type_shopping);
        textViewPlay=(TextView)findViewById(R.id.cost_type_play);
        textViewMedicine=(TextView)findViewById(R.id.cost_type_medicine);
        textViewEducation=(TextView)findViewById(R.id.cost_type_education);
        textViewOther=(TextView)findViewById(R.id.cost_type_other);
        textViewList.add(textViewEat);
        textViewList.add(textViewTraffic);
        textViewList.add(textViewShopping);
        textViewList.add(textViewPlay);
        textViewList.add(textViewMedicine);
        textViewList.add(textViewEducation);
        textViewList.add(textViewOther);
        eatValueList=new ArrayList<>();
        trafficList=new ArrayList<>();
        shoppingList=new ArrayList<>();
        playList=new ArrayList<>();
        medicineList=new ArrayList<>();
        educationList=new ArrayList<>();
        otherList=new ArrayList<>();
        imgViewReturn=findViewById(R.id.cost_type_return);
        imgViewAdd=findViewById(R.id.cost_type_add);
        for(int i=0;i<7;i++)
        {
            List<TypeInfor> list= DataSupport.where("typeid=?",i+"").find(TypeInfor.class);
            int len=list.size();
            for(int j=0;j<len;j++) {
                TypeInfor temp=list.get(j);
                boolean isSave=temp.isSave();
                if(isSave)
                    saveNum++;
                switch (i) {
                    case 0:
                        eatValueList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;
                    case 1:
                        trafficList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;
                    case 2:
                        shoppingList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;
                    case 3:
                        playList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;
                    case 4:
                        medicineList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;
                    case 5:
                        educationList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;
                    default:
                        otherList.add(new TypeValueBasic(temp.getId(),i,temp.getName(),isSave));
                        break;

                }
            }
        }
        imgViewReturn.setOnClickListener(this);
        imgViewAdd.setOnClickListener(this);
        for(int i=0;i<textViewList.size();i++)
        {
           textViewList.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.cost_type_eat:
                setNotSelect();
                textViewEat.setSelected(true);
                valueList.clear();
                valueList.addAll(eatValueList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=0;
                break;
            case R.id.cost_type_traffic:
                setNotSelect();
                textViewTraffic.setSelected(true);
                valueList.clear();
                valueList.addAll(trafficList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=1;
                break;
            case R.id.cost_type_shopping:
                setNotSelect();
                textViewShopping.setSelected(true);
                valueList.clear();
                valueList.addAll(shoppingList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=2;
                break;
            case R.id.cost_type_play:
                setNotSelect();
                textViewPlay.setSelected(true);
                valueList.clear();
                valueList.addAll(playList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=3;
                break;
            case R.id.cost_type_medicine:
                setNotSelect();
                textViewMedicine.setSelected(true);
                valueList.clear();
                valueList.addAll(medicineList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=4;
                break;
            case R.id.cost_type_education:
                setNotSelect();
                textViewEducation.setSelected(true);
                valueList.clear();
                valueList.addAll(educationList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=5;
                break;
            case R.id.cost_type_other:
                setNotSelect();
                textViewOther.setSelected(true);
                valueList.clear();
                valueList.addAll(otherList);
                adapterTypeValue=new AdapterTypeValue();
                listViewValue.setAdapter(adapterTypeValue);
                adapterTypeValue.setmListener(myListener);
                typeNum=6;
                break;
            case R.id.cost_type_return:
                finish();
                break;
            case R.id.cost_type_add:
                GetTypeValue getTypeValue= GetTypePic.getTypeInfor(typeNum,true);
                final EditText et = new EditText(this);
                new AlertDialog.Builder(this).setTitle("您正在"+getTypeValue.getName()+"大类下，请输入需要保存的类型：")
                        .setView(et)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //按下确定键后的事件
                               String newTYpe=et.getText().toString();
                               if(!TextUtils.isEmpty(newTYpe))
                               {
                                   TypeInfor typeInfor=DataSupport.where("name=?",newTYpe).findFirst(TypeInfor.class);
                                   if(typeInfor==null)
                                   {
                                       typeInfor=new TypeInfor();
                                       typeInfor.setTypeId(typeNum);
                                       typeInfor.setSave(false);
                                       typeInfor.setName(newTYpe);
                                       typeInfor.setCost(true);
                                       if(typeInfor.save())
                                       {
                                           TypeValueBasic typeValueBasic;
                                           if(0==typeNum) {
                                               typeValueBasic = new TypeValueBasic(typeInfor.getId(), 0, newTYpe, false);
                                               eatValueList.add(typeValueBasic);

                                           }
                                           else
                                               if(1==typeNum) {
                                                   typeValueBasic = new TypeValueBasic(typeInfor.getId(), 1, newTYpe, false);
                                                   trafficList.add(typeValueBasic);

                                           }
                                           else
                                               if(2==typeNum) {
                                                   typeValueBasic = new TypeValueBasic(typeInfor.getId(), 2, newTYpe, false);
                                                   shoppingList.add(typeValueBasic);
                                           }
                                           else
                                               if(3==typeNum) {
                                                   typeValueBasic = new TypeValueBasic(typeInfor.getId(), 3, newTYpe, false);
                                                   playList.add(typeValueBasic);
                                               }
                                           else
                                               if(4==typeNum) {
                                                   typeValueBasic = new TypeValueBasic(typeInfor.getId(), 4, newTYpe, false);
                                                   medicineList.add(typeValueBasic);
                                           }
                                           else
                                               if(5==typeNum) {
                                                   typeValueBasic = new TypeValueBasic(typeInfor.getId(), 5, newTYpe, false);
                                                   educationList.add(typeValueBasic);
                                           }
                                           else {
                                                   typeValueBasic = new TypeValueBasic(typeInfor.getId(), 6, newTYpe, false);
                                                   otherList.add(typeValueBasic);
                                               }
                                           Toast.makeText(ActivityCostTypeManage.this,"类别添加成功！！！",Toast.LENGTH_SHORT).show();
                                           valueList.add(typeValueBasic);
                                           adapterTypeValue.notifyDataSetChanged();

                                       }
                                       else
                                       {
                                           Toast.makeText(ActivityCostTypeManage.this,"类别保存出现错误！！！",Toast.LENGTH_SHORT).show();
                                       }
                                   }
                                   else
                                   {
                                       Toast.makeText(ActivityCostTypeManage.this,"类别名称不能重复！！！",Toast.LENGTH_SHORT).show();
                                   }
                               }
                               else
                               {
                                   Toast.makeText(ActivityCostTypeManage.this,"类别名称不能为空！！！",Toast.LENGTH_SHORT).show();
                               }
                            }
                        }).setNegativeButton("取消",null).show();
                break;
            default:
                break;
        }
    }
    public void setNotSelect()
    {
        int len=textViewList.size();
        for(int i=0;i<len;i++)
        {
            textViewList.get(i).setSelected(false);
        }
    }
    class AdapterTypeValue extends BaseAdapter {
        private ViewHold viewHold;
        private TypeValueBasic typeValueBasic;
        private CallbackListener mListener;
        @Override
        public int getCount() {
            return valueList.size();
        }

        @Override
        public Object getItem(int position) {
            return valueList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            TypeValueBasic typeValueBasic = valueList.get(position);
                if (convertView == null) {
                    viewHold = new ViewHold();
                    convertView = View.inflate(ActivityCostTypeManage.this, R.layout.list_view_type_value_basic, null);
                    viewHold.value = convertView.findViewById(R.id.list_view_type_infor);
                    viewHold.valuePic = convertView.findViewById(R.id.list_view_type_pic);
                    viewHold.isSelect = valueList.get(position).isSeclect;
                    convertView.setTag(viewHold);
                } else {
                    viewHold = (ViewHold) convertView.getTag();
                }
                viewHold.value.setText(valueList.get(position).valueName);
                viewHold.valuePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TypeValueBasic typeValueBasic = valueList.get(position);
                        Log.d("ActivityCostTypeManage", "类别：" + typeValueBasic.typeId + ",名称" + typeValueBasic.valueName);
                        if (saveList.contains(typeValueBasic)) {
                            Log.d("ActivityCostTypeManage", "已经被选过");
                            saveList.remove(typeValueBasic);
                            --saveNum;
                            mListener.callBackItem(position, false);
                        } else {
                            saveList.add(typeValueBasic);
                            Log.d("ActivityCostTypeManage", "新选对象");
                            if(saveNum<12)
                            {
                                ++saveNum;
                                mListener.callBackItem(position, true);
                            }
                            else
                            {
                                Toast.makeText(ActivityCostTypeManage.this,"首页只能显示12个哦，请取消其他选择后，再试。",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                if (viewHold.isSelect)
                    viewHold.valuePic.setSelected(true);
                else
                    viewHold.valuePic.setSelected(false);

                return convertView;
        }
        public void setmListener(CallbackListener listener)
        {
            this.mListener=listener;
        }
        public CallbackListener getmListener()
        {
            return mListener;
        }
        class ViewHold
        {
            TextView value;
            ImageView valuePic;
            boolean isSelect;
        }
    }
    //value点击事件
    public interface CallbackListener
    {
        public void callBackItem(int positon,boolean state);
    }
    //typeValue存放的内容，主要包括大类id，及该类的名称
    class TypeValueBasic
    {
        long id;//数据库中的id
        int typeId;  //所属的大类id
        String valueName; //小类名字
        boolean isSeclect; //是否被选中
        public TypeValueBasic(final long id,final int typeId,final String valueName,boolean isSeclect)
        {
            this.id=id;
            this.typeId=typeId;
            this.valueName=valueName;
            this.isSeclect=isSeclect;
        }

    }
   //活动销毁前一定会调用的方法
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        super.onDestroy();
        Log.d("TestIsTest","我进来了。。。");
        if(saveNum!=0)
        {
            for(int i=0;i<eatValueList.size();i++)
            {
                TypeValueBasic typeValueBasic=eatValueList.get(i);
                if(eatValueList.get(i).isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
            for(int i=0;i<trafficList.size();i++)
            {
                TypeValueBasic typeValueBasic=trafficList.get(i);
                if(typeValueBasic.isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
            for(int i=0;i<shoppingList.size();i++)
            {
                TypeValueBasic typeValueBasic=shoppingList.get(i);
                if(typeValueBasic.isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
            for(int i=0;i<playList.size();i++)
            {
                TypeValueBasic typeValueBasic=playList.get(i);
                if(typeValueBasic.isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
            for(int i=0;i<medicineList.size();i++)
            {
                TypeValueBasic typeValueBasic=medicineList.get(i);
                if(typeValueBasic.isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
            for(int i=0;i<educationList.size();i++)
            {
                TypeValueBasic typeValueBasic=educationList.get(i);
                if(typeValueBasic.isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
            for(int i=0;i<otherList.size();i++)
            {
                TypeValueBasic typeValueBasic=otherList.get(i);
                if(typeValueBasic.isSeclect)
                {
                    TypeInfor typeInfor=DataSupport.find(TypeInfor.class,typeValueBasic.id);
                    typeInfor.setSave(true);
                    typeInfor.save();
                }
            }
        }
    }
}
