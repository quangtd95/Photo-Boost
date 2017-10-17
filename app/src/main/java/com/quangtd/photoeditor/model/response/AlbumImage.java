package com.quangtd.photoeditor.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AlbumImage implements Parcelable {
    private List<LocalImage> localImages;
    private String name;
    private String path;

    public AlbumImage(List<LocalImage> localImages, String name) {
        this.localImages = localImages;
        this.name = name;
    }

    protected AlbumImage(Parcel in) {
        localImages = in.createTypedArrayList(LocalImage.CREATOR);
        name = in.readString();
        path = in.readString();
    }

    public static final Creator<AlbumImage> CREATOR = new Creator<AlbumImage>() {
        @Override
        public AlbumImage createFromParcel(Parcel in) {
            return new AlbumImage(in);
        }

        @Override
        public AlbumImage[] newArray(int size) {
            return new AlbumImage[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<LocalImage> getLocalImages() {
        return localImages;
    }

    public String getName() {
        return name;
    }

    public String getFirstImage() {
        if (localImages != null && !localImages.isEmpty()) {
            return localImages.get(0).getPath();
        }
        return null;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(localImages);
        dest.writeString(name);
        dest.writeString(path);
    }
}
