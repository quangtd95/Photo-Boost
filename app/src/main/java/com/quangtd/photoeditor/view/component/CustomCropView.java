package com.quangtd.photoeditor.view.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by HuynhSang on 12/7/2017.
 */

public class CustomCropView extends View {
    Paint paint;
    float x1, y1, x2, y2;
    float cloneX1, cloneX2, cloneY1, cloneY2;
    float currentTouch_dx, currentTouch_dy, currentRectWidth, currentRectHeigh;
    float sizeWidth, sizeHeigh;
    int [] zoomFollow = {0, 0};
    boolean isTouchedInside = false, showGrid = false;

    public CustomCropView(Context context) {
        super(context);
        init();
    }

    public CustomCropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomCropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.sizeHeigh = this.getHeight();
        this.sizeWidth = this.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setAlpha(200);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, this.getWidth(), this.getHeight(), paint);
        RectF rect = new RectF(x1, y1, x2, y2);
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, paint);

        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
        paint.setXfermode(null);

        if (showGrid) {
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(2);
            canvas.drawLine(x1+currentRectWidth/3, y1, x1+currentRectWidth/3, y2, paint);
            canvas.drawLine(x1+currentRectWidth*2/3, y1, x1+currentRectWidth*2/3, y2, paint);
            canvas.drawLine(x1, y1+currentRectHeigh/3, x2, y1+currentRectHeigh/3, paint);
            canvas.drawLine(x1, y1+currentRectHeigh*2/3, x2, y1+currentRectHeigh*2/3, paint);
        }
    }

    private void init() {
        paint = new Paint();
        setRectangle(200, 500, 500, 1000);
    }

    private void setRectangle(float x1, float y1, float x2, float y2) {
        this.x1 = this.cloneX1 = x1;
        this.y1 = this.cloneY1 = y1;
        this.x2 = this.cloneX2 = x2;
        this.y2 = this.cloneY2 = y2;
        currentRectWidth = x2 - x1;
        currentRectHeigh = y2 - y1;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentTouch_dx = event.getX() - x1;
                currentTouch_dy = event.getY() - y1;
                checkTouchToZoom();
                isTouchedInside = checkTouchInside();
                break;
            case MotionEvent.ACTION_UP:
                zoomFollow[0] = zoomFollow[1] = 0;
                isTouchedInside = false;
                showGrid = false;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (zoomFollow[0] != 0 || zoomFollow[1] != 0) {
                    zoom(event.getX(), event.getY());
                    break;
                } else if (isTouchedInside) {
                    moveRectangle(event.getX(), event.getY());
                    break;
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean checkTouchInside() {
        if ((0 < currentTouch_dx && currentTouch_dx < currentRectWidth) && (0 < currentTouch_dy && currentTouch_dy < currentRectHeigh)) {
            showGrid = true;
            return true;
        }
        return false;
    }

    private void moveRectangle(float endTouch_dx, float endTouch_dy) {
        cloneX1 = endTouch_dx - currentTouch_dx;
        cloneY1 = endTouch_dy - currentTouch_dy;
        x1 = (cloneX1<0 || cloneX1 + currentRectWidth > sizeWidth)? x1: cloneX1;
        y1 = (cloneY1<0 || cloneY1 + currentRectHeigh > sizeHeigh)? y1: cloneY1;
        x2 = cloneX2 = x1 + currentRectWidth;
        y2 = cloneY2 = y1 + currentRectHeigh;
        setRectangle(x1, y1, x2, y2);
        this.invalidate();
    }

    private void checkTouchToZoom() {
        if (Math.abs(currentTouch_dx) <= 30) {
            zoomFollow[0] = 1;
        } else if (currentTouch_dx >= currentRectWidth - 30 && currentTouch_dx <= currentRectWidth+30) {
            zoomFollow[0] = 2;
        }
        if (Math.abs(currentTouch_dy) <= 30) {
            zoomFollow[1] = 1;
        } else if (currentTouch_dy >= currentRectHeigh - 30 && currentTouch_dy <= currentRectHeigh+30) {
            zoomFollow[1] = 2;
        }
        showGrid = ((zoomFollow[0] != 0 || zoomFollow[1] != 0)? true : false);
    }


    private void zoom(float endTouch_dx, float endTouch_dy) {
        if (zoomFollow[0] == 1) {
            cloneX1 = endTouch_dx;
        }else if (zoomFollow[0] == 2) {
            cloneX2 = endTouch_dx;
        }
        if (zoomFollow[1] == 1) {
            cloneY1 = endTouch_dy;
        } else if (zoomFollow[1] == 2) {
            cloneY2 = endTouch_dy;
        }
        if (cloneX2 - cloneX1 > 100 && cloneY2 - cloneY1 > 100) {
            setRectangle(cloneX1, cloneY1, cloneX2, cloneY2);
            this.invalidate();
            return;
        }
        setRectangle(x1, y1, x2, y2);
    }

    public Bitmap getNewImage(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap,(int) x1,(int) y1,(int) currentRectWidth,(int) currentRectHeigh);
    }
}
