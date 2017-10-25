package com.quangtd.photoeditor.presenter;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.Filter;
import com.quangtd.photoeditor.model.net.DataCallBack;
import com.quangtd.photoeditor.model.repository.ListFilterRepository;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.iface.IViewEditPhoto;

import java.util.List;

/**
 * QuangTD on 10/19/2017.
 */

public class PresenterEditPhoto extends PresenterBase<IViewEditPhoto> {
    private ListFilterRepository mListFilterRepository;

    @Override public void onInit() {
        mListFilterRepository = ListFilterRepository.getInstance(getContext());
    }

    public void downloadFilter(int filterNumber) {
        getIFace().showLoading();
        mListFilterRepository.downloadFilter(filterNumber, new DataCallBack<String>() {
            @Override public void onSuccess(String result) {
                getIFace().downloadFilterSuccess(result);
                getIFace().hideLoading();
            }

            @Override public void onError(String message) {
                getIFace().downloadFilterFailure(message);
                getIFace().hideLoading();
            }
        });
    }

    public void save() {
        Bitmap bitmap = getIFace().getPhoto();
        Filter filter = getIFace().getFilter();
        List<Decor> decors = getIFace().getListDecor();
        new EditPhotoAsync(bitmap, filter, decors).execute();
    }

    private class EditPhotoAsync extends AsyncTask<Void, Void, String> {
        private Bitmap mBitmap;
        private Filter mFilter;
        private List<Decor> decors;

        EditPhotoAsync(Bitmap bitmap, Filter filter, List<Decor> decors) {
            this.mBitmap = bitmap;
            this.mFilter = filter;
            this.decors = decors;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            getIFace().showLoading();
        }

        @Override protected String doInBackground(Void... params) {
            return EditPhotoUtils.editAndSaveImage(mBitmap, mFilter, decors);
        }

        @Override protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getIFace().hideLoading();
            getIFace().showOutput(s);
        }
    }
}
