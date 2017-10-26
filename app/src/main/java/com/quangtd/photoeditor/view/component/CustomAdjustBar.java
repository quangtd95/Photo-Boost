package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.quangtd.photoeditor.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * QuangTD on 10/15/2017.
 */

@EViewGroup(R.layout.custom_adjust_bar)
public class CustomAdjustBar extends LinearLayout {
    @ViewById(R.id.rvTools) RecyclerView mRvTools;
    private CustomAdjustAdapter mAdapter;
    private String[] mTools;
    private int[] mIcons;
    private OnClickAdjustListener mListener;

    public interface OnClickAdjustListener {
        void clickItemAdjust(CustomAdjustBar.TYPE type);

        void clickCloseAdjust();

        void clickOkAdjust();

    }

    public enum TYPE {
        CHANGE_PHOTO, FLIP_H, FLIP_V, ZOOM_IN, ZOOM_OUT, ROTATE
    }

    public CustomAdjustBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void init() {
        mTools = getContext().getResources().getStringArray(R.array.list_tools);
        mIcons = new int[]{R.drawable.ic_change_photo, R.drawable.ic_flip_h, R.drawable.ic_flip_v, R.drawable.ic_zoom_in, R.drawable.ic_zoom_out, R.drawable.ic_rotate};
        mAdapter = new CustomAdjustAdapter(this.getContext(), mTools, mIcons);
        mRvTools.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvTools.setAdapter(mAdapter);
    }

    @Click(R.id.imgClose)
    void onClickClose() {
        if (mListener != null) {
            mListener.clickCloseAdjust();
        }
    }

    @Click(R.id.imgOk)
    void onClickOk() {
        if (mListener != null) {
            mListener.clickOkAdjust();
        }
    }

    public void setOnClickToolListener(OnClickAdjustListener listener) {
        if (listener != null) {
            mListener = listener;
            mAdapter.setListener(listener);
        }
    }
}
