package com.quangtd.photoeditor.view.iface;

import android.graphics.Bitmap;

import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.Effect;

import java.util.List;

/**
 * QuangTD on 10/19/2017.
 */

public interface IViewEditPhoto extends IViewBase {
    void onPrepared(String path);

    List<Decor> getListDecor();

    Bitmap getPhoto();

    Effect getEffect();

    void downloadEffectSuccess(String path);

    void downloadEffectFailure(String message);

    void showOutput(String output);
}
