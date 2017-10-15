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

class CustomToolAdapter extends RecyclerView.Adapter<CustomToolAdapter.ToolHolder> {
    private final Context mContext;
    private String[] mTools;
    private int[] mIdIcons;
    @Accessors(prefix = "m")
    @Setter
    private CustomToolBar.OnClickToolListener mListener;

    CustomToolAdapter(Context context, String[] features, int[] ids) {
        this.mContext = context;
        this.mTools = features;
        this.mIdIcons = ids;
    }

    @Override public ToolHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ToolHolder(LayoutInflater.from(mContext).inflate(R.layout.item_bottom, parent, false));
    }

    @Override public void onBindViewHolder(ToolHolder holder, int position) {
        holder.bindData(CustomToolBar.TYPE.values()[position], mTools[position], mIdIcons[position]);
    }

    @Override public int getItemCount() {
        return mTools.length;
    }

    class ToolHolder extends RecyclerView.ViewHolder {
        ImageView mImgIcon;
        TextView mTvTitle;
        CustomToolBar.TYPE mType;

        ToolHolder(View itemView) {
            super(itemView);
            mImgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        void bindData(CustomToolBar.TYPE type, String title, int id) {
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
