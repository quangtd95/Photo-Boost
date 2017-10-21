package com.quangtd.photoeditor.view.activity.choosesticker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.data.CategorySticker;
import com.quangtd.photoeditor.utils.ScreenUtils;
import com.quangtd.photoeditor.view.AdapterBase;
import com.quangtd.photoeditor.view.fragment.ListStickerAdapter;
import com.quangtd.photoeditor.view.ViewHolderBase;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 10/17/2017.
 */


public class CategoryStickerAdapter extends AdapterBase<CategoryStickerAdapter.CategoryStickerHolder> {
    public interface OnClickItemCategoryListener {
        void onClickItemCategory(int position);
    }

    private List<CategorySticker> mCategoryStickers;
    private int mImgSize;
    private int mFolderSize;
    private int mCurrentPosition;
    @Accessors(prefix = "m")
    @Setter
    private ListStickerAdapter.OnClickStickerListener mListener;

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

    public void setSelected(int position) {
        if (position < 0 || position >= mCategoryStickers.size()) return;
        mCategoryStickers.get(mCurrentPosition).setSelected(false);
        mCategoryStickers.get(position).setSelected(true);
        notifyItemChanged(mCurrentPosition);
        notifyItemChanged(position);
        mCurrentPosition = position;

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
                    if (mListener != null) {
                        mListener.onClickSticker(mCurrentPosition);
                    }
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
