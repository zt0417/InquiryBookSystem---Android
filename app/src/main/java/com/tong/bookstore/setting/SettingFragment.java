package com.tong.bookstore.setting;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.tong.bookstore.BookStoreNotification;
import com.tong.bookstore.R;
import com.tong.bookstore.database.DataOperator;
import com.tong.bookstore.database.SQLiteHelper;
import com.tong.bookstore.netview.NetworkFragment;
import com.tong.bookstore.util.ContactHelper;
import com.tong.bookstore.util.ToastUtil;

import java.util.List;

/**
 * Created by tzhang1-cc on 2016/3/11.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

public class SettingFragment extends NetworkFragment {

    // this part for clearn databbase part.

    private View clearView;
    private View contactBtn;
    private TextView nameText;

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void findViewByIds(View layout) {
        super.findViewByIds(layout);
        clearView = layout.findViewById(R.id.clear_btn);
        clearView.setOnClickListener(this);

        contactBtn = layout.findViewById(R.id.get_contact_btn);
        contactBtn.setOnClickListener(this);

        nameText = (TextView) layout.findViewById(R.id.show_name);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.clear_btn == v.getId()) {
            clearDataBase();

        } else if (R.id.get_contact_btn == v.getId()) {
            getContacts();
        }
    }

    private void getContacts() {
        List<ContactHelper.FriendItem> friendItems = ContactHelper.getBriefContactInfo(getContext().getApplicationContext());
        if (friendItems != null && friendItems.size() > 0) {
            int size = friendItems.size();
            StringBuilder stringBuilder = new StringBuilder(size);
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    stringBuilder.append(friendItems.get(i).toString());
                } else {
                    stringBuilder.append(friendItems.get(i).toString()).append("--");
                }
            }
            nameText.setText(stringBuilder.toString());
        } else {
            ToastUtil.showToast(getContext().getApplicationContext(), "maybe have no contacts!");
        }

    }

    private static final int DELETE_DATABASE = 10;

    private void clearDataBase() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                boolean result = new DataOperator(SQLiteHelper.getDB(getContext().getApplicationContext())).delete();
                handler.obtainMessage(DELETE_DATABASE, result).sendToTarget();
            }
        }).start();
    }

    // delect database
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (DELETE_DATABASE == msg.what) {
                boolean result = (boolean) msg.obj;
                String str;
                if (result) {
                    str = "data base deleted success!";
                } else {
                    str = "data base deleted fail!";
                }
                Context context = getContext().getApplicationContext();
                ToastUtil.showToast(context, str);
                BookStoreNotification.cancelAll(context);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
