package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.quangtd.photoeditor.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;

/**
 * Created by HuynhSang on 12/7/2017.
 */

@EViewGroup(R.layout.custom_crop_bar)
public class CustomCropBar extends RelativeLayout {


    public interface OnClickCropListener {

        void onClickCropClose();

        void onClickCropOk();
    }

    public CustomCropBar(Context context) {
        super(context);
    }

    public CustomCropBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private OnClickCropListener mListener;

    @Click(R.id.imgClose)
    public void onClickClose() {
        if (mListener != null) {
            mListener.onClickCropClose();
        }
    }

    @Click(R.id.imgOk)
    public void onClickOk() {
        if (mListener != null) {
            mListener.onClickCropOk();
        }
    }

    public void setOnclickCropListener(OnClickCropListener listener) {
        this.mListener = listener;
    }
}
