package com.tong.bookstore.netview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.tong.bookstore.BaseFragment;
import com.tong.bookstore.R;
import com.tong.bookstore.util.NetworkUtil;

/**
 * Created by tzhang1-cc on 2016/3/11.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

public abstract class NetworkFragment extends BaseFragment implements NetWorkInterface, View.OnClickListener {

    private View mContentView;
    private ViewStub contentStub;
    private NetworkView netView;
    private View contentView;

    @Nullable
    //@Nullable It means that the definition of the field can be empty.
    @Override
    // For this onCreateView, make the View that the network request become independent and
    // Subclass only worry about the content they display.

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mContentView != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
            return mContentView;
        }

        View layout = getLayoutView(inflater, container);
        mContentView = layout;
        findViewByIds(layout);
        initViews();

        return layout;
    }

    //initialization
    protected void initViews() {

    }

    //find view by id
    protected void findViewByIds(View layout) {
        contentStub = (ViewStub) layout.findViewById(R.id.stub_content);
        netView = (NetworkView) layout.findViewById(R.id.net_view);

        int rid = getContentLayout();
        if (rid <= 0) {
            throw new RuntimeException("network base fragment has none valid sub content layout");
        }
        contentStub.setLayoutResource(rid);
        contentView = contentStub.inflate();
        netView.setOnNetFailClickLister(this);

    }

    protected abstract int getContentLayout();

    private View getLayoutView(LayoutInflater inflater, ViewGroup container) {
        if (getLayoutResId() != 0) {
            return inflater.inflate(this.getLayoutResId(), container, false);
        }
        return null;
    }

    public int getLayoutResId() {
        return R.layout.fragment_network;
    }

    @Override
    public void showLoading() {
        contentView.setVisibility(View.GONE);
        netView.loading();
    }

    @Override
    public void showContent() {
        contentView.setVisibility(View.VISIBLE);
        netView.setVisibility(View.GONE);
    }

    @Override
    public void showFail() {
        contentView.setVisibility(View.GONE);
        netView.fail();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.net_click == id && canRetry()) {
            doRetry();
        }
    }

    private boolean canRetry() {
        if (!NetworkUtil.isNetworkAvailable(getContext().getApplicationContext())) {
            showFail();
            return false;
        }
        showLoading();
        return true;
    }

    private void doRetry() {

    }
}
