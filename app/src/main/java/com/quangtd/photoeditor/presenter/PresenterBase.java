package com.quangtd.photoeditor.presenter;

import android.content.Context;

import com.quangtd.photoeditor.view.iface.IViewBase;

/**
 * QuangTD on 10/5/2017.
 */

public abstract class PresenterBase<I extends IViewBase> {
    private I iFace;

    public PresenterBase() {

    }

    public I getIFace() {
        return this.iFace;
    }

    public void setIFace(I iFace) {
        this.iFace = iFace;
    }

    protected Context getContext() {
        return this.iFace != null ? this.iFace.getContext() : null;
    }

    protected String getTag() {
        return this.getClass().getName();
    }

    public void onInit() {
    }
}
