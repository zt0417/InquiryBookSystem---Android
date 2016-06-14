package com.tong.bookstore.netview;

/**
 * Created by tzhang1-cc on 2016/3/11.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

//Interfaces, when network loading, it can displayed load, fail or empty.

public interface NetWorkInterface {
    void showLoading();
    void showContent();
    void showFail();
    void showEmpty();
}
