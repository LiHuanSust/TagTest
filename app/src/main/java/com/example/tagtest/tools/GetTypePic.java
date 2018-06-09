package com.example.tagtest.tools;

import com.example.tagtest.R;

/**
 * Created by MyFirstPC on 2018/6/8.
 * 根据此类获得每种类型的图片
 */

public class GetTypePic {
    private static final int PIC_ID[]={R.drawable.type_eat,R.drawable.type_traffic,R.drawable.type_shopping,
     R.drawable.type_play,R.drawable.type_medicine,R.drawable.type_educate,R.drawable.type_other};
    public static GetTypeValue getTypeInfor(int position,boolean isCost)
    {
       //如果是消费类型
       if(isCost) {
           if (0 == position)
               //餐饮
               return new GetTypeValue(PIC_ID[0],"餐饮");
           if (1 == position)
               //交通
               return new GetTypeValue(PIC_ID[1],"交通");
           if (2 == position)
               //购物
               return new GetTypeValue(PIC_ID[2],"购物");
           if (3 == position)
               //娱乐
               return new GetTypeValue(PIC_ID[3],"娱乐");
           if (4 == position)
               //医药
               return new GetTypeValue(PIC_ID[4],"医药");
           if (5 == position)
               //教育
               return new GetTypeValue(PIC_ID[5],"教育");
           //其他
       }
        return new GetTypeValue(PIC_ID[6],"其他");
    }

}
