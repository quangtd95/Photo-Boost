package com.quangtd.photoeditor.view.activity.editphoto;

import android.content.Intent;
import android.os.Environment;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.response.Filter;
import com.quangtd.photoeditor.utils.TimeUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.component.CustomFilterBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * QuangTD on 12/2/2017.
 */

@EActivity(R.layout.activity_filter_photo)
public class ActivityFilterPhoto extends ActivityBase implements CustomFilterBar.OnClickFilterListener {
    @ViewById(R.id.bottomFilters) CustomFilterBar mCustomFilterBar;
    @ViewById(R.id.imgPreview) GPUImageView mGpuImageView;
    @Extra(GlobalDefine.KEY_IMAGE) String mImagePath;
    private List<Filter> mFilters;

    @AfterViews
    public void init() {
        mFilters = Filter.createFilterList();
        mGpuImageView.setImage(new File(mImagePath));
        mCustomFilterBar.init(mImagePath, mFilters);
        addListeners();
    }

    private void addListeners() {
        mCustomFilterBar.setOnClickFilterListener(this);
    }

    @Override public void onClickItemFilter(int position) {
        mGpuImageView.setFilter(new GPUImageFilter());
        mGpuImageView.setFilter(Filter.getGpuFilter(this, mFilters.get(position)));
    }

    @Override public void onClickFilterClose() {
        finish();
    }

    @Override public void onClickFilterOk() {
        String fileName = "temp_flter_" + TimeUtils.parseTimeStampToString(System.currentTimeMillis()) + ".png";
        mGpuImageView.saveToPictures(
                ".temp",
                fileName,
                uri -> {
                    setResult(RESULT_OK, new Intent().putExtra(GlobalDefine.KEY_IMAGE, Environment.getExternalStorageDirectory() + "/PICTURES/.temp/" + fileName));
                    finish();
                });
    }
}
