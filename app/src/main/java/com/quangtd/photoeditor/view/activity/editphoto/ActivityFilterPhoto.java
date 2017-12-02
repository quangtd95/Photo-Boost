package com.quangtd.photoeditor.view.activity.editphoto;

import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.SeekBar;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.response.Filter;
import com.quangtd.photoeditor.utils.TimeUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.component.CustomFilterBar;
import com.quangtd.photoeditor.view.iface.listener.AbstractSeekBarChangeListener;

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
    @ViewById(R.id.sb) SeekBar mSb;
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
        mSb.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                super.onProgressChanged(seekBar, progress, fromUser);

            }
        });
    }

    @Override public void onClickItemFilter(int position) {
        if (position != 0) {
            mSb.setVisibility(View.VISIBLE);
        } else {
            mSb.setVisibility(View.INVISIBLE);
        }
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
