package com.quangtd.photoeditor.view.iface;

import android.graphics.Bitmap;

import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.Filter;

import java.util.List;

/**
 * Created by QuangTD on 10/19/2017.
 */

public interface IViewEditPhoto extends IViewBase {
    List<Decor> getListDecor();

    Bitmap getPhoto();

    Filter getFilter();

    void downloadFilterSuccess(String path);

    void downloadFilterFailure(String message);

    void showOutput(String output);
}
