package com.iceford.heartguard.page;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

// 活动管理类,用于管理应用程序中的活动实例
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    // 将传入的活动实例添加到activities列表中
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    // 从activities列表中移除传入的活动实例
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    // 遍历activities列表中的活动实例
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                // 并调用每个活动实例的finish()方法来销毁活动
                activity.finish();
            }
        }
        // 在销毁活动之后，清空activities列表
        activities.clear();
    }
}

