package com.quangtd.photoeditor.model.data;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Parcel;

/**
 * QuangTD on 6/24/2017.
 */

public class DecorText extends Decor {
    private String text;
    private int align;
    private int style;
    private int font;
    private int shadow;
    private int color = 0xFFFFFFFF;
    private int size = 72;
    private int[] rgb;

    public DecorText(Bitmap bitmap, float x, float y, float width, float height, Paint paint, int rotation, int type, float scale) {
        super(bitmap, x, y, width, height, paint, rotation, type, scale);
        rgb = new int[3];
    }


    protected DecorText(Parcel in) {
        super(in);
        text = in.readString();
        align = in.readInt();
        style = in.readInt();
        font = in.readInt();
        shadow = in.readInt();
        color = in.readInt();
        size = in.readInt();
        rgb = in.createIntArray();
    }

    public static final Creator<DecorText> CREATOR = new Creator<DecorText>() {
        @Override
        public DecorText createFromParcel(Parcel in) {
            return new DecorText(in);
        }

        @Override
        public DecorText[] newArray(int size) {
            return new DecorText[size];
        }
    };

    public void setColor(int color) {
        this.color = color;
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = color & 0xFF;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(text);
        dest.writeInt(align);
        dest.writeInt(style);
        dest.writeInt(font);
        dest.writeInt(shadow);
        dest.writeInt(color);
        dest.writeInt(size);
        dest.writeIntArray(rgb);
    }
}
