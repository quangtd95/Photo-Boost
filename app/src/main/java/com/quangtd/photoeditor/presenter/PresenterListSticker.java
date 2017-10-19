package com.quangtd.photoeditor.presenter;

import com.google.firebase.storage.StorageReference;
import com.quangtd.photoeditor.model.net.DataCallBack;
import com.quangtd.photoeditor.model.repository.ListStickerRepository;
import com.quangtd.photoeditor.model.data.CategorySticker;
import com.quangtd.photoeditor.view.iface.IViewListSticker;

import java.util.ArrayList;
import java.util.List;

/**
 * QuangTD on 10/17/2017.
 */

public class PresenterListSticker extends PresenterBase<IViewListSticker> {
    private ListStickerRepository mListStickerRepository;

    @Override public void onInit() {
        mListStickerRepository = ListStickerRepository.getInstance(getContext());
    }

    public void getListSticker(CategorySticker categorySticker) {
        List<StorageReference> mList = new ArrayList<>();
        for (int i = 1; i <= categorySticker.getSize(); i++) {
            String path = mListStickerRepository.convertStickerPath(categorySticker, i);
            mList.add(mListStickerRepository.getRefImageInCloud(path));
        }
        if (mList.size() > 0) {
            getIFace().getListStickerSuccess(mList);
        } else {
            getIFace().getListStickerFailure("error");
        }
    }

    public void downloadSticker(StorageReference storageReference) {
        getIFace().showLoading();
        mListStickerRepository.downloadSticker(storageReference, new DataCallBack<String>() {
            @Override public void onSuccess(String path) {
                getIFace().downloadStickerSuccess(path);
                getIFace().hideLoading();
            }

            @Override public void onError(String message) {
                getIFace().downloadStickerFailure(message);
                getIFace().hideLoading();
            }
        });
    }
}
