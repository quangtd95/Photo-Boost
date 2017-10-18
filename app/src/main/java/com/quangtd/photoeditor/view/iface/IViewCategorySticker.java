package com.quangtd.photoeditor.view.iface;

import com.quangtd.photoeditor.model.response.CategorySticker;

import java.util.List;

/**
 * QuangTD on 10/17/2017.
 */

public interface IViewCategorySticker extends IViewBase {
    void getListCategorySuccess(List<CategorySticker> categoryStickers);

    void getListCategoryFail(String message);


}
