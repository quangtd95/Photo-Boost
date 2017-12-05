package com.quangtd.photoeditor.view.activity.editphoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.muddzdev.viewshotlibrary.Viewshot;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.Effect;
import com.quangtd.photoeditor.presenter.PresenterEditPhoto;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.utils.FileUtils;
import com.quangtd.photoeditor.utils.ScreenUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosesticker.StickerActivity_;
import com.quangtd.photoeditor.view.activity.edittext.ActivityEditText_;
import com.quangtd.photoeditor.view.component.CustomAdjustBar;
import com.quangtd.photoeditor.view.component.CustomDrawEffect;
import com.quangtd.photoeditor.view.component.CustomDrawSticker;
import com.quangtd.photoeditor.view.component.CustomEffectBar;
import com.quangtd.photoeditor.view.component.CustomFeatureBar;
import com.quangtd.photoeditor.view.iface.IViewEditPhoto;
import com.quangtd.photoeditor.view.iface.listener.AbstractSeekBarChangeListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * QuangTD on 10/15/2017.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_edit_photo)
public class ActivityEditPhoto extends ActivityBase<PresenterEditPhoto> implements CustomFeatureBar.OnClickFeatureListener, CustomAdjustBar.OnClickAdjustListener, CustomDrawSticker.OnChangeItemStickerListener, IViewEditPhoto, CustomEffectBar.OnClickEffectListener {
    @ViewById(R.id.bottomFeatures) CustomFeatureBar mCustomFeatureBar;
    @ViewById(R.id.bottomTools) CustomAdjustBar mCustomAdjustBar;
    @ViewById(R.id.imgPreview) PhotoView mCustomPreview;
    @ViewById(R.id.imgBack) CircleImageView mImgBack;
    @ViewById(R.id.imgSave) CircleImageView mImgSave;
    @ViewById(R.id.bottomEffects) CustomEffectBar mCustomEffectBar;
    @ViewById(R.id.containerBottom) FrameLayout mFlContainer;
    @ViewById(R.id.drawSticker) CustomDrawSticker mCustomDrawSticker;
    @ViewById(R.id.drawEffect) CustomDrawEffect mCustomDrawEffect;
    @ViewById(R.id.imgOriginEffect) ImageView mImgOriginFilter;
    @ViewById(R.id.sb) SeekBar mSeekBar;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    Bitmap mBitmap;
    private static float DEFAULT_MAX_SCALE = 3.0f;
    private static float DEFAULT_MIN_SCALE = 1.0f;

    private boolean mToolsVisible;
    private Matrix mMatrix;

    @Override protected void init() {
        super.init();
        int widthFrame = ScreenUtils.getWidthScreen(this);
        int heightFrame = ScreenUtils.getHeightScreen(this) - ScreenUtils.convertDpToPixel(this, 100);
        getPresenter(this).prepareImage(mImagePath, widthFrame, heightFrame);
    }

    @Override
    public void onPrepared(String path) {
        this.mImagePath = path;
        mBitmap = BitmapFactory.decodeFile(mImagePath);
        Glide.with(this).load(mImagePath).into(mCustomPreview);
        RequestOptions requestOptions = new RequestOptions().fitCenter();
        Glide.with(this).setDefaultRequestOptions(requestOptions).load(mImagePath).into(mImgOriginFilter);
        addListeners();
    }

