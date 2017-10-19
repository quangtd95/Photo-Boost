package com.quangtd.photoeditor.view.iface;

import android.graphics.Bitmap;

import com.quangtd.photoeditor.model.data.Decor;

import java.util.List;

/**
 * Created by QuangTD on 10/19/2017.
 */

public interface IViewEditPhoto extends IViewBase {
    List<Decor> getListDecor();

    Bitmap getPhoto();

    void showOutput(String output);
}
