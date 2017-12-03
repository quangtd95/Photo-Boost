package com.quangtd.photoeditor.view.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.DecorText;
import com.quangtd.photoeditor.utils.EditPhotoUtils;
import com.quangtd.photoeditor.view.activity.edittext.OnHandlerItemListener;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class CustomDrawSticker extends View {

    @Accessors(prefix = "m")
    @Setter
    private OnHandlerItemListener mOnHandlerItemListener;

    public interface OnChangeItemStickerListener {
        void changeSticker(int oldP, int newP);
    }

    private static final float MENU_BUTTON_SIZE = 64.0f;
    private static final int TYPE_CHANGE = 3;
    private static final int TYPE_DELETE = 4;
    private static final int TYPE_MOVE = MotionEvent.ACTION_MOVE;
    private static final int TYPE_NONE = MotionEvent.ACTION_DOWN;
    private static final int TYPE_TAP = MotionEvent.ACTION_UP;
    @Accessors(prefix = "m")
    @Getter
    private List<Decor> mDecors;
    private Bitmap mDeleteBitmap;
    private int mFocusObjectIndex;
    private Paint mFramePaint;
    private Point mLastPoint;
    private Bitmap mOpenBitmap;
    private int mTouchType;

    private float mLengthOld;
    private boolean mIsMove = true;
    @Accessors(prefix = "m")
    @Getter
    private float mScale = 1;


    private DecorText mDecorText;

    @Accessors(prefix = "m")
    @Getter
    private float mDeltaY;
    private boolean mIsDraw;

    @Accessors(prefix = "m")
    @Getter
    private Bitmap mBitmapDeco;

    @Accessors(prefix = "m")
    @Setter
    private OnChangeItemStickerListener mOnChangeItemStickerListener;

    public void clearData() {
        mDecors.clear();
    }

    public void clearDataPosition() {
        if (mFocusObjectIndex != -1) {
            mDecors.remove(mFocusObjectIndex);
            mFocusObjectIndex--;
        }
    }

    public CustomDrawSticker(Context context) {
        this(context, null);
    }

    public CustomDrawSticker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDrawSticker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mTouchType = TYPE_NONE;
        if (mDecors == null) {
            mDecors = new ArrayList<>();
        }
        this.mFocusObjectIndex = -1;
        this.mLastPoint = null;
        initialize();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        EditPhotoUtils.WIDTH_PREVIEW = getMeasuredWidth();
        EditPhotoUtils.HEIGHT_PREVIEW = getMeasuredHeight();
    }

    private void initialize() {
        Resources resources = getResources();
        float destiny = resources.getDisplayMetrics().density * 2;
        this.mFramePaint = new Paint();
        this.mFramePaint.setStrokeWidth(destiny);
        this.mFramePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        this.mOpenBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_btn_rotation);
        this.mDeleteBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_btn_delete);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIsDraw = true;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsDraw) {
            drawCake(canvas);
        }
    }

    private void decorNoBorder() {
        mBitmapDeco = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmapDeco);
        canvas.drawColor(Color.WHITE);
        for (Decor mDecor : mDecors) {
            (mDecor).draw(canvas);
        }
    }

    private void drawCake(Canvas canvas) {
        for (Decor mDecor : mDecors) {
            mDecor.draw(canvas);
        }
        if (this.mFocusObjectIndex >= 0 && this.mFocusObjectIndex < mDecors.size()) {
            drawFrame(canvas, mDecors.get(this.mFocusObjectIndex));
        }
    }

    private long mTimeCurrent;

    public boolean onTouchEvent(MotionEvent event) {
        Point touchPoint = new Point((int) event.getX(), (int) event.getY());
        judgeTouchPoint(touchPoint);
        if (mOnHandlerItemListener != null) {
            mOnHandlerItemListener.touchItem();
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                long mTime = System.currentTimeMillis();
                Log.d("ttt", "ACTION_DOWN: " + mTime);
                if (this.mTouchType == TYPE_DELETE) {
                    if (mDecors.get(this.mFocusObjectIndex).getTouchType(touchPoint) == TYPE_DELETE) {
                        if (mFocusObjectIndex != -1 && mDecors.get(mFocusObjectIndex).getType() == 0) {
                            if (mOnHandlerItemListener != null) {
                                mOnHandlerItemListener.onDeleteItem();
                            }
                        }
                        mDecors.remove(this.mFocusObjectIndex);
                        mFocusObjectIndex--;
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!mDecors.isEmpty()) {
                    if (event.getPointerCount() == 2) {
                        float[] lastEvent = new float[4];
                        lastEvent[0] = event.getX(0);
                        lastEvent[1] = event.getX(1);
                        lastEvent[2] = event.getY(0);
                        lastEvent[3] = event.getY(1);
                        mLengthOld = lengthTwoPoint(lastEvent[0], lastEvent[2], lastEvent[1], lastEvent[3]);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) {
                    Handler handler = new Handler();
                    handler.postDelayed(() -> mIsMove = true, 500);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - mTimeCurrent < 500) {
                    mTimeCurrent = System.currentTimeMillis();
                    return true;
                }
                mTimeCurrent = System.currentTimeMillis();

                if (!mDecors.isEmpty()) {
                    if (mTouchType == TYPE_NONE || mTouchType == TYPE_TAP) {
                        if (mFocusObjectIndex != -1 && mDecors.get(mFocusObjectIndex).getType() == 0) {
                            if (System.currentTimeMillis() - mTimeCurrent < 300) {
                                if (mOnHandlerItemListener != null && !mIsMove) {
                                    mOnHandlerItemListener.clickItem();
                                }
                            }
                        }
                    } else if (mFocusObjectIndex >= 0 && mFocusObjectIndex < mDecors.size() && mDecors.get(mFocusObjectIndex).getType() == 2) {
                        Decor decor = mDecors.get(mFocusObjectIndex);
                    }
                    mIsMove = true;
                    this.mTouchType = TYPE_NONE;
                    break;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (!mDecors.isEmpty()) {
                    if (event.getPointerCount() == 2) {
                        float[] lastEvent = new float[4];
                        lastEvent[0] = event.getX(0);
                        lastEvent[1] = event.getX(1);
                        lastEvent[2] = event.getY(0);
                        lastEvent[3] = event.getY(1);
                        float mLengthNew = lengthTwoPoint(lastEvent[0], lastEvent[2], lastEvent[1], lastEvent[3]);
                        mIsMove = false;
                        if (mFocusObjectIndex != -1) {
                            Decor focusDecor = mDecors.get(this.mFocusObjectIndex);
                            focusDecor.setZoom(mLengthOld, mLengthNew);
                            mLengthOld = mLengthNew;
                            if (mOnHandlerItemListener != null) {
                                mOnHandlerItemListener.onZoomItem();
                            }
                        }
                        break;
                    } else if (this.mTouchType == TYPE_CHANGE) {
                        if (mFocusObjectIndex != -1) {
                            mDecors.get(this.mFocusObjectIndex).setChange(touchPoint);
                            if (mOnHandlerItemListener != null) {
                                mOnHandlerItemListener.onZoomItem();
                            }
                            break;
                        }
                    } else if (mIsMove && mFocusObjectIndex != -1 && mFocusObjectIndex < mDecors.size()) {
                        Decor focusDecor = mDecors.get(this.mFocusObjectIndex);
                        float deltaX = focusDecor.getX() + (float) (touchPoint.x - this.mLastPoint.x);
                        float deltaY = focusDecor.getY() + (float) (touchPoint.y - this.mLastPoint.y);
                        focusDecor.setX(deltaX);
                        focusDecor.setY(deltaY);
                        mDecors.set(this.mFocusObjectIndex, focusDecor);
                    }
                    break;
                }
                break;
            default:
                break;
        }
        this.mLastPoint = touchPoint;
        invalidate();
        return true;
    }


    private void judgeTouchPoint(Point touch) {
        this.mTouchType = TYPE_NONE;
        for (int i = mDecors.size() - 1; i >= 0; i--) {
            int touchType = mDecors.get(i).getTouchType(touch);

            if (touchType != 0) {
                if (this.mFocusObjectIndex == i) {
                    this.mTouchType = touchType;
                } else {
                    this.mTouchType = TYPE_MOVE;
                }
                if (mFocusObjectIndex != i) {
                    if (mOnChangeItemStickerListener != null) {
                        mOnChangeItemStickerListener.changeSticker(mFocusObjectIndex, i);
                    }
                }
                this.mFocusObjectIndex = i;
                return;
            }
            if (this.mFocusObjectIndex == i) {
                if (mOnChangeItemStickerListener != null) {
                    mOnChangeItemStickerListener.changeSticker(mFocusObjectIndex, -1);
                }
                this.mFocusObjectIndex = -1;
            }
        }
    }


    private void drawFrame(Canvas canvas, Decor decor) {
        Point[] points = decor.getRectPoints();
        for (int i = TYPE_NONE; i < points.length; i += TYPE_TAP) {
            canvas.drawLine((float) points[i].x, (float) points[i].y, (float) points[(i + TYPE_TAP) % points.length].x, (float) points[(i + TYPE_TAP) % points.length].y, this.mFramePaint);
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Matrix deleteMatrix = new Matrix();
        deleteMatrix.setScale(MENU_BUTTON_SIZE / ((float) this.mDeleteBitmap.getWidth()), MENU_BUTTON_SIZE / ((float) this.mDeleteBitmap.getHeight()));
        deleteMatrix.postRotate((float) decor.getRotation(), MENU_BUTTON_SIZE / 2f, MENU_BUTTON_SIZE / 2);
        deleteMatrix.postTranslate(((float) points[TYPE_NONE].x) - MENU_BUTTON_SIZE / 2, ((float) points[TYPE_NONE].y) - MENU_BUTTON_SIZE / 2);
        canvas.drawBitmap(this.mDeleteBitmap, deleteMatrix, paint);
        Matrix openMatrix = new Matrix();
        openMatrix.setScale(MENU_BUTTON_SIZE / ((float) this.mOpenBitmap.getWidth()), MENU_BUTTON_SIZE / ((float) this.mOpenBitmap.getHeight()));
        openMatrix.postRotate((float) decor.getRotation(), MENU_BUTTON_SIZE / 2, MENU_BUTTON_SIZE / 2);
        openMatrix.postTranslate(((float) points[TYPE_MOVE].x) - MENU_BUTTON_SIZE / 2, ((float) points[TYPE_MOVE].y) - MENU_BUTTON_SIZE / 2);
        canvas.drawBitmap(this.mOpenBitmap, openMatrix, paint);
    }

    private Matrix getMatrix(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        int height = getHeight();
        int width = getWidth();
        int heightBitmap = bitmap.getHeight();
        int widthBitmap = bitmap.getWidth();
        float scale;
        scale = 1.3f * Math.max((float) width / (float) widthBitmap, (float) height / (float) heightBitmap);
        mScale = scale;
        mDeltaY = ((getHeight() - bitmap.getHeight() * scale) / (2f * scale) - (getHeight() - bitmap.getHeight() * scale) / (1.2f * scale)) * 1.3f;
        matrix.preScale(scale, scale);
        matrix.preTranslate((getWidth() - bitmap.getWidth() * scale) / (2.0f * scale), (getHeight() - bitmap.getHeight() * scale) / (1.2f * scale));
        return matrix;
    }

    private float lengthTwoPoint(float x0, float y0, float x1, float y1) {
        double length = Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        return (float) length;
    }

    public void addDecoItem(Bitmap bitmap, int type) {
        float scale = 1.0f;
        if (Math.max(bitmap.getWidth(), bitmap.getHeight()) >= Math.max(getWidth() / 2, getHeight() / 2)) {
            scale = ((float) Math.max(getWidth() / 2, getHeight() / 2)) / ((float) Math.max(bitmap.getWidth(), bitmap.getHeight()));
        }
        addDecoItem(new Decor(bitmap, getWidth() / 2, getHeight() / 2, bitmap.getWidth() * scale, bitmap.getHeight() * scale, new Paint(Paint.FILTER_BITMAP_FLAG), TYPE_NONE, type, scale));
    }

    public void addDecoItemText(Bitmap bitmap, int type, String text) {
        float scale = 1.0f;
        mDecorText = new DecorText(bitmap, getWidth() / 2, getHeight() / 2, bitmap.getWidth() * scale, bitmap.getHeight() * scale, new Paint(), TYPE_NONE, type, scale);
        mDecorText.setText(text);
        addDecoItem(mDecorText);
    }

    public void addDecoItem(Decor decor) {
        mFocusObjectIndex++;
        if (mDecors != null) {
            mDecors.add(decor);
        }
        invalidate();
    }

    public int getDecorCount() {
        return mDecors.size();
    }

    public void removeFrame() {
        this.mFocusObjectIndex = -1;
        invalidate();
    }

    public Decor getFocusDecor() {
        if (mFocusObjectIndex == -1) return null;
        return mDecors.get(mFocusObjectIndex);
    }
}
