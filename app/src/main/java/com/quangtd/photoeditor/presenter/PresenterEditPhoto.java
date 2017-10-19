package com.quangtd.photoeditor.presenter;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.iface.IViewEditPhoto;

import java.util.List;

/**
 * QuangTD on 10/19/2017.
 */

public class PresenterEditPhoto extends PresenterBase<IViewEditPhoto> {
    @Override public void onInit() {

    }

    public void save() {
        Bitmap bitmap = getIFace().getPhoto();
        List<Decor> decors = getIFace().getListDecor();
        new EditPhotoAsync(bitmap, decors).execute();
    }

    private class EditPhotoAsync extends AsyncTask<Void, Void, String> {
        private Bitmap bitmap;
        private List<Decor> decors;

        EditPhotoAsync(Bitmap bitmap, List<Decor> decors) {
            this.bitmap = bitmap;
            this.decors = decors;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            getIFace().showLoading();
        }

        @Override protected String doInBackground(Void... params) {
            return EditPhotoUtils.editAndSaveImage(bitmap, decors);
        }

        @Override protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getIFace().hideLoading();
            getIFace().showOutput(s);

        }
    }
}
