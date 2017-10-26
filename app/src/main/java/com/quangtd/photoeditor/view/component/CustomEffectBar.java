package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.quangtd.photoeditor.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * QuangTD on 10/16/2017.
 */


@EViewGroup(R.layout.custom_effect_bar)
public class CustomEffectBar extends RelativeLayout {
    @ViewById(R.id.rvEffects) RecyclerView mRvEffects;
    private CustomEffectAdapter mAdapter;

    public interface OnClickEffectListener {
        void onClickItemEffect(int position);

        void onClickEffectClose();

        void onClickEffectOk();

    }

    public CustomEffectBar(Context context) {
        super(context);
    }

    public CustomEffectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private OnClickEffectListener mListener;

    @AfterViews
    public void init() {
        String[] nameEffects = new String[37];
        int[] effectIds = new int[37];
        for (int i = 1; i <= nameEffects.length; i++) {
            int drawableResourceId = this.getResources().getIdentifier("filter" + i, "drawable", this.getContext().getPackageName());
            effectIds[i - 1] = drawableResourceId;
        }

        mAdapter = new CustomEffectAdapter(this.getContext(), effectIds);
        mRvEffects.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvEffects.setAdapter(mAdapter);
    }

    @Click(R.id.imgClose)
    public void onClickClose() {
        if (mListener != null) {
            mListener.onClickEffectClose();
        }
    }

    @Click(R.id.imgOk)
    public void onClickOk() {
        if (mListener != null) {
            mListener.onClickEffectOk();
        }
    }

    public void setOnClickEffectListener(OnClickEffectListener listener) {
        mListener = listener;
        if (listener != null) mAdapter.setListener(listener);
    }

}