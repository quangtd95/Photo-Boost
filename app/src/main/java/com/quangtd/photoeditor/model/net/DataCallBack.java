package com.quangtd.photoeditor.model.net;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * QuangTD on 8/24/17.
 */

public abstract class DataCallBack<T> implements Callback<T> {

    public abstract void onSuccess(T result);

    public abstract void onError(String message);

    @Override public void onResponse(Call<T> call, Response<T> response) {
        onSuccess(response.body());
    }

    @Override public void onFailure(Call<T> call, Throwable t) {
        onError(t.toString());
    }
}