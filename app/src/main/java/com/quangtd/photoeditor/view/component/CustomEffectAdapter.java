package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private int mCurrent = -1;
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
        LinearLayout mBackground;

        EffectHolder(View itemView) {
            super(itemView);
            mImgEffect = itemView.findViewById(R.id.imgEffect);
            mBackground = itemView.findViewById(R.id.background);
        }

        void bindData(int resId) {
            Glide.with(mContext).load(resId).into(mImgEffect);
            if (getAdapterPosition() == mCurrent) {
                mBackground.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            } else {
                mBackground.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
            }
            mImgEffect.setOnClickListener(v -> {
                if (mListener != null) mListener.onClickItemEffect(getAdapterPosition());
                notifyItemChanged(mCurrent);
                mCurrent = getAdapterPosition();
                notifyItemChanged(mCurrent);
            });
        }
    }
}
