package com.example.tagtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MyFirstPC on 2018/3/27.
 */

public class FragmentCost extends Fragment implements View.OnClickListener{
    private TextView cost_type;
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
    private ArrayList<Button> list_button=null;

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
        initialise();



    }
    public void initialise()  //把所有按钮加入到ArrayList中
    {
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
        cost_type=getActivity().findViewById(R.id.cost_type);
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
        for(Button button:list_button)
        {
            button.setOnClickListener(this);
        }


    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button1:
                setNotSelected();
                cost_type.setText(button_1.getText().toString());
                button_1.setSelected(true);
                break;
            case R.id.button2:
                setNotSelected();
                cost_type.setText(button_2.getText().toString());
                button_2.setSelected(true);
                break;
            case R.id.button3:
                setNotSelected();
                cost_type.setText(button_3.getText().toString());
                button_3.setSelected(true);
                break;
            case R.id.button4:
                setNotSelected();
                cost_type.setText(button_4.getText().toString());
                button_4.setSelected(true);
                break;
            case R.id.button5:
                setNotSelected();
                cost_type.setText(button_5.getText().toString());
                button_5.setSelected(true);
                break;
            case R.id.button6:
                setNotSelected();
                cost_type.setText(button_6.getText().toString());
                button_6.setSelected(true);
                break;
            case R.id.button7:
                setNotSelected();
                cost_type.setText(button_7.getText().toString());
                button_7.setSelected(true);
                break;
            case R.id.button8:
                setNotSelected();
                cost_type.setText(button_8.getText().toString());
                button_8.setSelected(true);
                break;
            case R.id.button9:
                setNotSelected();
                cost_type.setText(button_9.getText().toString());
                button_9.setSelected(true);
                break;
            case R.id.button10:
                setNotSelected();
                cost_type.setText(button_10.getText().toString());
                button_10.setSelected(true);
                break;
            case R.id.button11:
                setNotSelected();
                cost_type.setText(button_11.getText().toString());
                button_11.setSelected(true);
                break;
            case R.id.button12:
                setNotSelected();
                cost_type.setText(button_12.getText().toString());
                button_12.setSelected(true);
                break;
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
}