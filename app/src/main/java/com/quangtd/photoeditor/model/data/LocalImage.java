package com.quangtd.photoeditor.model.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class LocalImage implements Parcelable {
    private Uri uri;
    private String path;
    private String name;
    private boolean select;

    protected LocalImage(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        path = in.readString();
        name = in.readString();
        select = in.readByte() != 0;
    }

    public static final Creator<LocalImage> CREATOR = new Creator<LocalImage>() {
        @Override
        public LocalImage createFromParcel(Parcel in) {
            return new LocalImage(in);
        }

        @Override
        public LocalImage[] newArray(int size) {
            return new LocalImage[size];
        }
    };


    public LocalImage() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeString(path);
        dest.writeString(name);
        dest.writeByte((byte) (select ? 1 : 0));
    }
}
