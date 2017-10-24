package com.quangtd.photoeditor.model.repository;

import android.content.Context;

import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.cloud.FireBaseUtils;
import com.quangtd.photoeditor.model.data.CategorySticker;
import com.quangtd.photoeditor.model.net.DataCallBack;

import java.io.File;
import java.util.Locale;

/**
 * QuangTD on 10/18/2017.
 */

public class ListStickerRepository {
    private static ListStickerRepository mInstance;
    private Context mContext;

    private ListStickerRepository(Context context) {
        mInstance = this;
        this.mContext = context;
    }

    public static synchronized ListStickerRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListStickerRepository(context);
        }
        return mInstance;
    }

    public StorageReference getRefImageInCloud(String path) {
        return FireBaseUtils.getInstance().getStorageReference(path);
    }

    public String convertStickerPath(CategorySticker categorySticker, int id) {
        return GlobalDefine.STICKER_FOLDER + File.separator
                + categorySticker.getFolderName() + File.separator
                + String.format(Locale.getDefault(), GlobalDefine.STICKER_FILE_FORMAT, id);
    }

    public void downloadSticker(StorageReference storageReference, DataCallBack<String> callBack) {
        FireBaseUtils.getInstance().downloadSticker(storageReference, callBack);
    }
}
