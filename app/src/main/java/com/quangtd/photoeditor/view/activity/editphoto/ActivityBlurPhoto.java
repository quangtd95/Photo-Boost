package com.quangtd.photoeditor.view.activity.editphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.Effect;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.component.CustomBlurBar;
import com.quangtd.photoeditor.view.component.CustomDrawSticker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import jp.wasabeef.blurry.Blurry;

/**
 * QuangTD on 12/2/2017.
 */
@EActivity(R.layout.activity_blur_photo)
public class ActivityBlurPhoto extends ActivityBase implements CustomBlurBar.OnBLurControlListener {
    @ViewById(R.id.bottomBlur) CustomBlurBar mCustomBlurBar;
    @ViewById(R.id.drawOverlay) CustomDrawSticker mCustomDrawSticker;
    @ViewById(R.id.imgBackground) ImageView mImgBackground;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    private int mRadius = 15;
    private Bitmap mOrigin;
    private boolean isPrepared;

    @AfterViews
    public void init() {
        mOrigin = BitmapFactory.decodeFile(mImagePath);
        Blurry.with(this).radius(mRadius).from(mOrigin).into(mImgBackground);
        addSticker();
        addListener();
    }

    private void addSticker() {
        if (isPrepared) return;
        isPrepared = true;
        showProgressDialog();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap mBitmap = Bitmap.createScaledBitmap(mOrigin, mOrigin.getWidth() * 3 / 4, mOrigin.getHeight() * 3 / 4, true);
        mCustomDrawSticker.post(() -> {
            mCustomDrawSticker.addDecoItem(mBitmap, 1);
            dismissProgressDialog();
        });
    }

    private void addListener() {
        mCustomBlurBar.setListener(this);
    }

    private void refreshBlur() {
        if (mRadius > 0) {
            Blurry.with(ActivityBlurPhoto.this)
                    .radius(mRadius)
                    .async()
                    .from(mOrigin)
                    .into(mImgBackground);
        } else {
            mImgBackground.setImageBitmap(mOrigin);
        }
    }

    @Override public void onChangeRadiusBlur(int radius) {
        this.mRadius = radius;
        refreshBlur();
    }

    @Override public void onClickBlurClose() {
        finish();
    }

    @Override public void onClickBlurOk() {
        showProgressDialog();
        mImgBackground.setDrawingCacheEnabled(true);
        Bitmap bitmap = mImgBackground.getDrawingCache();
        String path = EditPhotoUtils.editAndSaveImage(bitmap, new Effect(), mCustomDrawSticker.getDecors());
        dismissProgressDialog();
        setResult(RESULT_OK, new Intent().putExtra(GlobalDefine.KEY_IMAGE, path));
        finish();
    }
}
