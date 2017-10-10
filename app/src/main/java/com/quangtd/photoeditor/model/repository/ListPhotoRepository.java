package com.quangtd.photoeditor.model.repository;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;

import com.quangtd.photoeditor.model.response.AlbumImage;
import com.quangtd.photoeditor.model.response.LocalImage;

import java.util.ArrayList;
import java.util.List;

/**
 * QuangTD on 10/10/2017.
 */

public class ListPhotoRepository {
    private static ListPhotoRepository mInstance;

    private Context mContext;

    private ListPhotoRepository(Context context) {
        mInstance = this;
        this.mContext = context;
    }

    public static synchronized ListPhotoRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ListPhotoRepository(context);
        }
        return mInstance;
    }

    public List<AlbumImage> getAllImage(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        List<LocalImage> mLocalImages = new ArrayList<>();
        List<AlbumImage> mList = new ArrayList<>();

        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID + " DESC";
        Cursor cc = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        assert cc != null;
        cc.moveToFirst();
        for (int i = 0; i < cc.getCount(); i++) {
            cc.moveToPosition(i);
            int id = cc.getInt(cc.getColumnIndex(MediaStore.MediaColumns._ID));

            Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
            String name = cc.getString(1);
            String path = cc.getString(0);

            LocalImage localImage = new LocalImage();
            localImage.setUri(uri);
            localImage.setPath(path);
            localImage.setName(name);
            mLocalImages.add(localImage);
        }
        cc.close();
        mList.addAll(getAlbumImage(mLocalImages));
        return mList;
    }

    private List<AlbumImage> getAlbumImage(List<LocalImage> localImages) {
        List<AlbumImage> albumImages = new ArrayList<>();

        for (LocalImage localImage : localImages) {
            if (!checkAlbum(albumImages, localImage)) {
                List<LocalImage> localImagesAlbum = new ArrayList<>();
                localImagesAlbum.add(localImage);
                AlbumImage albumImage = new AlbumImage(localImagesAlbum, getAlbumName(localImage.getPath()));
                albumImage.setPath(getPathAlbum(localImage.getPath()));
                albumImages.add(albumImage);
            }
        }
        return albumImages;
    }

    private boolean checkAlbum(List<AlbumImage> albumImages, LocalImage localImage) {
        boolean check = false;
        for (int i = 0; i < albumImages.size(); i++) {
            if (getAlbumName(localImage.getPath()).equals(albumImages.get(i).getName())) {
                check = true;
                albumImages.get(i).getLocalImages().add(localImage);
                break;
            }
            check = false;
        }
        return check;
    }

    private String getAlbumName(String path) {
        String[] names = path.split("/");
        return names[names.length - 2];
    }

    private String getPathAlbum(String path) {
        String[] names = path.split("/");
        return path.substring(0, path.length() - names[names.length - 1].length());
    }


}
