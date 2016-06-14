package com.tong.bookstore;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

/**
 * @author tong.zhang
 * @version 1.0.0
 * @date 2016-04-12 14:53
 * @since 5.3.0
 */

//This class is to achieve notification, it provides a way to the service to display a message
// even when another app is on the foreground. Notification will appear in the notification
// area on the phone screen. In order to see it user must pull down the notification drawer.
// If user clicks on notification it will start the app, which displayed the notification
public class BookStoreNotification {

    private BookStoreNotification() {
    }


    //Create a notification object, which contains all fields required to display notification
    public static void notify(Context context, String title, String content) {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context);

        notifyBuilder.setContentTitle(title);
        notifyBuilder.setTicker(title);
        notifyBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notifyBuilder.setAutoCancel(true);
        StringBuilder append = new StringBuilder().append(content).append(title);
        notifyBuilder.setContentText(append);


        //Get a reference for notification service and use it to display a notification
        Notification notification = notifyBuilder.build();
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int) SystemClock.currentThreadTimeMillis(), notification);
    }

    public static void cancelAll(Context context) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

}
