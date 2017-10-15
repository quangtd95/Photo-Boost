package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quangtd.photoeditor.R;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 10/15/2017.
 */

class CustomFeatureAdapter extends RecyclerView.Adapter<CustomFeatureAdapter.FeatureHolder> {
    private final Context mContext;
    private String[] mFeatures;
    private int[] mIdIcons;
    @Accessors(prefix = "m")
    @Setter
    private CustomFeatureBar.OnClickFeatureListener mListener;

    CustomFeatureAdapter(Context context, String[] features, int[] ids) {
        this.mContext = context;
        this.mFeatures = features;
        this.mIdIcons = ids;
    }

    @Override public FeatureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FeatureHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bottom, parent, false));
    }

    @Override public void onBindViewHolder(FeatureHolder holder, int position) {
        holder.bindData(CustomFeatureBar.TYPE.values()[position], mFeatures[position], mIdIcons[position]);
    }

    @Override public int getItemCount() {
        return mFeatures.length;
    }

    class FeatureHolder extends RecyclerView.ViewHolder {
        ImageView mImgIcon;
        TextView mTvTitle;
        CustomFeatureBar.TYPE mType;

        FeatureHolder(View itemView) {
            super(itemView);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        void bindData(CustomFeatureBar.TYPE type, String title, int id) {
            this.mType = type;
            mImgIcon.setImageDrawable(mContext.getResources().getDrawable(id));
            mTvTitle.setText(title);
            mImgIcon.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.clickItem(mType);
                }
            });
        }
    }
}