    private void addListeners() {
        mCustomFeatureBar.setOnClickFeatureListener(this);
        mCustomEffectBar.setOnClickEffectListener(this);
        mCustomAdjustBar.setOnClickToolListener(this);
        mCustomDrawSticker.setOnChangeItemStickerListener(this);
        mSeekBar.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ActivityEditPhoto.this.onSeekBarProgressChanged(progress, fromUser);
            }
        });
    }

    @Click(R.id.imgPreview)
    void onClickPreview() {
        if (mToolsVisible) {
            showBar(mCustomFeatureBar);
        } else {
            showBar(mCustomAdjustBar);
        }
        mToolsVisible = !mToolsVisible;
    }

    @Click(R.id.imgBack)
    void onClickBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.confirm_back));
        builder.setCancelable(true);
        builder.setNeutralButton(getResources().getString(R.string.btn_cancel), (dialog, which) -> dialog.dismiss());
        builder.setNegativeButton(getResources().getString(R.string.discard), (dialog, which) -> finish());
        builder.setPositiveButton(getResources().getString(R.string.save), (dialog, which) -> {
            dialog.dismiss();
            getPresenter(ActivityEditPhoto.this).save();
        });
        builder.show();
    }

    @Click(R.id.imgSave)
    void onClickSave() {
        getPresenter(this).save();
    }

    @Click(R.id.imgOriginEffect)
    void onClickClearEffect() {
        mCustomDrawEffect.clearEffect();
    }

    private void showBar(View view) {
        for (int index = 0; index < mFlContainer.getChildCount(); ++index) {
            View nextChild = mFlContainer.getChildAt(index);
            nextChild.setVisibility(View.INVISIBLE);
        }
        view.setVisibility(View.VISIBLE);
        if (view == mCustomFeatureBar) {
            mImgBack.setVisibility(View.VISIBLE);
            mImgSave.setVisibility(View.VISIBLE);
        } else {
            mImgBack.setVisibility(View.GONE);
            mImgSave.setVisibility(View.GONE);
        }
        if (view == mCustomAdjustBar) {
            mCustomDrawSticker.setVisibility(View.GONE);
            mCustomDrawEffect.setVisibility(View.GONE);
        } else {
            mCustomDrawSticker.setVisibility(View.VISIBLE);
            mCustomDrawEffect.setVisibility(View.VISIBLE);
        }
        mSeekBar.setVisibility(View.INVISIBLE);
    }

    @Override public void clickItem(CustomFeatureBar.TYPE type) {
        Toast.makeText(this, "on click " + type, Toast.LENGTH_SHORT).show();
        switch (type) {
            case STICKER:
                StickerActivity_.intent(this).startForResult(GlobalDefine.MY_REQUEST_CODE_GET_STICKER);
                break;
            case ADJUST:
                if (mMatrix == null) mMatrix = new Matrix();
                showBar(mCustomAdjustBar);
                break;
            case EFFECT:
                showBar(mCustomEffectBar);
                mSeekBar.setVisibility(View.VISIBLE);
                mSeekBar.setProgress(100);
                break;
            case FILTER:
                ActivityFilterPhoto_.intent(this).extra(GlobalDefine.KEY_IMAGE, mImagePath).startForResult(GlobalDefine.MY_REQUEST_CODE_FILTER);
                break;
            case BLUR:
                ActivityBlurPhoto_.intent(this).extra(GlobalDefine.KEY_IMAGE, mImagePath).startForResult(GlobalDefine.MY_REQUEST_CODE_BLUR);
                break;
            case TEXT:
                ActivityEditText_.intent(this).extra(GlobalDefine.KEY_IMAGE, mImagePath).startForResult(GlobalDefine.MY_REQUEST_CODE_TEXT);
            default:
                showBar(mCustomFeatureBar);
        }
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

    private void changeImage(String path) {
        mCustomPreview.setRotation(0);
        mCustomPreview.setScale(1);
        mCustomPreview.setScaleX(1);
        mCustomPreview.setScaleY(1);
        mMatrix = null;
        int widthFrame = ScreenUtils.getWidthScreen(this);
        int heightFrame = ScreenUtils.getHeightScreen(this) - ScreenUtils.convertDpToPixel(this, 100);
        getPresenter(this).prepareImage(path, widthFrame, heightFrame);
    }

    private void changeImage(Matrix matrix) {
        int widthFrame = ScreenUtils.getWidthScreen(this);
        int heightFrame = ScreenUtils.getHeightScreen(this) - ScreenUtils.convertDpToPixel(this, 100);
        getPresenter(this).prepareImage(mImagePath, matrix, widthFrame, heightFrame);
    }

    @OnActivityResult(GlobalDefine.MY_REQUEST_CODE_FILTER)
    public void onResultFilter(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            changeImage(data.getStringExtra(GlobalDefine.KEY_IMAGE));
        }
    }

    @OnActivityResult(GlobalDefine.MY_REQUEST_CODE_BLUR)
    public void onResultBlur(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            changeImage(data.getStringExtra(GlobalDefine.KEY_IMAGE));
        }
    }


    @OnActivityResult(GlobalDefine.MY_REQUEST_CODE_TEXT)
    public void onResultText(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            changeImage(data.getStringExtra(GlobalDefine.KEY_IMAGE));
        }
    }

    private void addSticker(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap mBitmap = BitmapFactory.decodeFile(path, options);
        mSeekBar.setVisibility(View.VISIBLE);
        showProgressDialog();
        mCustomDrawSticker.post(() -> {
            mCustomDrawSticker.addDecoItem(mBitmap, 1);
            dismissProgressDialog();
        });
    }

    @Override public Context getContext() {
        return null;
    }

    @Override public void showLoading() {
        showProgressDialog();
    }

    @Override public void hideLoading() {
        dismissProgressDialog();
    }

    @Override public List<Decor> getListDecor() {
        return mCustomDrawSticker.getDecors();
    }

    @Override public Bitmap getPhoto() {
        return mBitmap;
    }

    @Override public Effect getEffect() {
        return mCustomDrawEffect.getEffect();
    }

    @Override public void showOutput(String output) {
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override public void changeSticker(int oldP, int newP) {
        if (newP == -1) {
            mSeekBar.setVisibility(View.GONE);
        } else {
            mSeekBar.setVisibility(View.VISIBLE);
            mSeekBar.setProgress(mCustomDrawSticker.getDecors().get(newP).getAlpha());
        }

    }

    public void onSeekBarProgressChanged(int progress, boolean fromUser) {
        if (fromUser) {
            if (mCustomEffectBar.getVisibility() == View.VISIBLE) {
                mCustomDrawEffect.setAlpha(progress);
            } else {
                Decor decor = mCustomDrawSticker.getFocusDecor();
                if (decor == null) return;
                decor.setAlpha(progress);
                mCustomDrawSticker.invalidate();
            }
        }
    }

    @Override public void onClickItemEffect(int position) {
        getPresenter(this).downloadEffect(position + 1);
    }

    @Override public void onClickEffectClose() {
        onClickClearEffect();
        showBar(mCustomFeatureBar);
    }

    @Override public void onClickEffectOk() {
        showBar(mCustomFeatureBar);
    }

    @Override public void clickItemAdjust(CustomAdjustBar.TYPE type) {
        switch (type) {
            case ZOOM_IN:
                float scale = mCustomPreview.getScale() + 0.5f;
                mCustomPreview.setScale((scale < DEFAULT_MAX_SCALE) ? scale : DEFAULT_MAX_SCALE, true);
                break;
            case ZOOM_OUT:
                scale = mCustomPreview.getScale() - 0.5f;
                mCustomPreview.setScale((scale < DEFAULT_MIN_SCALE) ? DEFAULT_MIN_SCALE : scale, true);
                break;
            case ROTATE:
                float mRotation = mCustomPreview.getRotation() + 90;
                if (mRotation >= 360) mRotation = 0;
                if (mRotation < 0) mRotation = 0;
                mCustomPreview.setRotation(mRotation);
                mMatrix.setRotate(mRotation);
                break;
            case FLIP_H:
                float scaleX = mCustomPreview.getScaleX();
                mCustomPreview.setScaleX(-1 * scaleX);
//                mMatrix.setScale(scaleX, mCustomPreview.getScaleY());
                mMatrix.postScale(-1,1,mCustomPreview.getWidth() / 2,mCustomPreview.getHeight() / 2);

                break;
            case FLIP_V:
                float scaleY = mCustomPreview.getScaleY();
                mCustomPreview.setScaleY(-1 * scaleY);
//                mMatrix.setScale(mCustomPreview.getScaleX(), scaleY);
                mMatrix.postScale(1,-1,mCustomPreview.getWidth() / 2,mCustomPreview.getHeight() / 2);
                break;
        }
    }

    @Override public void clickCloseAdjust() {
        mCustomPreview.setRotation(0);
        mCustomPreview.setScale(1);
        mCustomPreview.setScaleX(1);
        mCustomPreview.setScaleY(1);
        mMatrix = null;
        showBar(mCustomFeatureBar);
    }

    @Override public void clickOkAdjust() {
        showLoading();
        Viewshot.of(mCustomPreview)
                .setFilename(System.currentTimeMillis() + "")
                .setDirectoryPath("." + GlobalDefine.APP_NAME)
                .toPNG()
                .setOnSaveResultListener((isSaved, path) -> {
                    if (isSaved) {
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        String pathFinal = EditPhotoUtils.editAndSaveImage(bitmap, new Effect(), mCustomDrawSticker.getDecors(), mMatrix, true);
                        changeImage(pathFinal);
                        showBar(mCustomFeatureBar);
                    }
                })
                .save();

    }

    @Override public void downloadEffectSuccess(String path) {
        mCustomDrawEffect.setResource(path);
    }

    @Override public void downloadEffectFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        FileUtils.deleteFolder(new File(GlobalDefine.OUTPUT_TEMP));
    }
}

