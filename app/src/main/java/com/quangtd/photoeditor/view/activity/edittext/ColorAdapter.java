package com.quangtd.photoeditor.view.activity.edittext;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.utils.ScreenUtils;


public class ColorAdapter extends RecyclerView.Adapter {
    public interface OnClickItemColorListener {
        void onClickItemColor(int position);
    }

    private Context mContext;
    private OnClickItemColorListener mOnClickItemColorListener;
    private String[] mColors;
    private int mSize;

    public void setOnClickItemColorListener(OnClickItemColorListener onClickItemColorListener) {
        this.mOnClickItemColorListener = onClickItemColorListener;
    }

    public ColorAdapter(Context context) {
        this.mContext = context;
        mColors = context.getResources().getStringArray(R.array.array_color);
        mSize = (ScreenUtils.getWidthScreen(context) - 8 * 15) / 7;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_color, parent, false);
        return new ColorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int color = Color.parseColor(mColors[position]);
        ((ColorViewHolder) holder).mImgColor.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

    private class ColorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mImgColor;

        ColorViewHolder(View itemView) {
            super(itemView);
            mImgColor = itemView.findViewById(R.id.imgColor);
            mImgColor.getLayoutParams().width = mSize;
            mImgColor.getLayoutParams().height = mSize * 2 / 3;
            mImgColor.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickItemColorListener != null) {
                int color = Color.parseColor(mColors[getLayoutPosition()]);
                mOnClickItemColorListener.onClickItemColor(color);
            }
        }
    }
}
