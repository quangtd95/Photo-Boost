package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.view.activity.edittext.OnChangeCustomTextListener;


public class CustomAdjustmentText extends LinearLayout implements View.OnClickListener {
    private ImageView mImgAlignLeft;
    private ImageView mImgAlignCenter;
    private ImageView mImgAlignRight;
    private ImageView mImgSizeDes;
    private ImageView mImgSizeAsc;
    private ImageView mImgNormalText;
    private ImageView mImgItalicText;
    private ImageView mImgBoldText;
    private ImageView mImgUnderLineText;
    private ImageView mImgCenterLineText;
    private ImageView mImgShadowDes;
    private ImageView mImgShadowAsc;
    private OnChangeCustomTextListener mOnChangeAdjustmentTextListener;

    public void setOnChangeAdjustmentTextListener(OnChangeCustomTextListener onChangeAdjustmentTextListener) {
        this.mOnChangeAdjustmentTextListener = onChangeAdjustmentTextListener;
    }

    public CustomAdjustmentText(Context context) {
        this(context, null);
    }

    public CustomAdjustmentText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomAdjustmentText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_setting_text, this, false);
        addView(v);

        mImgAlignLeft = v.findViewById(R.id.imgLeftAlign);
        mImgAlignCenter = v.findViewById(R.id.imgCenterAlign);
        mImgAlignRight = v.findViewById(R.id.imgRightAlign);

        mImgSizeDes = v.findViewById(R.id.imgDes);
        mImgSizeAsc = v.findViewById(R.id.imgAsc);

        mImgNormalText = v.findViewById(R.id.imgNormalText);
        mImgItalicText = v.findViewById(R.id.imgItalyText);
        mImgBoldText = v.findViewById(R.id.imgBoldText);
        mImgUnderLineText = v.findViewById(R.id.imgUnderLineText);
        mImgCenterLineText = v.findViewById(R.id.imgCenterLineText);

        mImgShadowDes = v.findViewById(R.id.imgDesShadow);
        mImgShadowAsc = v.findViewById(R.id.imgAscShadow);

        mImgAlignLeft.setOnClickListener(this);
        mImgAlignCenter.setOnClickListener(this);
        mImgAlignRight.setOnClickListener(this);

        mImgSizeDes.setOnClickListener(this);
        mImgSizeAsc.setOnClickListener(this);

        mImgNormalText.setOnClickListener(this);
        mImgItalicText.setOnClickListener(this);
        mImgBoldText.setOnClickListener(this);
        mImgUnderLineText.setOnClickListener(this);
        mImgCenterLineText.setOnClickListener(this);

        mImgShadowDes.setOnClickListener(this);
        mImgShadowAsc.setOnClickListener(this);

        mImgNormalText.setSelected(true);
    }

    private void setDefaultColor() {
        mImgNormalText.setSelected(false);
        mImgBoldText.setSelected(false);
        mImgItalicText.setSelected(false);
        mImgUnderLineText.setSelected(false);
        mImgCenterLineText.setSelected(false);
    }

    private void setDefaultAlign() {
        mImgAlignLeft.setImageResource(R.drawable.ic_left_alignment);
        mImgAlignCenter.setImageResource(R.drawable.ic_center_alignment);
        mImgAlignRight.setImageResource(R.drawable.ic_right_alignment);
    }

    private void setDefaultSize() {
        mImgSizeDes.setImageResource(R.drawable.ic_des);
        mImgSizeAsc.setImageResource(R.drawable.ic_asc);
    }

    private void setDefaultShadow() {
        mImgShadowDes.setImageResource(R.drawable.ic_des);
        mImgShadowAsc.setImageResource(R.drawable.ic_asc);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLeftAlign:
                setDefaultAlign();
                mImgAlignLeft.setImageResource(R.drawable.ic_left_alignment_select);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeAlign(1);
                }
                break;
            case R.id.imgRightAlign:
                setDefaultAlign();
                mImgAlignRight.setImageResource(R.drawable.ic_right_alignment_select);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeAlign(2);
                }
                break;
            case R.id.imgCenterAlign:
                setDefaultAlign();
                mImgAlignCenter.setImageResource(R.drawable.ic_center_alignment_select);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeAlign(0);
                }
                break;
            case R.id.imgDes:
                setDefaultSize();
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeSize(-1);
                }
                break;
            case R.id.imgAsc:
                setDefaultSize();
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeSize(1);
                }
                break;
            case R.id.imgNormalText:
                setDefaultColor();
                mImgNormalText.setSelected(true);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeType(0);
                }
                break;
            case R.id.imgItalyText:
                setDefaultColor();
                mImgItalicText.setSelected(true);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeType(2);
                }
                break;
            case R.id.imgBoldText:
                setDefaultColor();
                mImgBoldText.setSelected(true);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeType(1);
                }
                break;
            case R.id.imgUnderLineText:
                setDefaultColor();
                mImgUnderLineText.setSelected(true);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeType(3);
                }
                break;
            case R.id.imgCenterLineText:
                setDefaultColor();
                mImgCenterLineText.setSelected(true);
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeType(4);
                }
                break;
            case R.id.imgDesShadow:
                setDefaultShadow();
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeShadow(-1);
                }
                break;
            case R.id.imgAscShadow:
                setDefaultShadow();
                if (mOnChangeAdjustmentTextListener != null) {
                    mOnChangeAdjustmentTextListener.onChangeShadow(1);
                }
                break;
            default:
                break;
        }
    }

    public void setMaxShadow(boolean max) {
        if (!max) {
            mImgShadowDes.setImageResource(R.drawable.ic_des_none);
        } else {
            mImgShadowAsc.setImageResource(R.drawable.ic_asc_none);
        }
    }


    public void setMaxSize(boolean max) {
        if (!max) {
            mImgSizeDes.setImageResource(R.drawable.ic_des_none);
        } else {
            mImgSizeAsc.setImageResource(R.drawable.ic_asc_none);
        }
    }
}
