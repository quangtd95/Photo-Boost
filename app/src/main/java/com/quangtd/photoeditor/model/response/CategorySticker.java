package com.quangtd.photoeditor.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * QuangTD on 10/17/2017.
 */
@Data
public class CategorySticker extends RealmObject implements Parcelable {
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("folder_name")
    private String folderName;
    private boolean isSelected;
    @SerializedName("size")
    private int size;

    protected CategorySticker(Parcel in) {
        id = in.readInt();
        title = in.readString();
        thumbnail = in.readString();
        folderName = in.readString();
        isSelected = in.readByte() != 0;
        size = in.readInt();
    }

    public CategorySticker() {

    }

    public static final Creator<CategorySticker> CREATOR = new Creator<CategorySticker>() {
        @Override
        public CategorySticker createFromParcel(Parcel in) {
            return new CategorySticker(in);
        }

        @Override
        public CategorySticker[] newArray(int size) {
            return new CategorySticker[size];
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(folderName);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeInt(size);
    }
}
