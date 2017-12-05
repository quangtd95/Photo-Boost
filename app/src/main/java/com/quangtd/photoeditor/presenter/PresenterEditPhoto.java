package com.quangtd.photoeditor.presenter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.AsyncTask;

import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.Effect;
import com.quangtd.photoeditor.model.net.DataCallBack;
import com.quangtd.photoeditor.model.repository.ListFilterRepository;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.iface.IViewEditPhoto;

import java.util.List;

/**
 * QuangTD on 10/19/2017.
 */

public class PresenterEditPhoto extends PresenterBase<IViewEditPhoto> implements OnLoadImageFinishListener {
    private ListFilterRepository mListEffectRepository;

    @Override public void onInit() {
        mListEffectRepository = ListFilterRepository.getInstance(getContext());
    }

    public void downloadEffect(int effectNumber) {
        getIFace().showLoading();
        mListEffectRepository.downloadEffect(effectNumber, new DataCallBack<String>() {
            @Override public void onSuccess(String result) {
                getIFace().downloadEffectSuccess(result);
                getIFace().hideLoading();
            }

            @Override public void onError(String message) {
                getIFace().downloadEffectFailure(message);
                getIFace().hideLoading();
            }
        });
    }

    @Override public void onLoadFinish(String path) {
        getIFace().onPrepared(path);
        getIFace().hideLoading();
    }

    public void save() {
        Bitmap bitmap = getIFace().getPhoto();
        Effect effect = getIFace().getEffect();
        List<Decor> decors = getIFace().getListDecor();
        new EditPhotoAsync(bitmap, effect, decors, false).execute();
    }

    public void prepareImage(String mImagePath, int widthFrame, int heightFrame) {
        prepareImage(mImagePath, null, widthFrame, heightFrame);
    }

    public void prepareImage(String mImagePath, Matrix matrix, int widthFrame, int heightFrame) {
        getIFace().showLoading();
        new LoadLargeImageAsyncTask(this, mImagePath, matrix, widthFrame, heightFrame).execute();
    }

    private static class LoadLargeImageAsyncTask extends AsyncTask<Void, Void, String> {
        private int mWidthFrame, mHeightFrame;
        private String mPath;
        private Matrix mMatrix;
        private OnLoadImageFinishListener mListener;

        LoadLargeImageAsyncTask(OnLoadImageFinishListener listener, String path, Matrix mMatrix, int widthFrame, int heightFrame) {
            this.mPath = path;
            this.mWidthFrame = widthFrame;
            this.mHeightFrame = heightFrame;
            this.mListener = listener;
            this.mMatrix = mMatrix;
        }

        @Override protected String doInBackground(Void... voids) {
            return EditPhotoUtils.scaleBitmapFitScreen(mPath, mMatrix, mWidthFrame, mHeightFrame);
//            return EditPhotoUtils.getSquareImage(mPath, mWidthFrame, mHeightFrame);
        }

        @Override protected void onPostExecute(String s) {
            mListener.onLoadFinish(s);
        }
    }

    private class EditPhotoAsync extends AsyncTask<Void, Void, String> {
        private final boolean mIsTemp;
        private Bitmap mInput;
        private Effect mEffect;
        private List<Decor> decors;

        EditPhotoAsync(Bitmap bitmap, Effect effect, List<Decor> decors, boolean isTemp) {
            this.mInput = bitmap;
            this.mEffect = effect;
            this.decors = decors;
            this.mIsTemp = isTemp;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            getIFace().showLoading();
        }

        @Override protected String doInBackground(Void... params) {
            return EditPhotoUtils.editAndSaveImage(mInput, mEffect, decors, mIsTemp);
        }

        @Override protected void onPostExecute(String s) {

            super.onPostExecute(s);
            getIFace().hideLoading();
            getIFace().showOutput(s);
        }
    }
}
