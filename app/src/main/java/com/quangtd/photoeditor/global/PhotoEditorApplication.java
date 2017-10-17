package com.quangtd.photoeditor.global;

import android.app.Application;

import com.quangtd.photoeditor.model.net.ApiClient;
import com.quangtd.photoeditor.model.net.ApiConfig;
import com.quangtd.photoeditor.model.net.ServerPath;

import io.realm.Realm;

/**
 * QuangTD on 10/5/2017.
 */

public class PhotoEditorApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        createService();
        createLocal();
    }

    private void createLocal() {
        Realm.init(this);
    }

    private void createService() {
        ApiConfig apiConfig = new ApiConfig(this, ServerPath.API_DOMAIN);
        ApiClient.getInstance().init(apiConfig);
    }
}
