package com.quangtd.photoeditor.view.activity.output;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosephoto.ActivityListPhoto_;
import com.quangtd.photoeditor.view.activity.home.ActivityHome_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by HuynhSang on 12/7/2017.
 */
@EActivity(R.layout.activity_output)
public class ActivityOutput extends ActivityBase {
    @ViewById(R.id.btn_linearShareNơw) LinearLayout btn_linearShareNow;
    @ViewById(R.id.btn_linearSetWallpaper) LinearLayout btn_linearSetWallpaper;
    @ViewById(R.id.btn_linearVideoMaker) LinearLayout btn_linearVideoMaker;
    @ViewById(R.id.tv_pathSaved) TextView tv_pathSaved;
    @ViewById(R.id.img_outPut) ImageView img_outPut;
    @ViewById(R.id.img_back) ImageView img_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.init();
        setContentView(R.layout.activity_output);
        tv_pathSaved.setText("Photo saved in: \"/storage/emulated/0/Android/data/com.quangtd.photoeditor/files/Pictures/JPEG_20171207_023658_839894421.jpg\"");
    }

    @Click(R.id.btn_linearShareNơw)
    void onClickShareNow() {
        Toast.makeText(this, "comming soon!", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.btn_linearSetWallpaper)
    void onClickSetWallpaper() {
        Toast.makeText(this, "comming soon!", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.btn_linearVideoMaker)
    void onClickVideoMaker() {
        Toast.makeText(this, "comming soon!", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.img_back)
    void onClickBack() {
        ActivityHome_.intent(this).start();
        finish();
    }

    @Override public void onBackPressed() {
        onClickBack();
    }
}
