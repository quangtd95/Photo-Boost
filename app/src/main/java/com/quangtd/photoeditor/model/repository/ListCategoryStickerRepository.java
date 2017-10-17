package com.quangtd.photoeditor.model.repository;

import android.content.Context;

import com.quangtd.photoeditor.model.net.ApiClient;
import com.quangtd.photoeditor.model.net.DataCallBack;
import com.quangtd.photoeditor.model.response.CategoryStickerResponse;

/**
 * QuangTD on 10/17/2017.
 */

public class ListCategoryStickerRepository {
    private static ListCategoryStickerRepository mInstance;
    private Context mContext;

    private ListCategoryStickerRepository(Context context) {
        mInstance = this;
        this.mContext = context;
    }

    public static synchronized ListCategoryStickerRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListCategoryStickerRepository(context);
        }
        return mInstance;
    }

    public void getListCategorySticker(DataCallBack<CategoryStickerResponse> callBack) {
        ApiClient.getService().getListCategory().enqueue(callBack);
    }
}
