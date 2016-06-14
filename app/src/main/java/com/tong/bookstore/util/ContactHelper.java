package com.tong.bookstore.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tong.zhang
 * @version 1.0.0
 * @date 2016-04-12 15:30
 * @since 5.3.0
 */
public class ContactHelper {
    private ContactHelper() {
    }

    public static class FriendItem {
        String name;
        String id;
        int phoneCount;

        @Override
        public String toString() {
            return name;
        }
    }

    public static List<FriendItem> getBriefContactInfo(Context context) {

        String ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        String contactId;
        String displayName;

        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, null);

        if (cursor == null) {
            return null;
        }

        List<FriendItem> friendList = new ArrayList<>();
        // if have no friend return
        if (!cursor.moveToFirst()) {//moveToFirst
            return null;
        }
        do {
            contactId = cursor.getString(cursor.getColumnIndex(ID));
            displayName = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
            int phoneCount = cursor.getInt(cursor.getColumnIndex(HAS_PHONE_NUMBER));

            FriendItem item;
            item = new FriendItem();
            item.id = contactId;
            item.name = displayName;
            item.phoneCount = phoneCount;

            friendList.add(item);
        } while (cursor.moveToNext());

        cursor.close();
        return friendList;
    }
}
