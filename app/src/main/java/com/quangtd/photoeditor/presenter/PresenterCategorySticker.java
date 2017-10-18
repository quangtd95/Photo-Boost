package com.quangtd.photoeditor.presenter;

import com.quangtd.photoeditor.model.net.DataCallBack;
import com.quangtd.photoeditor.model.repository.ListCategoryStickerRepository;
import com.quangtd.photoeditor.model.response.CategoryStickerResponse;
import com.quangtd.photoeditor.view.iface.IViewCategorySticker;

/**
 * QuangTD on 10/17/2017.
 */

public class PresenterCategorySticker extends PresenterBase<IViewCategorySticker> {
    private final String TAG = getClass().getSimpleName();
    private ListCategoryStickerRepository mListCategoryStickerRepository;

    @Override
    public void onInit() {
        mListCategoryStickerRepository = ListCategoryStickerRepository.getInstance(getContext());
    }

    public void getListCategory() {
        mListCategoryStickerRepository.getListCategorySticker(new DataCallBack<CategoryStickerResponse>() {
            @Override public void onSuccess(CategoryStickerResponse result) {
                getIFace().getListCategorySuccess(result.getCategories());
            }

            @Override public void onError(String message) {
                getIFace().getListCategoryFail(message);
            }
        });
    }
}
