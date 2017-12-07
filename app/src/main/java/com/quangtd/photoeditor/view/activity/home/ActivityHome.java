package com.quangtd.photoeditor.view.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosephoto.ActivityListPhoto_;
import com.quangtd.photoeditor.view.activity.gallery.ActivityGallery;
import com.quangtd.photoeditor.view.activity.gallery.ActivityGallery_;
import com.quangtd.photoeditor.view.activity.output.ActivityOutput_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by HuynhSang on 12/6/2017.
 */
@EActivity(R.layout.activity_home)
public class ActivityHome extends ActivityBase {

    @ViewById(R.id.btn_linearPhotoEditor)
    LinearLayout btn_linearPhotoEditor;
    @ViewById(R.id.btn_linearCollage)
    LinearLayout btn_linearCollage;
    @ViewById(R.id.btn_linearGallery)
    LinearLayout btn_linearGallery;
    @ViewById(R.id.btn_linearPhotoFrames)
    LinearLayout btn_linearPhotoFrames;
    @ViewById(R.id.btn_linearTattoo)
    LinearLayout btn_linearTattoo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();
        setContentView(R.layout.activity_home);

    }

    @Click(R.id.btn_linearPhotoEditor)
    void onClickPhotoEditor() {
        ActivityListPhoto_.intent(this).start();
    }

    @Click(R.id.btn_linearGallery)
    void onClickGallery(){
        ActivityGallery_.intent(this).start();
    }
}
