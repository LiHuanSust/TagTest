package com.example.tagtest.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tagtest.R;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class FragmentSalary extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle bundle)
    {
        View view=inflater.inflate(R.layout.frament_salary_add,contain,false);
        return  view;
    }
}
