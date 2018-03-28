package com.example.tagtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by MyFirstPC on 2018/3/25.
 */

public class MyFragmet1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState)
    {
        View view=inflater.inflate(R.layout.first_fragment,container,false);
        return view;
    }
}
