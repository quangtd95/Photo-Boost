package com.quangtd.photoeditor.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * QuangTD on 10/5/2017.
 */


public abstract class ViewHolderBase<Item> extends RecyclerView.ViewHolder {
    protected final String TAG = this.getClass().getSimpleName();

    private Item mItem;
    private Context mContext;

    protected Context getContext() {
        return mContext;
    }

    public Item getItem() {
        return mItem;
    }

    public ViewHolderBase(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }

    public void bindData(Item item) {
        mItem = item;
    }

}
