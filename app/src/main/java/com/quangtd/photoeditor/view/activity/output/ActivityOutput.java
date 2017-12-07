package com.quangtd.photoeditor.view.activity.output;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.utils.FileUtils;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.choosephoto.ActivityListPhoto_;
import com.quangtd.photoeditor.view.activity.home.ActivityHome_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.OutputStream;

/**
 * Created by HuynhSang on 12/7/2017.
 */
@EActivity(R.layout.activity_output)
public class ActivityOutput extends ActivityBase {
    @ViewById(R.id.btn_linearShareNơw)
    LinearLayout btn_linearShareNow;
    @ViewById(R.id.btn_linearSetWallpaper)
    LinearLayout btn_linearSetWallpaper;
    @ViewById(R.id.btn_linearVideoMaker)
    LinearLayout btn_linearVideoMaker;
    @ViewById(R.id.tv_pathSaved)
    TextView tv_pathSaved;
    @ViewById(R.id.img_outPut)
    ImageView img_outPut;
    @ViewById(R.id.img_back)
    ImageView img_back;
    @Extra(GlobalDefine.KEY_IMAGE)
    String mImagePath;

    @AfterViews
    public void init() {
        FileUtils.reloadMedia(this, mImagePath);
        Glide.with(this).load(mImagePath).into(img_outPut);
        String result = getResources().getString(R.string.result);
        tv_pathSaved.setText(String.format(result, mImagePath));
    }

    @Click(R.id.btn_linearShareNơw)
    void onClickShareNow() {
        Bitmap mBitmap = BitmapFactory.decodeFile(mImagePath);
        Bitmap icon = mBitmap;
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);


        OutputStream outstream;
        try {
            outstream = getContentResolver().openOutputStream(uri);
            icon.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));
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

    @Override
    public void onBackPressed() {
        onClickBack();
    }
}
