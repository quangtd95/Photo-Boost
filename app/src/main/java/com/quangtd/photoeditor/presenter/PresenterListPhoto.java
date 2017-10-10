package com.quangtd.photoeditor.presenter;

import com.quangtd.photoeditor.model.repository.ListPhotoRepository;
import com.quangtd.photoeditor.model.response.AlbumImage;
import com.quangtd.photoeditor.view.iface.IViewListPhoto;

import java.util.List;

/**
 * QuangTD on 10/10/2017.
 */

public class PresenterListPhoto extends PresenterBase<IViewListPhoto> {

    private ListPhotoRepository mListPhotoRepository;

    @Override
    public void onInit() {
        mListPhotoRepository = ListPhotoRepository.getInstance(getContext());
    }

    public void getListPhoto() {
        List<AlbumImage> albumImages = mListPhotoRepository.getAllImage(getContext());
        if (albumImages != null) {
            getIFace().getListPhotoSuccess(albumImages);
        } else {
            getIFace().getListPhotoFail("errror");
        }
    }
}
