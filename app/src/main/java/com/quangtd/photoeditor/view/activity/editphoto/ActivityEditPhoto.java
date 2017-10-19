package com.quangtd.photoeditor.view.activity.editphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosesticker.StickerActivity_;
import com.quangtd.photoeditor.view.component.CustomDrawView;
import com.quangtd.photoeditor.view.component.CustomFeatureBar;
import com.quangtd.photoeditor.view.component.CustomFilterBar;
import com.quangtd.photoeditor.view.component.CustomToolBar;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
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
    @ViewById(R.id.customDraw)
    CustomDrawView mCustomDraw;
    @Extra(GlobalDefine.KEY_IMAGE)
    String mImagePath;

    private boolean mToolsVisible;

    @Override protected void init() {
        super.init();
        Glide.with(this).load(mImagePath).into(mCustomPreview);
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

    private void showBar(View view) {
        for (int index = 0; index < mFlContainer.getChildCount(); ++index) {
            View nextChild = mFlContainer.getChildAt(index);
            nextChild.setVisibility(View.INVISIBLE);
        }
        view.setVisibility(View.VISIBLE);
    }

    @Override public void clickItem(CustomFeatureBar.TYPE type) {
        Toast.makeText(this, "on click " + type, Toast.LENGTH_SHORT).show();
        switch (type) {
            case STICKER:
                StickerActivity_.intent(this).startForResult(GlobalDefine.MY_REQUEST_CODE_GET_STICKER);
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

    @OnActivityResult(GlobalDefine.MY_REQUEST_CODE_GET_STICKER)
    public void onResultGetSticker(int resultCode, Intent data) {
        if (resultCode == GlobalDefine.MY_RESULT_CODE_GET_STICKER) {
            if (data != null) {
                String pathSticker = data.getStringExtra(GlobalDefine.KEY_STICKER);
                addSticker(pathSticker);
            }
        }
    }

    private void addSticker(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
        mCustomDraw.setVisibility(View.VISIBLE);
        showProgressDialog();
        mCustomDraw.post(() -> {
            mCustomDraw.addDecoItem(mBitmap, 1);
            dismissProgressDialog();
        });
    }
}

