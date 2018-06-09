package com.example.tagtest.drawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tagtest.R;
import com.example.tagtest.tools.ActivityCollector;

public class ActivityUserInfor extends AppCompatActivity {
    private TextView userName;
    private ImageView imageViewReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_user_infor);
        userName=(TextView) findViewById(R.id.infor_user);
        imageViewReturn=(ImageView)findViewById(R.id.infor_return);
        imageViewReturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ActivityCollector.removeActivity(ActivityUserInfor.this);
                finish();

            }
        });
        userName.setText(User.getNowUserName());
    }
}
