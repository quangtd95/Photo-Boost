package com.quangtd.photoeditor.view.iface;

import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * QuangTD on 10/17/2017.
 */

public interface IViewListSticker extends IViewBase {
    void getListStickerSuccess(List<StorageReference> storageReferences);

    void getListStickerFailure(String message);

    void downloadStickerSuccess(String path);

    void downloadStickerFailure(String message);
}
