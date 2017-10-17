package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.quangtd.photoeditor.R;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 10/16/2017.
 */

public class CustomFilterAdapter extends RecyclerView.Adapter<CustomFilterAdapter.FilterHolder> {
    private final Context mContext;
    private int[] mResIds;
    @Accessors(prefix = "m")
    @Setter
    private CustomFilterBar.OnClickFilterListener mListener;

    public CustomFilterAdapter(Context context, int[] mResIds) {
        this.mContext = context;
        this.mResIds = mResIds;
    }

    @Override public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_filter, parent, false));
    }

    @Override public void onBindViewHolder(FilterHolder holder, int position) {
        holder.bindData(mResIds[position]);
    }

    @Override public int getItemCount() {
        return (mResIds == null) ? 0 : mResIds.length;
    }

    class FilterHolder extends RecyclerView.ViewHolder {

        ImageView mImgFilter;

        FilterHolder(View itemView) {
            super(itemView);
            mImgFilter = (ImageView) itemView.findViewById(R.id.imgFilter);
        }

        void bindData(int resId) {
            Glide.with(mContext).load(resId).into(mImgFilter);
            mImgFilter.setOnClickListener(v -> {
                if (mListener != null) mListener.clickItem(getAdapterPosition());
            });
        }
    }
}
