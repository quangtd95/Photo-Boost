package com.quangtd.photoeditor.view.activity.editphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.Effect;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.component.CustomCropBar;
import com.quangtd.photoeditor.view.component.CustomCropView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;


/**
 * Created by HuynhSang on 12/7/2017.
 */
@EActivity(R.layout.activity_crop_photo)
public class ActivityCropPhoto extends ActivityBase implements CustomCropBar.OnClickCropListener{
    @ViewById(R.id.bottomCrop) CustomCropBar mCustomCropBar;
    @ViewById(R.id.imgPreview) ImageView mImageView;
    @ViewById(R.id.cropView) CustomCropView mCropView;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    private Bitmap mOrigin;

    @Override protected void init() {
        mOrigin = BitmapFactory.decodeFile(mImagePath);
        mImageView.setImageBitmap(mOrigin);
        mCustomCropBar.setOnclickCropListener(this);
        mCropView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onClickCropClose() {
        finish();
    }

    @Override
    public void onClickCropOk() {
        new ActivityCropPhoto.FilterImageAsync().execute();
    }

    private class FilterImageAsync extends AsyncTask<Void, Void, String> {
        private Bitmap crop;

        @Override protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            crop = mCropView.getNewImage(mOrigin);
        }

        @Override protected String doInBackground(Void... voids) {
            return EditPhotoUtils.editAndSaveImage(crop, new Effect(), null,true);
        }

        @Override protected void onPostExecute(String s) {
            setResult(RESULT_OK, new Intent().putExtra(GlobalDefine.KEY_IMAGE, s));
            dismissProgressDialog();
            finish();
        }
    }
}
