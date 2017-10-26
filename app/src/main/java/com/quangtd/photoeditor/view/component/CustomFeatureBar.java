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
 * QuangTD on 10/15/2017.
 */

@EViewGroup(R.layout.custom_feature_bar)
public class CustomFeatureBar extends RelativeLayout {
    @ViewById(R.id.rvFeatures)
    RecyclerView mRvFeatures;
    String[] mFeatures;
    int[] mIcons;
    private CustomFeatureAdapter mAdapter;

    public interface OnClickFeatureListener {
        void clickItem(TYPE type);
    }

    public enum TYPE {
        STICKER, ADJUST, EFFECT, FILTER, BLUR, TEXT, CROP, SHARE
    }

    public CustomFeatureBar(Context context) {
        super(context);
    }

    public CustomFeatureBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @AfterViews
    public void init() {
        mFeatures = getContext().getResources().getStringArray(R.array.list_features);
        mIcons = new int[]{R.drawable.ic_sticker, R.drawable.ic_adjust, R.drawable.ic_effect, R.drawable.ic_filter, R.drawable.ic_blur, R.drawable.ic_text, R.drawable.ic_crop, R.drawable.ic_share};
        mAdapter = new CustomFeatureAdapter(this.getContext(), mFeatures, mIcons);
        mRvFeatures.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvFeatures.setAdapter(mAdapter);
    }

    public void setOnClickFeatureListener(CustomFeatureBar.OnClickFeatureListener listener) {
        if (listener != null) mAdapter.setListener(listener);
    }

}
