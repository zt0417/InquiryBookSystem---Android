package com.tong.bookstore.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tong.bookstore.bookstore.MyRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzhang1-cc on 2016/3/12.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

public class DataOperator {
    // build database, and add the function for insert, update, delect book in this database.
    private static final String TAG = "DataOperator";

    private SQLiteDatabase db = null;

    public DataOperator(SQLiteDatabase db) {
        this.db = db;
    }

    // insert book to database.
    public void insert(int imageId, String desc) {
        String sql = "INSERT INTO " + SQLiteHelper.TABLE_NAME + " (imageId,desc)" + " VALUES(?,?)";
        Object args[] = new Object[]{imageId, desc};
        this.db.execSQL(sql, args);
    }

    //update database.
    public void update(int imageId, String desc) {
        String sql = "UPDATE " + SQLiteHelper.TABLE_NAME + " SET imageId=? WHERE desc=?";
        Object args[] = new Object[]{imageId, desc};
        this.db.execSQL(sql, args);
    }

    // delect book from database
    public void delete(int imageId) {
        String sql = "DELETE FROM " + SQLiteHelper.TABLE_NAME + " WHERE imageId=?";
        Object args[] = new Object[]{imageId};
        this.db.execSQL(sql, args);
    }

    //This is for a function return all book.
    public List<MyRecycleViewAdapter.BookBean> find() {
        List<MyRecycleViewAdapter.BookBean> all = new ArrayList<MyRecycleViewAdapter.BookBean>();
        String sql = "SELECT * FROM " + SQLiteHelper.TABLE_NAME;
        Cursor result = this.db.rawQuery(sql, null);
        try {
            MyRecycleViewAdapter.BookBean bookBean = null;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
                bookBean = new MyRecycleViewAdapter.BookBean();
                bookBean.imageId = result.getInt(result.getColumnIndex("imageId"));
                bookBean.desc = result.getString(result.getColumnIndex("desc"));
                all.add(bookBean);
            }
        } catch (Exception e) {
            if (result != null)
                result.close();
            Log.e(TAG, "data base exception");
        }

        return all;
    }

    //delect all database.
    public boolean delete() {
        return db.delete(SQLiteHelper.TABLE_NAME, null, null) > 0;
    }
}
