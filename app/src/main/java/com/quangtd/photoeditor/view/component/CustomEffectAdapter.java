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

public class CustomEffectAdapter extends RecyclerView.Adapter<CustomEffectAdapter.EffectHolder> {
    private final Context mContext;
    private int[] mResIds;
    @Accessors(prefix = "m")
    @Setter
    private CustomEffectBar.OnClickEffectListener mListener;

    public CustomEffectAdapter(Context context, int[] mResIds) {
        this.mContext = context;
        this.mResIds = mResIds;
    }

    @Override public EffectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EffectHolder(LayoutInflater.from(mContext).inflate(R.layout.item_effect, parent, false));
    }

    @Override public void onBindViewHolder(EffectHolder holder, int position) {
        holder.bindData(mResIds[position]);
    }

    @Override public int getItemCount() {
        return (mResIds == null) ? 0 : mResIds.length;
    }

    class EffectHolder extends RecyclerView.ViewHolder {

        ImageView mImgEffect;

        EffectHolder(View itemView) {
            super(itemView);
            mImgEffect = (ImageView) itemView.findViewById(R.id.imgEffect);
        }

        void bindData(int resId) {
            Glide.with(mContext).load(resId).into(mImgEffect);
            mImgEffect.setOnClickListener(v -> {
                if (mListener != null) mListener.onClickItemEffect(getAdapterPosition());
            });
        }
    }
}
