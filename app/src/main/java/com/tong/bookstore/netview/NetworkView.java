package com.tong.bookstore.netview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.tong.bookstore.R;

/**
 * Created by tzhang1-cc on 2016/3/11.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

public class NetworkView extends FrameLayout {

    private View loadView;
    private ViewStub failStub;
    private View failView;
    private View rootView;

    private OnClickListener onClickListener;

    public NetworkView(Context context) {
        this(context, null);
    }

    public NetworkView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NetworkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rootView = LayoutInflater.from(context).inflate(R.layout.include_net_view, null);
        loadView = rootView.findViewById(R.id.net_loading);
        addView(rootView);
    }

    public void loading() {
        loadView.setVisibility(VISIBLE);
        if (failView != null)
            failView.setVisibility(GONE);
    }

    public void fail() {
        if (failStub == null) {
            failStub = (ViewStub) findViewById(R.id.net_fail_stub);
            failView = failStub.inflate();
            View netClick = failView.findViewById(R.id.net_click);
            if (onClickListener != null) {
                netClick.setOnClickListener(onClickListener);
            }
        }
        failView.setVisibility(VISIBLE);
        loadView.setVisibility(GONE);
    }

    public void setOnNetFailClickLister(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
