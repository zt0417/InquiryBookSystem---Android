package com.tong.bookstore.main;

import android.app.Activity;

/**
 * @author tong.zhang
 * @version 1.0.0
 * @date 2016-04-12 16:19
 * @since 5.3.0
 */

//ActivityManager function is to interact with all Activity running in the system and provides the interface,
//The main interface around the running process information, task information, service information.
public class ActivityManager {

    private static Activity topActivity;

    public static Activity getTopActivity() {
        return topActivity;
    }

    public static void setTopActivity(Activity topActivity) {
        ActivityManager.topActivity = topActivity;
    }


    private static class ActivityHolder {
        public final static ActivityManager INSTANCE = new ActivityManager();
    }

    public ActivityManager getInstance() {
        return ActivityHolder.INSTANCE;
    }
}
