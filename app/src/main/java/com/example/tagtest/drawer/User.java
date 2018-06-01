package com.example.tagtest.drawer;
import org.litepal.crud.DataSupport;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MyFirstPC on 2018/5/23.
 * 用户类
 */

public class User extends DataSupport {
    private String userName;//用户名
    private String userPassWord; //用户密码
    private String sex; //性别
    private String question; //遗忘密码后用来找回所用的问题
    private String answer;
    //存放当前的user信息的SharedPreference的文件名
    private static  String USER_URL="userData";
    //用户名
    private static String USER_NAME="temp";
    //当前用户状态
    private static boolean IS_TEMP=true;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWd() {
        return userPassWord;
    }

    public void setUserPassWd(String userPassWd) {
        this.userPassWord = userPassWd;
    }
    //密码存储采用md5加密
    public static String getMd5Value(String temp)
    {
        StringBuffer sb = new StringBuffer();
        // 得到一个信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(temp.getBytes());
            // 把每一个byte做一个与运算 0xff
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                //位数不够
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //判断登录者是否为游客
    public static void setUserIsTemp(final boolean flag)
    {
        IS_TEMP=flag;
    }
    public static void setNowUserName(final String userName)
    {
        USER_NAME=userName;
    }
    public static String getNowUserName()
    {
        return USER_NAME;
    }
    public static boolean getUserIsTemp()
    {
        return IS_TEMP;
    }

}
