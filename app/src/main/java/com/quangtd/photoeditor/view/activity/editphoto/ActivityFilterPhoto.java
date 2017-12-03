package com.quangtd.photoeditor.view.activity.editphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.SeekBar;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.Effect;
import com.quangtd.photoeditor.model.response.Filter;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.component.CustomFilterBar;
import com.quangtd.photoeditor.view.iface.listener.AbstractSeekBarChangeListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * QuangTD on 12/2/2017.
 */

@EActivity(R.layout.activity_filter_photo)
public class ActivityFilterPhoto extends ActivityBase implements CustomFilterBar.OnClickFilterListener {
    @ViewById(R.id.bottomFilters) CustomFilterBar mCustomFilterBar;
    @ViewById(R.id.imgPreview) GPUImageView mGpuImageView;
    @ViewById(R.id.sb) SeekBar mSb;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    private List<Filter> mFilters;
    GPUImage mGpuImage;

    @AfterViews
    public void init() {
        mFilters = Filter.createFilterList();
        mGpuImage = new GPUImage(this);
        mGpuImageView.setImage(new File(mImagePath));
        mCustomFilterBar.init(mImagePath, mFilters);
        addListeners();
    }

    private void addListeners() {
        mCustomFilterBar.setOnClickFilterListener(this);
        mSb.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                super.onProgressChanged(seekBar, progress, fromUser);

            }
        });
    }

    private int mCurrentFilter;

    @Override public void onClickItemFilter(int position) {
        if (mCurrentFilter == position) return;
        if (position == 0) {
            mSb.setVisibility(View.VISIBLE);
            mGpuImageView.setFilter(new GPUImageFilter());
        } else {
            mSb.setVisibility(View.INVISIBLE);
            mGpuImageView.setFilter(Filter.getGpuFilter(this, mFilters.get(position)));
        }
        mCurrentFilter = position;


    }

    @Override public void onClickFilterClose() {
        finish();
    }

    @Override public void onClickFilterOk() {
        GPUImageFilter gpuImageFilter;
        if (mCurrentFilter == 0) {
            gpuImageFilter = new GPUImageFilter();
        } else {
            gpuImageFilter = Filter.getGpuFilter(this, mFilters.get(mCurrentFilter));
        }
        new FilterImageAsync(gpuImageFilter).execute();
    }

    private class FilterImageAsync extends AsyncTask<Void, Void, String> {
        private GPUImageFilter gpuImageFilter;

        public FilterImageAsync(GPUImageFilter gpuImageFilter) {
            this.gpuImageFilter = gpuImageFilter;
        }

        @Override protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            mGpuImage.setImage(BitmapFactory.decodeFile(mImagePath));
            mGpuImage.setFilter(gpuImageFilter);
        }

        @Override protected String doInBackground(Void... voids) {
            Bitmap filter = mGpuImage.getBitmapWithFilterApplied();
            return EditPhotoUtils.editAndSaveImage(filter, new Effect(), null);
        }

        @Override protected void onPostExecute(String s) {
            setResult(RESULT_OK, new Intent().putExtra(GlobalDefine.KEY_IMAGE, s));
            dismissProgressDialog();
            finish();
        }
    }
}
