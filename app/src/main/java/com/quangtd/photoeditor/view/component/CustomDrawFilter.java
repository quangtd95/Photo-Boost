package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * QuangTD on 10/24/2017.
 */

public class CustomDrawFilter extends View {
    private Bitmap mBitmap;
    private Paint mPaint;
    private int mAlpha;
    private int mWidthView;
    private int mHeightView;
    private int mWidthImage;
    private int mHeightImage;

    public CustomDrawFilter(Context context) {
        super(context);
        init();
    }

    public CustomDrawFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDrawFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        mAlpha = 100;
    }

    public void setResource(String path) {
        mBitmap = BitmapFactory.decodeFile(path);
        if (mBitmap == null) {
            mWidthImage = 0;
            mHeightImage = 0;
        } else {
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mWidthView, mHeightView, true);
            mWidthImage = mBitmap.getWidth();
            mHeightImage = mBitmap.getHeight();
        }
        invalidate();
    }

    public void clearFilter() {
        mBitmap = null;
        mWidthImage = 0;
        mHeightImage = 0;
        mAlpha = 100;
    }

    public void setAlpha(int alpha) {
        mAlpha = alpha;
        invalidate();
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidthView = getMeasuredWidth();
        mHeightView = getMeasuredHeight();
    }

    @Override protected void onDraw(Canvas canvas) {
        if (mBitmap == null) return;
        if (mWidthImage == 0 || mHeightImage == 0) return;
        mPaint.setAlpha(mAlpha);
        canvas.drawBitmap(mBitmap, (mWidthView - mWidthImage) / 2, (mHeightView - mHeightImage) / 2, mPaint);
    }
}
