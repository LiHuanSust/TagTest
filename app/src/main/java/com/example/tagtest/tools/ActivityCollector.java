package com.example.tagtest.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyFirstPC on 2018/5/25.
 * activity管理类，方便随时退出app
 */

public class ActivityCollector {
    public static List<Activity> activities=new ArrayList<>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static void finishAll()
    {
        for(Activity activity:activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}
