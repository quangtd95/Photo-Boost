package com.quangtd.photoeditor.view.activity.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.utils.SharedPreferencesUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.home.ActivityHome_;
import com.quangtd.photoeditor.view.activity.intro.ActivityIntro;

import org.androidannotations.annotations.EActivity;

/**
 * QuangTD on 10/6/17.
 */

@EActivity(R.layout.activity_splash)
public class ActivitySplash extends ActivityBase {

    private boolean isFirstOpenApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();
        setContentView(R.layout.activity_splash);
        isFirstOpenApp = SharedPreferencesUtils.getInstance(this).getBool(getString(R.string.key_save_first_open_app));
        Handler handler = new Handler();
        int DELAY_TIME = 700;
        handler.postDelayed(this::gotoIntroOrMainActivity, DELAY_TIME);
    }

    private void gotoIntroOrMainActivity() {
        if (isFirstOpenApp) {
            startActivity(ActivityHome_.class);
        } else {
            startActivity(ActivityIntro.class);
        }
        Log.e("TAGG", isFirstOpenApp + "");
        finish();
    }

    @Override protected void init() {

    }
}
