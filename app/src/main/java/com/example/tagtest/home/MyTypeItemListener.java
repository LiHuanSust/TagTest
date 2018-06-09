package com.example.tagtest.home;

import android.view.View;

/**
 * Created by MyFirstPC on 2018/6/9.
 */

public class MyTypeItemListener implements View.OnClickListener{

       private static MyTypeItemListener myListener=null;
        private MyTypeItemListener()
        {}
        public static MyTypeItemListener getMyListener()
        {
            if(myListener==null)
                myListener=new MyTypeItemListener();
            return myListener;
        }

    @Override
    public void onClick(View v) {

    }
}
