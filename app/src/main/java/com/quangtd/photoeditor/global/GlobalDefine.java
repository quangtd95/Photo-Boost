package com.quangtd.photoeditor.global;

import android.os.Environment;

import com.quangtd.photoeditor.model.cloud.ServerConst;

/**
 * QuangTD on 10/5/2017.
 */

public class GlobalDefine {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    public static final int MY_REQUEST_CODE_GET_STICKER = 102;
    public static final int MY_RESULT_CODE_GET_STICKER = 103;

    public static final String KEY_IMAGE = "image";
    public static final String KEY_STICKER = "sticker";
    public static final String KEY_CATEGORY = "category";

    public static final String STICKER_FOLDER_LOCAL = Environment.getExternalStorageDirectory() + "/." + ServerConst.APP_NAME + "/." + ServerConst.STICKER_FOLDER + "/";
    public static final String OUTPUT_FOLDER = Environment.getExternalStorageDirectory() + "/" + ServerConst.APP_NAME + "/";

}
