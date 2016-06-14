package com.tong.bookstore;

import android.app.Application;
import android.content.Intent;

import com.tong.bookstore.service.BookService;

/**
 * Created by tzhang1-cc on 2016/3/11.
 */

/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/
//this is a fragment for book store.

public class BookStoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initAll();
    }

    private void initAll() {
        initLog();
        initNetwork();
        initService();
    }


    private void initService() {
        startService(new Intent(getApplicationContext(), BookService.class));
    }

    private void initLog() {
    }

    private void initNetwork() {
    }
}
