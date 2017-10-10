package com.quangtd.photoeditor.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.response.LocalImage;
import com.quangtd.photoeditor.utils.ScreenUtils;

import java.io.File;
import java.util.List;


/**
 * QuangTD on 4/29/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter {
    public interface OnClickItemPhotoListener {
        void onClickItemPhoto(int position);
    }

    private final int mSizeImage;
    private Context mContext;
    private List<LocalImage> mLocalImages;
    private OnClickItemPhotoListener mOnClickItemHomeListener;

    public void setOnClickItemPhotoListener(OnClickItemPhotoListener onClickItemHomeListener) {
        this.mOnClickItemHomeListener = onClickItemHomeListener;
    }

    public PhotoAdapter(Context context, List<LocalImage> localImages) {
        this.mContext = context;
        this.mLocalImages = localImages;
        mSizeImage = (ScreenUtils.getWidthScreen(mContext) - 2 * ScreenUtils.convertDpToPixel(mContext, 5)) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String link = mLocalImages.get(position).getPath();
        if (link != null) {
            Glide
                    .with(mContext)
                    .load(new File(link))
                    .centerCrop()
                    .override(mSizeImage, mSizeImage)
                    .into(((ItemViewHolder)holder).mImgPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mLocalImages != null ? mLocalImages.size() : 0;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImgPhoto;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImgPhoto = itemView.findViewById(R.id.imgPhoto);

            mImgPhoto.getLayoutParams().height = mSizeImage;
            mImgPhoto.getLayoutParams().width = mSizeImage;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickItemHomeListener != null) {
                mOnClickItemHomeListener.onClickItemPhoto(getLayoutPosition());
            }
        }
    }
}
