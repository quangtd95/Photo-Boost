package com.quangtd.photoeditor.view.activity.edittext;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quangtd.photoeditor.R;


public class FontAdapter extends RecyclerView.Adapter {
    public interface OnClickItemFontListener {
        void onClickItemFont(int position);
    }

    private Context mContext;
    private String[] mFontArrays;
    private OnClickItemFontListener mOnClickItemFontListener;
    private Typeface mPlain;
    private AssetManager mAssetManager;

    public void setOnClickItemFontListener(OnClickItemFontListener mOnClickItemFontListener) {
        this.mOnClickItemFontListener = mOnClickItemFontListener;
    }

    public FontAdapter(Context context,String[] fontArrays) {
        this.mContext = context;
        this.mFontArrays = fontArrays;
        mAssetManager = context.getAssets();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_text_font, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).setData(mFontArrays[position], position);
    }

    @Override
    public int getItemCount() {
        return mFontArrays.length;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNameFont;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvNameFont = itemView.findViewById(R.id.tvFont);
            itemView.setOnClickListener(v -> {
                if (mOnClickItemFontListener != null) {
                    mOnClickItemFontListener.onClickItemFont(getLayoutPosition());
                }
            });
        }

        void setData(String s, int position) {
            mTvNameFont.setText(s);
            Log.e("TAGG",s);
            if (position == 0) {
                mPlain = Typeface.DEFAULT;
            } else {
                mPlain = Typeface.createFromAsset(mAssetManager, String.format("%s.otf", s));
            }
            mTvNameFont.setTypeface(mPlain);
        }
    }
}
