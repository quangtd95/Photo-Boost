package com.quangtd.photoeditor.view.component;

/**
 * QuangTD on 12/2/2017.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.response.Filter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 10/16/2017.
 */


@EViewGroup(R.layout.custom_filter_bar)
public class CustomFilterBar extends RelativeLayout {
    @ViewById(R.id.rvFilters) RecyclerView mRvFilters;
    private CustomFilterAdapter mAdapter;

    public interface OnClickFilterListener {
        void onClickItemFilter(int position);

        void onClickFilterClose();

        void onClickFilterOk();

    }

    public CustomFilterBar(Context context) {
        super(context);
    }

    public CustomFilterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Accessors(prefix = "m")
    @Setter
    private OnClickFilterListener mListener;

    public void init(String path, List<Filter> mFilters) {
        mAdapter = new CustomFilterAdapter(this.getContext(), mFilters, path);
        mRvFilters.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvFilters.setAdapter(mAdapter);
        invalidate();
    }

    @Click(R.id.imgClose)
    public void onClickClose() {
        if (mListener != null) {
            mListener.onClickFilterClose();
        }
    }

    @Click(R.id.imgOk)
    public void onClickOk() {
        if (mListener != null) {
            mListener.onClickFilterOk();
        }
    }

    public void setOnClickFilterListener(OnClickFilterListener listener) {
        mListener = listener;
        if (listener != null) mAdapter.setListener(listener);
    }

}
