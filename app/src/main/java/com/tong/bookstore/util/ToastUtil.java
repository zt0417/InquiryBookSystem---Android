package com.tong.bookstore.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tong.zhang on 2016/3/12.
 */
public class ToastUtil {

    private static Toast toast = null;

    private ToastUtil() {
    }

    public static void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }
}
