package com.quangtd.photoeditor.model.net;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Use to create RestAdapter with options in order to request API.
 * Needs to call "init" in Application before using it.
 *
 * @author TienHN
 * @author ToanNS
 */

public final class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static ApiClient sInstance;
    private ApiService service;
    private Context context;
    private String baseUrl;


    public static ApiClient getInstance() {
        if (sInstance == null) {
            sInstance = new ApiClient();
        }
        return sInstance;
    }

    public static ApiService getService() {
        return getInstance().service;
    }


    public void init(ApiConfig apiConfig) {
        context = apiConfig.getContext();
        baseUrl = apiConfig.getBaseUrl();
        // init OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // init Service
        service = retrofit.create(ApiService.class);
    }
}
