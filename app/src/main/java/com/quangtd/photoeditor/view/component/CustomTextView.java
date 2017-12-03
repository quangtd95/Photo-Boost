package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.response.TextCategory;
import com.quangtd.photoeditor.view.activity.edittext.ColorAdapter;
import com.quangtd.photoeditor.view.activity.edittext.FontAdapter;
import com.quangtd.photoeditor.view.activity.edittext.OnChangeCustomTextListener;
import com.quangtd.photoeditor.view.activity.edittext.TextCategoryAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * QuangTD on 5/24/2017.
 */

public class CustomTextView extends LinearLayout implements TextCategoryAdapter.OnClickItemTextListener,
        FontAdapter.OnClickItemFontListener, ColorAdapter.OnClickItemColorListener, OnChangeCustomTextListener {
    private RecyclerView mRecyclerCategoryText;
    private RecyclerView mRecyclerFont;
    private RecyclerView mRecyclerColor;
    private CustomAdjustmentText mContainerAdjustment;

    private TextCategoryAdapter mCategoryAdapter;
    private FontAdapter mFontAdapter;
    private ColorAdapter mColorAdapter;

    private List<TextCategory> mTextCategories = new ArrayList<>();

    private OnChangeCustomTextListener mOnChangeCustomTextListener;

    public void setOnChangeCustomTextListener(OnChangeCustomTextListener onChangeCustomTextListener) {
        this.mOnChangeCustomTextListener = onChangeCustomTextListener;
    }

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_text_view, this, false);
        addView(v);

        mRecyclerCategoryText = v.findViewById(R.id.recyclerCategoryText);
        mRecyclerFont = v.findViewById(R.id.recyclerFont);
        mRecyclerColor = v.findViewById(R.id.recyclerColor);
        mContainerAdjustment = v.findViewById(R.id.containerAdjustment);
        mContainerAdjustment.setOnChangeAdjustmentTextListener(this);

        LinearLayoutManager categoryManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerCategoryText.setLayoutManager(categoryManager);
        mCategoryAdapter = new TextCategoryAdapter(context, mTextCategories);
        mRecyclerCategoryText.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnClickItemTextListener(this);

        LinearLayoutManager fontManager = new LinearLayoutManager(context);
        mRecyclerFont.setLayoutManager(fontManager);
        mFontAdapter = new FontAdapter(context, context.getResources().getStringArray(R.array.array_font));
        mRecyclerFont.setAdapter(mFontAdapter);
        mFontAdapter.setOnClickItemFontListener(this);

        GridLayoutManager colorManager = new GridLayoutManager(context, 7);
        GridSpacingItemDecoration itemDecoration1 = new GridSpacingItemDecoration(7, 15, true);
        mRecyclerColor.setLayoutManager(colorManager);
        mColorAdapter = new ColorAdapter(context);
        mRecyclerColor.setAdapter(mColorAdapter);
        mColorAdapter.setOnClickItemColorListener(this);
        mRecyclerColor.addItemDecoration(itemDecoration1);
        dummyDataCategory();
        mRecyclerFont.setVisibility(VISIBLE);
    }

    private void dummyDataCategory() {
        String[] categoryArr = getResources().getStringArray(R.array.array_text_category);
        int i = 0;
        for (String name : categoryArr) {
            TextCategory category = new TextCategory(name);
            if (i == 0) {
                category.setSelect(true);
            }
            mTextCategories.add(category);
            i++;
        }
        mCategoryAdapter.notifyDataSetChanged();
    }

    private void hideDefault() {
        mRecyclerFont.setVisibility(GONE);
        mRecyclerColor.setVisibility(GONE);
        mContainerAdjustment.setVisibility(GONE);
    }

    @Override
    public void onClickItemText(int position) {
        hideDefault();
        switch (position) {
            case 0:
                mRecyclerFont.setVisibility(VISIBLE);
                break;
            case 1:
                mRecyclerColor.setVisibility(VISIBLE);
                break;
            case 2:
                mContainerAdjustment.setVisibility(VISIBLE);
                break;
            default:
        }
    }

    @Override
    public void onClickItemFont(int position) {
        if (mOnChangeCustomTextListener != null) {
            mOnChangeCustomTextListener.onClickItemFont(position);
        }
    }

    @Override
    public void onClickItemColor(int color) {
        if (mOnChangeCustomTextListener != null) {
            mOnChangeCustomTextListener.onClickItemColor(color);
        }
    }

    @Override
    public void onChangeSize(int size) {
        if (mOnChangeCustomTextListener != null) {
            mOnChangeCustomTextListener.onChangeSize(size);
        }
    }

    @Override
    public void onChangeAlign(int align) {
        if (mOnChangeCustomTextListener != null) {
            mOnChangeCustomTextListener.onChangeAlign(align);
        }
    }

    @Override
    public void onChangeType(int type) {
        if (mOnChangeCustomTextListener != null) {
            mOnChangeCustomTextListener.onChangeType(type);
        }
    }

    @Override
    public void onChangeShadow(int shadow) {
        if (mOnChangeCustomTextListener != null) {
            mOnChangeCustomTextListener.onChangeShadow(shadow);
        }
    }

    public void setSizeMax(boolean max) {
        mContainerAdjustment.setMaxSize(max);
    }

    public void setSizeShadow(boolean max) {
        mContainerAdjustment.setMaxShadow(max);
    }
}
