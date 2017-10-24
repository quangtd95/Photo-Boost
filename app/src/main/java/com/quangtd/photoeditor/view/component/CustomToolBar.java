package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.quangtd.photoeditor.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * QuangTD on 10/15/2017.
 */

@EViewGroup(R.layout.custom_tool_bar)
public class CustomToolBar extends LinearLayout {
    @ViewById(R.id.rvTools)
    RecyclerView mRvTools;
    private CustomToolAdapter mAdapter;
    private String[] mTools;
    private int[] mIcons;

    public interface OnClickToolListener {
        void clickItem(CustomToolBar.TYPE type);
    }

    public enum TYPE {
        CHANGE_PHOTO, FLIP_H, FLIP_V, ZOOM_IN, ZOOM_OUT, ROTATE
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void init() {
        mTools = getContext().getResources().getStringArray(R.array.list_tools);
        mIcons = new int[]{R.drawable.ic_change_photo, R.drawable.ic_flip_h, R.drawable.ic_flip_v, R.drawable.ic_zoom_in, R.drawable.ic_zoom_out, R.drawable.ic_rotate};
        mAdapter = new CustomToolAdapter(this.getContext(), mTools, mIcons);
        mRvTools.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvTools.setAdapter(mAdapter);
    }

    public void setOnClickToolListener(OnClickToolListener listener) {
        if (listener != null) mAdapter.setListener(listener);
    }
}
