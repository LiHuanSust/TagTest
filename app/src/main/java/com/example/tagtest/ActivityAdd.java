package com.example.tagtest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ActivityAdd extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView costAdd;
    private TextView salaryAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        costAdd=findViewById(R.id.cost_add);
        salaryAdd=findViewById(R.id.salary_add);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置返回键
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {  //事件
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentCost f=new FragmentCost();
        fragmentTransaction.replace(R.id.layout_bottom,f);
        fragmentTransaction.commit();

        setFragment();
    }
    public void selected()
    {
        costAdd.setSelected(false);
        salaryAdd.setSelected(false);
    }
    public void setFragment()
    {
        costAdd=findViewById(R.id.cost_add);
        salaryAdd=findViewById(R.id.salary_add);


        costAdd.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
               selected();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                FragmentCost f=new FragmentCost();
                fragmentTransaction.replace(R.id.layout_bottom,f);
                fragmentTransaction.commit();
                costAdd.setSelected(true);
            }
        });
        salaryAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selected();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                FragmentSalary f=new FragmentSalary();
                fragmentTransaction.replace(R.id.layout_bottom,f);
                fragmentTransaction.commit();
                salaryAdd.setSelected(true);
            }

        });

    }
}
