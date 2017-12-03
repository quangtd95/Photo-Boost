package com.quangtd.photoeditor.view.activity.edittext;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.response.TextCategory;
import com.quangtd.photoeditor.utils.ImageResourceUtils;
import com.quangtd.photoeditor.utils.ScreenUtils;

import java.util.List;

public class TextCategoryAdapter extends RecyclerView.Adapter {
    public interface OnClickItemTextListener {
        void onClickItemText(int position);
    }

    private Context mContext;
    private List<TextCategory> mTextCategories;
    private int mSizeCategory;
    private int mCurrentPosition;
    private OnClickItemTextListener mOnClickItemTextListener;

    public void setOnClickItemTextListener(OnClickItemTextListener onClickItemTextListener) {
        this.mOnClickItemTextListener = onClickItemTextListener;
    }

    public TextCategoryAdapter(Context context, List<TextCategory> textCategories) {
        mContext = context;
        mTextCategories = textCategories;
        mSizeCategory = ScreenUtils.getWidthScreen(mContext) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_text_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).setData(mTextCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mTextCategories.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            itemView.getLayoutParams().width = mSizeCategory;

            itemView.setOnClickListener(v -> {
                if (mCurrentPosition != getLayoutPosition()) {
                    mTextCategories.get(mCurrentPosition).setSelect(false);
                    mTextCategories.get(getLayoutPosition()).setSelect(true);
                    notifyDataSetChanged();
                    mCurrentPosition = getLayoutPosition();
                    if (mOnClickItemTextListener != null) {
                        mOnClickItemTextListener.onClickItemText(getLayoutPosition());
                    }
                }
            });
        }

        public void setData(TextCategory textCategory) {
            imgPhoto.setImageResource(ImageResourceUtils.selectImageResourceText(textCategory.getName(), textCategory.isSelect()));
        }
    }
}
