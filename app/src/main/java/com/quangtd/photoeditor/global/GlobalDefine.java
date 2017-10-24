package com.quangtd.photoeditor.global;

import android.os.Environment;

/**
 * QuangTD on 10/5/2017.
 */

public class GlobalDefine {
    public static final String APP_NAME = "photo_editor";
    public static final String STICKER_FOLDER = "stickers";
    public static final String STICKER_FILE_FORMAT = ".image%d.png";
    public static final String FILTER_FOLDER = "filters";
    public static final String FILTER_FILE_FORMAT = ".filter%d.png";
    public static final String FIREBASE_USER_NAME = "pfiev13.photoeditor@gmail.com";
    public static final String FIREBASE_PASSWORD = "quang123";

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    public static final int MY_REQUEST_CODE_GET_STICKER = 102;
    public static final int MY_RESULT_CODE_GET_STICKER = 103;

    public static final String KEY_IMAGE = "image";
    public static final String KEY_STICKER = "sticker";
    public static final String KEY_CATEGORY = "category";

    public static final String STICKER_FOLDER_LOCAL = Environment.getExternalStorageDirectory()
            + "/." + APP_NAME + "/." + STICKER_FOLDER + "/";
    public static final String FILTER_FOLDER_LOCAL = Environment.getExternalStorageDirectory()
            + "/." + APP_NAME + "/." + FILTER_FOLDER + "/";
    public static final String OUTPUT_FOLDER = Environment.getExternalStorageDirectory() + "/" + APP_NAME + "/";

}
