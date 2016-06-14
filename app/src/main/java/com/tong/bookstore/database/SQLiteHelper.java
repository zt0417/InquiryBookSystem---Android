package com.tong.bookstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tzhang1-cc on 2016/3/12.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "book_store";
    public static final String TABLE_NAME = "store";
    private static int dataVersion = 1;
    private static volatile SQLiteDatabase db = null;

    private SQLiteHelper(Context context, String name) {
        super(context, name, null, dataVersion);
    }

    private static SQLiteHelper getDatabaseHelper(Context context, String name) {
        return new SQLiteHelper(context, name);
    }

    public static synchronized SQLiteDatabase getDB(Context context) {
        if (db == null) {
            db = getDatabaseHelper(context, DATABASE_NAME)
                    .getWritableDatabase();
        }
        return db;
    }
    // create table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                + "id integer primary key autoincrement,"
                + "imageId integer(20),"
                + "desc text"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
