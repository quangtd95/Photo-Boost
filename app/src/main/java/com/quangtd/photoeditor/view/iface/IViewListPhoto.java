package com.quangtd.photoeditor.view.iface;

import com.quangtd.photoeditor.model.data.AlbumImage;

import java.util.List;

/**
 * QuangTD on 10/10/2017.
 */

public interface IViewListPhoto extends IViewBase {
    void getListPhotoSuccess(List<AlbumImage> albumImageList);

    void getListPhotoFail(String message);
}
