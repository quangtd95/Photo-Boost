package com.quangtd.photoeditor.model.data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;

public class Decor implements Parcelable, Cloneable {
    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_STAMP = 1;
    private static final int TYPE_TEXT = 2;
    private Bitmap bitmap;
    private float x;
    private float y;
    private float width;
    private float height;
    @Getter
    private Paint paint;
    private int rotation;
    private int type;
    private float scale = 1;
    private float startTime;
    private float endTime;
    protected int alpha = 255;

    public Decor(Bitmap bitmap, float x, float y, float width, float height, Paint paint, int rotation, int type, float scale) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.paint = paint;
        this.rotation = rotation;
        this.type = type;
        this.scale = scale;
    }

    protected Decor(Parcel in) {
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        x = in.readFloat();
        y = in.readFloat();
        width = in.readFloat();
        height = in.readFloat();
        rotation = in.readInt();
        type = in.readInt();
        scale = in.readFloat();
        startTime = in.readFloat();
        endTime = in.readFloat();
        alpha = in.readInt();
    }

    public static final Creator<Decor> CREATOR = new Creator<Decor>() {
        @Override
        public Decor createFromParcel(Parcel in) {
            return new Decor(in);
        }

        @Override
        public Decor[] newArray(int size) {
            return new Decor[size];
        }
    };

    @Override public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }


    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public void setTime(int s, int e) {
        startTime = s;
        endTime = e;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }

    public void draw(Canvas canvas) {
        if (this.width == 0.0f || this.height == 0.0f) {
            this.width = (float) (this.bitmap.getWidth() / TYPE_TEXT);
            this.height = (float) (this.bitmap.getHeight() / TYPE_TEXT);
        }
        paint.setAlpha(alpha);
        canvas.drawBitmap(this.bitmap, getMatrix(), this.paint);
    }

    public Matrix getMatrix() {
        Matrix matrix = new Matrix();
        matrix.setScale(this.width / ((float) this.bitmap.getWidth()), this.height / ((float) this.bitmap.getHeight()));
        matrix.postRotate((float) this.rotation, this.width / 2.0f, this.height / 2.0f);
        matrix.postTranslate(this.x - (this.width / 2.0f), this.y - (this.height / 2.0f));
        return matrix;
    }

    public Matrix getMatrix(float arX, float arY) {
        Matrix matrix = new Matrix();
        matrix.setScale(this.width / ((float) this.bitmap.getWidth()) * arX, this.height / ((float) this.bitmap.getHeight()) * arY);
        matrix.postRotate((float) this.rotation, this.width / 2.0f * arX, this.height / 2.0f * arY);
        matrix.postTranslate((this.x - (this.width / 2.0f)) * arX, (this.y - (this.height / 2.0f)) * arY);
        return matrix;
    }

    public int getTouchType(Point touch) {
        Point[] points = getRectPoints();
        if (Math.sqrt((double) (((touch.x - points[TYPE_CAMERA].x) * (touch.x - points[TYPE_CAMERA].x)) + ((touch.y - points[TYPE_CAMERA].y) * (touch.y - points[TYPE_CAMERA].y)))) < ((double) 50)) {
            return 4;
        }
        if (Math.sqrt((double) (((touch.x - points[TYPE_TEXT].x) * (touch.x - points[TYPE_TEXT].x)) + ((touch.y - points[TYPE_TEXT].y) * (touch.y - points[TYPE_TEXT].y)))) < ((double) 50)) {
            return 3;
        }
        if (crossingNumber(points, touch)) {
            return TYPE_STAMP;
        }
        return TYPE_CAMERA;
    }

    //get 4 point of rectangle
    public Point[] getRectPoints() {
        Double radian = (((double) this.rotation) * 3.141592653589793d) / 180.0d;
        double coordinateX1 = ((double) (this.width / 2.0f)) * Math.cos(radian);
        double coordinateX2 = ((double) (this.height / 2.0f)) * Math.sin(radian);
        double coordinateY1 = ((double) (this.width / 2.0f)) * Math.sin(radian);
        double coordinateY2 = ((double) (this.height / 2.0f)) * Math.cos(radian);
        Point[] points = new Point[4];
        double d = (double) this.y;
        points[TYPE_CAMERA] = new Point((int) (((-coordinateX1) + coordinateX2) + ((double) this.x)), (int) (((-coordinateY1) - coordinateY2) + d));
        points[TYPE_STAMP] = new Point((int) ((coordinateX1 + coordinateX2) + ((double) this.x)), (int) ((coordinateY1 - coordinateY2) + ((double) this.y)));
        points[TYPE_TEXT] = new Point((int) ((coordinateX1 - coordinateX2) + ((double) this.x)), (int) ((coordinateY1 + coordinateY2) + ((double) this.y)));
        d = (double) this.y;
        points[3] = new Point((int) (((-coordinateX1) - coordinateX2) + ((double) this.x)), (int) (((-coordinateY1) + coordinateY2) + d));
        return points;

    }


    public void setChange(Point touch) {
        Point[] points = getRectPoints();
        double firstRotation = (getRadian((double) this.x, (double) this.y, (double) points[TYPE_TEXT].x, (double) points[TYPE_TEXT].y) * 180.0d) / 3.141592653589793d;
        this.rotation -= (int) (((getRadian((double) this.x, (double) this.y, (double) touch.x, (double) touch.y) * 180.0d) / 3.141592653589793d) - firstRotation);
        double scale = Math.sqrt((double) (((((float) touch.x) - this.x) * (((float) touch.x) - this.x)) + ((((float) touch.y) - this.y) * (((float) touch.y) - this.y)))) / (Math.sqrt((double) ((this.width * this.width) + (this.height * this.height))) / 2.0d);
        this.width *= (float) scale;
        this.height *= (float) scale;
        this.scale *= (float) scale;
    }

    public void setZoom(float lengthOld, float lengthNew) {
        double scale = lengthNew / lengthOld;
        this.width *= (float) scale;
        this.height *= (float) scale;
        this.scale *= (float) scale;
    }

    private double getRadian(double x, double y, double x2, double y2) {
        return Math.atan2(x2 - x, y2 - y);
    }

    private boolean crossingNumber(Point[] polygon, Point touch) {
        int cnt = TYPE_CAMERA;
        for (int i = TYPE_CAMERA; i < 4; i += TYPE_STAMP) {
            if (((polygon[(i + TYPE_STAMP) % 4].x - polygon[i].x) * (touch.y - polygon[i].y)) - ((touch.x - polygon[i].x) * (polygon[(i + TYPE_STAMP) % 4].y - polygon[i].y)) < 0) {
                cnt += TYPE_STAMP;
            } else {
                cnt--;
            }
        }
        return cnt == 4 || cnt == -4;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(bitmap, flags);
        dest.writeFloat(x);
        dest.writeFloat(y);
        dest.writeFloat(width);
        dest.writeFloat(height);
        dest.writeInt(rotation);
        dest.writeInt(type);
        dest.writeFloat(scale);
        dest.writeFloat(startTime);
        dest.writeFloat(endTime);
        dest.writeInt(alpha);
    }
}
