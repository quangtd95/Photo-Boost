package com.quangtd.photoeditor.model.repository;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.cloud.FireBaseUtils;
import com.quangtd.photoeditor.model.net.DataCallBack;

import java.io.File;

/**
 * QuangTD on 10/24/2017.
 */

public class ListFilterRepository {
    private static ListFilterRepository mInstance;
    private Context mContext;

    private ListFilterRepository(Context context) {
        mInstance = this;
        this.mContext = context;
    }

    public static synchronized ListFilterRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListFilterRepository(context);
        }
        return mInstance;
    }

    /**
     * @param filerIndex start from 1
     * @param callBack
     */
    public void downloadEffect(int filerIndex, DataCallBack<String> callBack) {
        @SuppressLint("DefaultLocale")
        String filterName = String.format(GlobalDefine.EFFECT_FILE_FORMAT, filerIndex);
        StorageReference storageReference = FireBaseUtils.getInstance().getStorageReference(GlobalDefine.EFFECT_FOLDER + File.separator + filterName);
        FireBaseUtils.getInstance().downloadEffect(storageReference, callBack);
    }

}
