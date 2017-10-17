package com.quangtd.photoeditor.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.response.CategorySticker;
import com.quangtd.photoeditor.utils.ScreenUtils;

import java.util.List;

/**
 * QuangTD on 10/17/2017.
 */


public class CategoryStickerAdapter extends AdapterBase<CategoryStickerAdapter.CategoryStickerHolder> {
    private List<CategorySticker> mCategoryStickers;
    private int mImgSize;
    private int mFolderSize;
    private int mCurrentPosition;

    public CategoryStickerAdapter(Context context, List<CategorySticker> categoryStickers) {
        super(context);
        this.mCategoryStickers = categoryStickers;
        mFolderSize = ScreenUtils.getWidthScreen(context) / 4;
        mImgSize = ScreenUtils.convertDpToPixel(context, 75) * 2 / 4;
    }

    @Override public CategoryStickerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryStickerHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_sticker_tab, parent, false));
    }

    @Override public void onBindViewHolder(CategoryStickerHolder holder, int position) {
        holder.bindData(mCategoryStickers.get(position));
    }

    @Override public int getItemCount() {
        return mCategoryStickers == null ? 0 : mCategoryStickers.size();
    }

    class CategoryStickerHolder extends ViewHolderBase<CategorySticker> {
        ImageView mImgCategoryThumbnail;
        TextView mTvTitle;
        private View line;

        public CategoryStickerHolder(View itemView) {
            super(itemView);
            mImgCategoryThumbnail = (ImageView) itemView.findViewById(R.id.imgThumbnail);
            mTvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            line = itemView.findViewById(R.id.viewLine);
            mImgCategoryThumbnail.getLayoutParams().width = mImgSize;
            mImgCategoryThumbnail.getLayoutParams().height = mImgSize;
            itemView.getLayoutParams().width = mFolderSize;
            itemView.setOnClickListener(v -> {
                if (mCurrentPosition != getLayoutPosition()) {
                    mCategoryStickers.get(mCurrentPosition).setSelected(false);
                    mCategoryStickers.get(getLayoutPosition()).setSelected(true);
                    notifyDataSetChanged();
                    mCurrentPosition = getLayoutPosition();
                }
            });
        }

        @Override public void bindData(CategorySticker categorySticker) {
            super.bindData(categorySticker);
            Glide.with(getContext()).load(getItem().getThumbnail()).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImgCategoryThumbnail);
            mTvTitle.setText(getItem().getTitle());
            line.setVisibility(categorySticker.isSelected() ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
