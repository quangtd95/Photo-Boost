package com.quangtd.photoeditor.view.activity.gallery;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.quangtd.photoeditor.R;
import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.AlbumImage;
import com.quangtd.photoeditor.model.data.LocalImage;
import com.quangtd.photoeditor.presenter.PresenterListPhoto;
import com.quangtd.photoeditor.view.activity.ActivityBase;
import com.quangtd.photoeditor.view.activity.editphoto.ActivityEditPhoto_;
import com.quangtd.photoeditor.view.activity.output.ActivityOutput_;
import com.quangtd.photoeditor.view.component.GridSpacingItemDecoration;
import com.quangtd.photoeditor.view.iface.IViewListPhoto;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.quangtd.photoeditor.global.GlobalDefine.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

/**
 * QuangTD on 10/10/2017.
 */

@SuppressLint("Registered")
@EActivity(R.layout.activity_gallery)
public class ActivityGallery extends ActivityBase<PresenterListPhoto> implements FolderPhotoAdapter.OnClickItemFolderListener, PhotoAdapter.OnClickItemPhotoListener, IViewListPhoto {
    @ViewById(R.id.tvNameFolder)
    TextView mTvNameFolder;
    @ViewById(R.id.recyclerFolder)
    RecyclerView mRecyclerFolder;
    @ViewById(R.id.recyclerPhoto)
    RecyclerView mRecyclerPhoto;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private FolderPhotoAdapter mAdapterFolder;
    private PhotoAdapter mAdapterPhoto;
    private List<AlbumImage> mAlbumImages = new ArrayList<>();
    private List<LocalImage> mLocalImages = new ArrayList<>();
    private String myCurrentPhotoPath;

    @Override
    protected void init() {
        super.init();
        LinearLayoutManager mLayoutManagerFolder = new LinearLayoutManager(this);
        mAdapterFolder = new FolderPhotoAdapter(this, mAlbumImages);
        mRecyclerFolder.setLayoutManager(mLayoutManagerFolder);
        mRecyclerFolder.setAdapter(mAdapterFolder);

        mRecyclerFolder.setVisibility(View.VISIBLE);
        mRecyclerPhoto.setVisibility(View.GONE);

        GridLayoutManager mLayoutManagerPhoto = new GridLayoutManager(this, 3);
        mAdapterPhoto = new PhotoAdapter(this, mLocalImages);
        mRecyclerPhoto.setLayoutManager(mLayoutManagerPhoto);
        mRecyclerPhoto.setAdapter(mAdapterPhoto);
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(3, 10, false);
        mRecyclerPhoto.addItemDecoration(itemDecoration);

        mAdapterFolder.setOnClickItemFolderListener(this);
        mAdapterPhoto.setOnClickItemPhotoListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            getDummyFolder();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getDummyFolder();
                } else {
                    finish();
                }
                break;
            }
        }
    }

    private void getDummyFolder() {
        getPresenter(this).getListPhoto();
    }

    @Click(R.id.imgBack)
    void onClickBack() {
        finish();
    }

    @Click(R.id.imgSort)
    void onClickSort() {
        Collections.reverse(mLocalImages);
        mAdapterPhoto.notifyDataSetChanged();
        Toast.makeText(this, "Sort list photo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        onClickBack();
    }

    @Override
    public void onClickItem(int position) {
        mRecyclerPhoto.setVisibility(View.VISIBLE);
        mRecyclerFolder.setVisibility(View.GONE);
        mTvNameFolder.setText(mAlbumImages.get(position).getName());

        mLocalImages.clear();
        mLocalImages.addAll(mAlbumImages.get(position).getLocalImages());
        mAdapterPhoto.notifyDataSetChanged();
    }

    @Override
    public void onClickItemPhoto(int position) {
        ActivityOutput_.intent(this).extra(GlobalDefine.KEY_IMAGE, mLocalImages.get(position).getPath()).start();
        finish();
    }

    @Override
    public void getListPhotoSuccess(List<AlbumImage> albumImageList) {
        if (albumImageList != null) {
            for (AlbumImage albumImage : albumImageList) {
                if (albumImage.getName().equals(GlobalDefine.APP_NAME)) {
                    mAlbumImages.add(albumImage);
                }
            }
            mAdapterFolder.notifyDataSetChanged();
            if (mAlbumImages.size() > 0) {
                new Handler().postDelayed(() -> {
                    onClickItem(0);
                }, 500);

            }
        }

    }

    @Override
    public void getListPhotoFail(String message) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
