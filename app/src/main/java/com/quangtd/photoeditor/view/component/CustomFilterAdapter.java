package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.response.Filter;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 10/16/2017.
 */

public class CustomFilterAdapter extends RecyclerView.Adapter<CustomFilterAdapter.FilterHolder> {
    private final Context mContext;
    private List<Filter> mFilters;
    @Accessors(prefix = "m")
    @Setter
    private CustomFilterBar.OnClickFilterListener mListener;
    private String mPath;
    private Bitmap mBitmapOrigin;

    public CustomFilterAdapter(Context context, List<Filter> filters, String path) {
        this.mContext = context;
        this.mFilters = filters;
        this.mPath = path;
        mBitmapOrigin = BitmapFactory.decodeFile(mPath);
    }

    @Override public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_filter, parent, false));
    }

    @Override public void onBindViewHolder(FilterHolder holder, int position) {
        holder.bindData(mFilters.get(position));
    }

    @Override public int getItemCount() {
        return (mFilters == null) ? 0 : mFilters.size();
    }

    class FilterHolder extends RecyclerView.ViewHolder {
        TextView mTvFilter;
        ImageView mImgFilter;

        FilterHolder(View itemView) {
            super(itemView);
            mImgFilter = itemView.findViewById(R.id.imgFilter);
            mTvFilter = itemView.findViewById(R.id.tvFilter);
        }

        void bindData(Filter filter) {
            mImgFilter.setImageBitmap(mBitmapOrigin);
            mImgFilter.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onClickItemFilter(getAdapterPosition());
                }
            });
            mTvFilter.setText(filter.name());
        }
    }
}
