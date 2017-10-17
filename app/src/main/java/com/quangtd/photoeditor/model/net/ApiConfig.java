package com.quangtd.photoeditor.model.net;

import android.content.Context;


/**
 * A configure is used to create {@link ApiClient}.
 *
 * @author ToanNS
 */
public class ApiConfig {
    private Context context;
    private String baseUrl;

    public ApiConfig(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
