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
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

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
                handleChangeProgressBar(progress);
            }
        });
    }

    private void handleChangeProgressBar(int progress) {
        if (mGpuImageFilter == null) return;
        Filter filter = Filter.values()[mCurrentFilter];
        switch (filter) {
            case AMATORKA:
                GPUImageLookupFilter mGpuImageFilter2 = (GPUImageLookupFilter) mGpuImageFilter;
                mGpuImageFilter2.setIntensity(progress * 1.0f / 255);
                break;
            case BEAUTY:
                GPUImageSharpenFilter imageSharpenFilter = (GPUImageSharpenFilter) mGpuImageFilter;
                imageSharpenFilter.setSharpness(progress * 1.0f / 255 * 8 - 4);
                break;
            case BMW:
                break;
            case SWIRL:
                GPUImageSwirlFilter gpuImageSwirlFilter = (GPUImageSwirlFilter) mGpuImageFilter;
                gpuImageSwirlFilter.setRadius(progress * 1.0f / 255);
                break;
            case BRIGHTNESS:
                GPUImageBrightnessFilter gpuImageBrightnessFilter = (GPUImageBrightnessFilter) mGpuImageFilter;
                gpuImageBrightnessFilter.setBrightness(progress * 1.0f / 255 * 2 - 1);
                break;
            case LAPLACIAN:
                GPUImageLaplacianFilter gpuImageLaplacianFilter = (GPUImageLaplacianFilter) mGpuImageFilter;
                gpuImageLaplacianFilter.setLineSize(progress * 1.0f / 255 * 3);
                break;
            case MONOCHROME:
                GPUImageMonochromeFilter gpuImageMonochromeFilter = (GPUImageMonochromeFilter) mGpuImageFilter;
                gpuImageMonochromeFilter.setIntensity(progress * 1.0f / 255);
                break;
            case CGA_COLORSPACE:
                break;
            case VIGNETTE:
                GPUImageVignetteFilter gpuImageVignetteFilter = (GPUImageVignetteFilter) mGpuImageFilter;
                gpuImageVignetteFilter.setVignetteStart(progress * 1.0f / 255);
                break;
            case WHITE_BALANCE:
                GPUImageWhiteBalanceFilter gpuImageWhiteBalanceFilter = (GPUImageWhiteBalanceFilter) mGpuImageFilter;
                gpuImageWhiteBalanceFilter.setTint(progress * 1.0f / 255 * 100);
                break;
            case DILATION:
                break;
            case KUWAHARA:
                GPUImageKuwaharaFilter gpuImageKuwaharaFilter = (GPUImageKuwaharaFilter) mGpuImageFilter;
                gpuImageKuwaharaFilter.setRadius((int) (progress * 1.0f / 255 * 10));
                break;
            case RGB_DILATION:
                break;
            case SKETCH:
                break;
            case TOON:
                GPUImageToonFilter gpuImageToonFilter = (GPUImageToonFilter) mGpuImageFilter;
                gpuImageToonFilter.setQuantizationLevels(progress * 1.0f / 255 * 30);
                break;
            case SMOOTH_TOON:
                GPUImageSmoothToonFilter gpuImageSmoothToonFilter = (GPUImageSmoothToonFilter) mGpuImageFilter;
                gpuImageSmoothToonFilter.setBlurSize(progress * 1.0f / 255);
                break;

            case BULGE_DISTORTION:
                GPUImageBulgeDistortionFilter gpuImageBulgeDistortionFilter = (GPUImageBulgeDistortionFilter) mGpuImageFilter;
                gpuImageBulgeDistortionFilter.setRadius(progress * 1.0f / 255);
                break;
            case HAZE:
                GPUImageHazeFilter gpuImageHazeFilter = (GPUImageHazeFilter) mGpuImageFilter;
                gpuImageHazeFilter.setSlope(progress * 1.0f / 255 * 0.6f - 0.3f);
                break;
            case NON_MAXIMUM_SUPPRESSION:
                GPUImageNonMaximumSuppressionFilter gpuImageNonMaximumSuppressionFilter = (GPUImageNonMaximumSuppressionFilter) mGpuImageFilter;
                gpuImageNonMaximumSuppressionFilter.setLineSize(progress * 1.0f / 255 * 10);
                break;
            case FALSE_COLOR:
                break;
            case COLOR_BALANCE:
                break;
            case LEVELS_FILTER_MIN:
                break;
            case HALFTONE:
                GPUImageHalftoneFilter gpuImageHalftoneFilter = (GPUImageHalftoneFilter) mGpuImageFilter;
                gpuImageHalftoneFilter.setFractionalWidthOfAPixel(progress * 1.0f / 255 * 0.1f);
                break;
        }
        mGpuImageView.requestRender();
    }

    private int mCurrentFilter;
    private GPUImageFilter mGpuImageFilter;

    @Override public void onClickItemFilter(int position) {
        if (mCurrentFilter == position) return;
        if (position == 0) {
            mSb.setVisibility(View.INVISIBLE);
            mGpuImageFilter = new GPUImageFilter();
        } else {
            mSb.setVisibility(View.VISIBLE);
            mGpuImageFilter = Filter.getGpuFilter(this, mFilters.get(position));
        }
        mGpuImageView.setFilter(mGpuImageFilter);
        mCurrentFilter = position;


    }

    @Override public void onClickFilterClose() {
        finish();
    }

    @Override public void onClickFilterOk() {
        new FilterImageAsync(mGpuImageFilter).execute();
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
