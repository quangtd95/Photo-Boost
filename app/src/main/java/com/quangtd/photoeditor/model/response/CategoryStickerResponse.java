package com.quangtd.photoeditor.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

/**
 * QuangTD on 10/17/2017.
 */

@Data
public class CategoryStickerResponse {
    @SerializedName("size")
    private int size;
    @SerializedName("categories")
    private List<CategorySticker> categories;
}
