package com.quangtd.photoeditor.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by oanhdao on 5/3/2017.
 */

public class FolderSticker extends RealmObject implements Parcelable {
    @PrimaryKey
    @SerializedName("tab_name")
    private String name;
    @SerializedName("sticker_folder")
    private String folder;
    @SerializedName("tab_icon")
    private String icon;
    @SerializedName("total_image")
    private int total;
    private String path;
    @Ignore
    private boolean isSelect;

    public FolderSticker() {
        //no-op
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    protected FolderSticker(Parcel in) {
        path = in.readString();
        name = in.readString();
        folder = in.readString();
        icon = in.readString();
        total = in.readInt();
    }

    public static final Creator<FolderSticker> CREATOR = new Creator<FolderSticker>() {
        @Override
        public FolderSticker createFromParcel(Parcel in) {
            return new FolderSticker(in);
        }

        @Override
        public FolderSticker[] newArray(int size) {
            return new FolderSticker[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeString(folder);
        dest.writeString(icon);
        dest.writeInt(total);
    }
}
