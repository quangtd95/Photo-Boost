package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.quangtd.photoeditor.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * QuangTD on 10/16/2017.
 */


@EViewGroup(R.layout.custom_filter_bar)
public class CustomFilterBar extends RelativeLayout {
    @ViewById(R.id.rvFilters)
    RecyclerView mRvFilters;
    private CustomFilterAdapter mAdapter;

    public interface OnClickFilterListener {
        void clickFilterItem(int position);
    }

    public CustomFilterBar(Context context) {
        super(context);
    }

    public CustomFilterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void init() {
        String[] nameFilters = new String[37];
        int[] filterIds = new int[37];
        for (int i = 1; i <= nameFilters.length; i++) {
            int drawableResourceId = this.getResources().getIdentifier("filter" + i, "drawable", this.getContext().getPackageName());
            filterIds[i - 1] = drawableResourceId;
        }

        mAdapter = new CustomFilterAdapter(this.getContext(), filterIds);
        mRvFilters.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvFilters.setAdapter(mAdapter);
    }

    public void setOnClickFilterListener(CustomFilterBar.OnClickFilterListener listener) {
        if (listener != null) mAdapter.setListener(listener);
    }

}