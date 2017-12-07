package com.quangtd.photoeditor.presenter;

import com.quangtd.photoeditor.model.data.AlbumImage;
import com.quangtd.photoeditor.model.repository.ListPhotoRepository;
import com.quangtd.photoeditor.view.iface.IViewListPhoto;

import java.util.List;

/**
 * Created by djwag on 12/8/2017.
 */

public class PresenterGallery extends PresenterBase<IViewListPhoto> {

    private ListPhotoRepository mListPhotoRepository;

    @Override
    public void onInit() {
        mListPhotoRepository = ListPhotoRepository.getInstance();
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