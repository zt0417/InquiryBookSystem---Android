package com.tong.bookstore.bookstore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tong.bookstore.R;
import com.tong.bookstore.database.DataOperator;
import com.tong.bookstore.database.SQLiteHelper;
import com.tong.bookstore.service.BookService;
import com.tong.bookstore.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tzhang1-cc, on 2016/3/11.
 */
/*project name: book store
author: tong zhang, brodie parterson
description:The App for Book Store again. It can achieve go to amazon website, book store, my book and setting parts.
*/

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {

    //Initial variable
    private List<BookBean> mDataList = null;
    private Context context = null;

    //This funcrtion is used to up the book name to context
    public MyRecycleViewAdapter(Context context) {
        mDataList = new ArrayList<BookBean>();
        this.context = context;
    }

    //This function is used to set data to the list.
    public void setData(List<BookBean> list) {
        if (list != null && list.size() != 0) {
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    //This is a child view extent from parent view
    //Made a ViewHolder return.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //It can be used to define a xml layout of the controls to find out.
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_item, null));
    }

    //Bound View to Item, and in this function process data to Item
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookBean bookBean = mDataList.get(position);
        holder.binder(bookBean);
    }

    // return  display the number to Item.
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView desc;
        private View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            desc = (TextView) itemView.findViewById(R.id.text_view);
            this.itemView = itemView;
        }

        //this is a binder it can set image and description
        public void binder(final BookBean bookBean) {
            imageView.setImageResource(bookBean.imageId);
            desc.setText(bookBean.desc);
            itemView.setOnClickListener(new View.OnClickListener() {

                // this event can make a toast when click the image button.
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(v.getContext(), bookBean.desc);
                    new DataOperator(SQLiteHelper.getDB(v.getContext().getApplicationContext())).insert(bookBean.imageId, bookBean.desc);
                    BookService.startBookService(v.getContext().getApplicationContext(), bookBean.desc);
                }
            });

        }
    }

    //This is a class for book, it include imageId and description.
    public static class BookBean {
        public int imageId;
        public String desc;
    }
}
