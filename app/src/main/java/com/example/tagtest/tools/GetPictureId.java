package com.example.tagtest.tools;

import com.example.tagtest.R;

/**
 * Created by MyFirstPC on 2018/5/2.
 * 根据类型，获取图片资源id
 */

public class GetPictureId {
    public static int getId(final int id,final boolean isBank)
    {
        if(isBank==true) {
            switch (id) {
                case 0:
                    //招商
                    return R.drawable.zhao_shang;
                case 1:
                    //工商
                    return R.drawable.gong_shang;
                case 2:
                    //农业
                    return R.drawable.nong_ye;
                case 3:
                    //储蓄
                    return R.drawable.chu_xu;
                case 4:
                    //中国银行
                    return R.drawable.china_yin_hang;
                case 5:
                    //建设
                    return R.drawable.jian_she;
                case 6:
                    //交通
                    return R.drawable.jiao_tong;
                case 7:
                    //中信
                    return R.drawable.zhong_xing;
                case 8:
                    //光大
                    return R.drawable.guang_da;
                case 9:
                    //广发
                    return R.drawable.guang_fa;
                case 10:
                    //兴业
                    return R.drawable.xing_ye;
                case 11:
                    //民生
                    return R.drawable.ming_sheng;
                case 12:
                    //浦发
                    return R.drawable.pu_fa;
                case 13:
                    return R.drawable.bank_other;
            }
        }
        else
        {
            //普通账户
            switch (id)
            {
                case 14:
                    //默认账户
                    return R.drawable.bank_other;
                case 15:
                    //现金
                    return R.drawable.cash_account;
                case 16:
                    return R.drawable.alipay_account;
                case 17:
                    return R.drawable.wechat_account;
                case 18:
                    return R.drawable.other_account;
            }
        }
        return R.drawable.other_account;
    }
}
