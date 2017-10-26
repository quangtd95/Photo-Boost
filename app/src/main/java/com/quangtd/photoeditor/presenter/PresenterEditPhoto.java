package com.quangtd.photoeditor.presenter;

import android.graphics.Bitmap;
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

public class PresenterEditPhoto extends PresenterBase<IViewEditPhoto> {
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

    public void save() {
        Bitmap bitmap = getIFace().getPhoto();
        Effect effect = getIFace().getEffect();
        List<Decor> decors = getIFace().getListDecor();
        new EditPhotoAsync(bitmap, effect, decors).execute();
    }

    private class EditPhotoAsync extends AsyncTask<Void, Void, String> {
        private Bitmap mBitmap;
        private Effect mEffect;
        private List<Decor> decors;

        EditPhotoAsync(Bitmap bitmap, Effect effect, List<Decor> decors) {
            this.mBitmap = bitmap;
            this.mEffect = effect;
            this.decors = decors;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            getIFace().showLoading();
        }

        @Override protected String doInBackground(Void... params) {
            return EditPhotoUtils.editAndSaveImage(mBitmap, mEffect, decors);
        }

        @Override protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getIFace().hideLoading();
            getIFace().showOutput(s);
        }
    }
}
