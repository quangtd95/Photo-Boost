package com.quangtd.photoeditor.view.activity.editphoto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.presenter.PresenterEditPhoto;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosesticker.StickerActivity_;
import com.quangtd.photoeditor.view.component.CustomDrawFilter;
import com.quangtd.photoeditor.view.component.CustomDrawSticker;
import com.quangtd.photoeditor.view.component.CustomFeatureBar;
import com.quangtd.photoeditor.view.component.CustomFilterBar;
import com.quangtd.photoeditor.view.component.CustomToolBar;
import com.quangtd.photoeditor.view.iface.IViewEditPhoto;
import com.quangtd.photoeditor.view.iface.listener.AbstractSeekBarChangeListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * QuangTD on 10/15/2017.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_edit_photo)
public class ActivityEditPhoto extends ActivityBase<PresenterEditPhoto> implements CustomFeatureBar.OnClickFeatureListener, CustomToolBar.OnClickToolListener, CustomDrawSticker.OnChangeItemStickerListener, IViewEditPhoto, CustomFilterBar.OnClickFilterListener {
    @ViewById(R.id.bottomFeatures) CustomFeatureBar mCustomFeatureBar;
    @ViewById(R.id.bottomTools) CustomToolBar mCustomToolBar;
    @ViewById(R.id.imgPreview) ImageView mCustomPreview;
    @ViewById(R.id.imgBack) CircleImageView mImgBack;
    @ViewById(R.id.imgSave) CircleImageView mImgSave;
    @ViewById(R.id.bottomFilters) CustomFilterBar mCustomFilterBar;
    @ViewById(R.id.containerBottom) FrameLayout mFlContainer;
    @ViewById(R.id.drawSticker) CustomDrawSticker mCustomDrawSticker;
    @ViewById(R.id.drawFilter) CustomDrawFilter mCustomDrawFilter;
    @ViewById(R.id.imgOriginFilter) ImageView mImgOriginFilter;
    @ViewById(R.id.sb) SeekBar mSeekBar;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    Bitmap mBitmap;

    private boolean mToolsVisible;

    @Override protected void init() {
        super.init();
        Glide.with(this).load(mImagePath).into(mCustomPreview);
        Glide.with(this).load(mImagePath).fitCenter().into(mImgOriginFilter);
        mBitmap = BitmapFactory.decodeFile(mImagePath);
        addListeners();
    }

    private void addListeners() {
        mCustomFeatureBar.setOnClickFeatureListener(this);
        mCustomFilterBar.setOnClickFilterListener(this);
        mCustomToolBar.setOnClickToolListener(this);
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
            showBar(mCustomToolBar);
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

    @Click(R.id.imgOriginFilter)
    void onClickClearFilter() {
        mCustomDrawFilter.clearFilter();
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
                mImgBack.setVisibility(View.INVISIBLE);
                mImgSave.setVisibility(View.INVISIBLE);
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

    @Override public void showOutput(String output) {
        Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
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
            Decor decor = mCustomDrawSticker.getFocusDecor();
            if (decor == null) return;
            decor.setAlpha(progress);
            mCustomDrawSticker.invalidate();
        }
    }

    @Override public void onClickFilterItem(int position) {
        getPresenter(this).downloadFilter(position + 1);
    }

    @Override public void onClickFilterClose() {
        onClickClearFilter();
        showBar(mCustomFeatureBar);
        mImgSave.setVisibility(View.VISIBLE);
        mImgBack.setVisibility(View.VISIBLE);
    }

    @Override public void onClickFilterOk() {
        showBar(mCustomFeatureBar);
        mImgSave.setVisibility(View.VISIBLE);
        mImgBack.setVisibility(View.VISIBLE);
    }

    @Override public void downloadFilterSuccess(String path) {
        mCustomDrawFilter.setResource(path);
    }

    @Override public void downloadFilterFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

