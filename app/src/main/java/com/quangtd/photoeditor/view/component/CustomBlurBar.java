package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.view.iface.listener.AbstractSeekBarChangeListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * QuangTD on 12/2/2017.
 */

@EViewGroup(R.layout.custom_blur_bar)
public class CustomBlurBar extends RelativeLayout {
    @ViewById(R.id.sbRadius)
    SeekBar mSbRadius;

    public CustomBlurBar(Context context) {
        super(context);
    }

    public CustomBlurBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBlurBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnBLurControlListener {
        void onChangeRadiusBlur(int radius);

        void onClickBlurClose();

        void onClickBlurOk();

    }

    @Accessors(prefix = "m")
    @Setter
    private OnBLurControlListener mListener;

    @AfterViews
    public void init() {
        mSbRadius.setOnSeekBarChangeListener(new AbstractSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                super.onStopTrackingTouch(seekBar);
                mListener.onChangeRadiusBlur(seekBar.getProgress());
            }
        });
    }

    @Click(R.id.imgClose)
    public void clickClose() {
        mListener.onClickBlurClose();
    }

    @Click(R.id.imgOk)
    public void clickOk() {
        mListener.onClickBlurOk();
    }

}
