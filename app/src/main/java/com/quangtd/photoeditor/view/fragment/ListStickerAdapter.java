package com.quangtd.photoeditor.view.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.utils.ScreenUtils;

import java.util.List;

/**
 * QuangTD on 5/5/2017.
 */

public class ListStickerAdapter extends RecyclerView.Adapter {
    public interface OnClickStickerListener {
        void onClickSticker(int position);
    }

    private List<StorageReference> mStorageReferences;
    private Context mContext;
    private int mImgSize;
    private int mImgSizeContainer;
    private OnClickStickerListener mOnClickListener;


    public ListStickerAdapter(Context context, List<StorageReference> mStorageReferences, OnClickStickerListener onClickListener) {
        this.mContext = context;
        this.mStorageReferences = mStorageReferences;
        this.mOnClickListener = onClickListener;
        mImgSizeContainer = ScreenUtils.getWidthScreen(mContext) / 3;
        mImgSize = ScreenUtils.getWidthScreen(mContext) / 3 * 90 / 100;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sticker, parent, false);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(mContext).using(new FirebaseImageLoader()).load(mStorageReferences.get(position)).into(((IViewHolder) holder).imgSticker);
        if (position % 2 == 1) {
            ((IViewHolder) holder).llContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_gray));
        } else {
            ((IViewHolder) holder).llContainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_white));
        }
    }

    @Override
    public int getItemCount() {
        return mStorageReferences != null ? mStorageReferences.size() : 0;
    }

    private class IViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llContainer;
        private ImageView imgSticker;

        IViewHolder(View itemView) {
            super(itemView);
            imgSticker = (ImageView) itemView.findViewById(R.id.imgSticker);
            llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);

            llContainer.getLayoutParams().width = mImgSizeContainer;
            llContainer.getLayoutParams().height = mImgSizeContainer;
            imgSticker.getLayoutParams().width = mImgSize;
            imgSticker.getLayoutParams().height = mImgSize;

            imgSticker.setOnClickListener(v -> {
                if (mOnClickListener != null) {
                    mOnClickListener.onClickSticker(getLayoutPosition());
                }
            });
        }
    }
}
