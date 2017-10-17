package com.quangtd.photoeditor.model.net;


import com.quangtd.photoeditor.model.response.CategoryStickerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * QuangTD on 8/24/17.
 * * Define call request api
 */

public interface ApiService {
    @GET(ServerPath.API_GET_LIST_CATEGORY_STICKER)
    Call<CategoryStickerResponse> getListCategory();

}
