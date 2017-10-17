package com.quangtd.photoeditor.model.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * QuangTD on 10/17/2017.
 */
@Data
public class CategorySticker {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("folder_name")
    private String folderName;
    private boolean isSelected;
}
