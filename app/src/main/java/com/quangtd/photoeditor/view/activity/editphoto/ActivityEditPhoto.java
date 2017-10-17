package com.quangtd.photoeditor.view.activity.editphoto;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosesticker.StickerActivity_;
import com.quangtd.photoeditor.view.component.CustomFeatureBar;
import com.quangtd.photoeditor.view.component.CustomFilterBar;
import com.quangtd.photoeditor.view.component.CustomToolBar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * QuangTD on 10/15/2017.
 */
@EActivity(R.layout.activity_edit_photo)
public class ActivityEditPhoto extends ActivityBase implements CustomFeatureBar.OnClickFeatureListener, CustomToolBar.OnClickToolListener {
    @ViewById(R.id.bottomFeatures)
    CustomFeatureBar mCustomFeatureBar;
    @ViewById(R.id.bottomTools)
    CustomToolBar mCustomToolBar;
    @ViewById(R.id.preview)
    ImageView mCustomPreview;
    @ViewById(R.id.imgBack)
    CircleImageView mImgBack;
    @ViewById(R.id.imgSave)
    CircleImageView mImgSave;
    @ViewById(R.id.bottomFilters)
    CustomFilterBar mCustomFilterBar;
    @ViewById(R.id.containerBottom)
    FrameLayout mFlContainer;
    private boolean mToolsVisible;

    @Override protected void init() {
        super.init();
        mCustomFeatureBar.setOnClickFeatureListener(this);
        mCustomToolBar.setOnClickToolListener(this);
    }

    @Click(R.id.preview)
    public void onClickPreview() {
        if (mToolsVisible) {
            showBar(mCustomFeatureBar);
        } else {
            showBar(mCustomToolBar);
        }
        mToolsVisible = !mToolsVisible;
    }

    /*private void hideToolBar() {
        mCustomToolBar.setVisibility(View.INVISIBLE);
        mCustomFeatureBar.setVisibility(View.VISIBLE);
        mImgBack.setVisibility(View.VISIBLE);
        mImgSave.setVisibility(View.VISIBLE);
    }

    private void showToolBar() {
        mCustomFeatureBar.setVisibility(View.INVISIBLE);
        mCustomToolBar.setVisibility(View.VISIBLE);
        mImgBack.setVisibility(View.INVISIBLE);
        mImgSave.setVisibility(View.INVISIBLE);
    }*/

    private void showBar(View view) {
        hideAllBottomItem();
        view.setVisibility(View.VISIBLE);
    }

    private void hideAllBottomItem() {
        for (int index = 0; index < mFlContainer.getChildCount(); ++index) {
            View nextChild = mFlContainer.getChildAt(index);
            nextChild.setVisibility(View.INVISIBLE);
        }
    }

    @Override public void clickItem(CustomFeatureBar.TYPE type) {
        Toast.makeText(this, "on click " + type, Toast.LENGTH_SHORT).show();
        switch (type) {
            case STICKER:
                StickerActivity_.intent(this).start();
                break;
            case FILTER:
                showBar(mCustomFilterBar);
                break;
            default:
                showBar(mCustomFeatureBar);
        }
    }

    @Override public void clickItem(CustomToolBar.TYPE type) {
        Toast.makeText(this, "on click " + type, Toast.LENGTH_SHORT).show();
    }
}

