package com.quangtd.photoeditor.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;

/**
 * QuangTD on 10/5/2017.
 */


public abstract class AdapterBase<VH extends ViewHolderBase> extends RecyclerView.Adapter<VH> {
    protected final String TAG = this.getClass().getSimpleName();

    private final Context mContext;

    protected AdapterBase(Context context) {
        mContext = context;
    }

    protected Context getContext() {
        return mContext;
    }

    protected Resources getResources() {
        return mContext.getResources();
    }

    protected String getString(int resId) {
        return mContext.getString(resId);
    }

    protected String getString(int resId, Object... objects) {
        return mContext.getString(resId, objects);
    }

    public interface OnBaseItemClickListener<Item> {
        void onItemClick(Item item, int position);
    }
}
