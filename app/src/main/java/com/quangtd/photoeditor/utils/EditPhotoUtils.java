package com.quangtd.photoeditor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import com.quangtd.photoeditor.global.GlobalDefine;
import com.quangtd.photoeditor.model.data.Decor;
import com.quangtd.photoeditor.model.data.Effect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * QuangTD on 10/19/2017.
 */

public class EditPhotoUtils {

    public static int WIDTH_PREVIEW;
    public static int HEIGHT_PREVIEW;

    public static String getSquareImage(String filePath, int widthScreen, int heightScreen) {
        Bitmap bitmap = decodeScaledBitmapFromSdCard(filePath, widthScreen, heightScreen);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap result = (w <= h) ? Bitmap.createBitmap(bitmap, 0, (h - w) / 2, w, w) : Bitmap.createBitmap(bitmap, (w - h) / 2, 0, h, h);
        return saveImage(Bitmap.createScaledBitmap(result, widthScreen, widthScreen, true));
    }

    private static Bitmap decodeScaledBitmapFromSdCard(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        ExifInterface exif;
        Matrix matrix = new Matrix();
        int rotate = 0;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
            matrix.setRotate(-rotate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (rotate == 0) return bitmap;
        else {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static String scaleBitmapFitScreen(String path, int width, int height) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bitmapOptions);
        int imageWidth = bitmapOptions.outWidth;
        int imageHeight = bitmapOptions.outHeight;
        float aX, aY;
        aX = width * 1.0f / imageWidth;
        aY = height * 1.0f / imageHeight;
        aX = (aX < aY) ? aX : aY;
        bitmapOptions.inJustDecodeBounds = false;
        bitmapOptions.inSampleSize = Math.round(aX);
        Bitmap tmp = BitmapFactory.decodeFile(path, bitmapOptions);
        Bitmap scaled = Bitmap.createScaledBitmap(tmp, (int) (imageWidth * aX), (int) (imageHeight * aX), true);
        return saveImage(scaled);
    }

    public static String editAndSaveImage(Bitmap bitmap, Effect effect, List<Decor> decors) {
        Bitmap edited = editImage(bitmap, effect, decors);
        return saveImage(edited);
    }

    private static Bitmap editImage(Bitmap bitmap, Effect effect, List<Decor> decors) {
        Bitmap mutableBitmap = convertToMutable(bitmap);
        Canvas canvas = new Canvas(mutableBitmap);
        if (effect != null) {
            Bitmap bmEffect = effect.getBitmap();
            //draw effect
            if (bmEffect != null) {
                Bitmap bmEffectScaled = Bitmap.createScaledBitmap(bmEffect, mutableBitmap.getWidth(), mutableBitmap.getHeight(), true);
                Paint paintEffect = new Paint(Paint.FILTER_BITMAP_FLAG);
                paintEffect.setAlpha(effect.getAlpha());
                canvas.drawBitmap(bmEffectScaled, (bitmap.getWidth() - bmEffectScaled.getWidth()) / 2, (bitmap.getHeight() - bmEffectScaled.getHeight()) / 2, paintEffect);
            }
        }
        if (decors != null) {
            //draw sticker
            for (int i = 0; i < decors.size(); i++) {
                Decor decor = decors.get(i);
                Matrix matrix = decor.getMatrix(bitmap.getWidth() * 1.0f / WIDTH_PREVIEW, bitmap.getHeight() * 1.0f / HEIGHT_PREVIEW);
                canvas.drawBitmap(decor.getBitmap(), matrix, decor.getPaint());
            }
        }
        return mutableBitmap;
    }

    private static String saveImage(Bitmap bitmap) {
        String fileName = GlobalDefine.OUTPUT_FOLDER + "Image_" + TimeUtils.parseTimeStampToString(System.currentTimeMillis()) + ".png";
        File dir = new File(GlobalDefine.OUTPUT_FOLDER);
        if (!dir.exists()) dir.mkdirs();
        try {
            OutputStream stream = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();
        } catch (IOException ex) {
            LogUtils.e(EditPhotoUtils.class.getSimpleName(), ex.toString());
            return null;
        }
        return fileName;
    }

    public static Bitmap convertToMutable(Bitmap imgIn) {
        try {
            //this is the file going to use temporally to save the bytes.
            // This file will not be a image, it will store the raw image data.
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

            //Open an RandomAccessFile
            //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
            //into AndroidManifest.xml file
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // get the width and height of the source bitmap.
            int width = imgIn.getWidth();
            int height = imgIn.getHeight();
            Bitmap.Config type = imgIn.getConfig();

            //Copy the byte to the file
            //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.getRowBytes() * height);
            imgIn.copyPixelsToBuffer(map);
            //recycle the source bitmap, this will be no longer used.
            imgIn.recycle();
            System.gc();// try to force the bytes from the imgIn to be released

            //Create a new bitmap to load the bitmap again. Probably the memory will be available.
            imgIn = Bitmap.createBitmap(width, height, type);
            map.position(0);
            //load it back from temporary
            imgIn.copyPixelsFromBuffer(map);
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();

            // delete the temp file
            file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgIn;
    }

    public static void reloadMedia(Context context) {
        MediaScannerConnection.scanFile(context,
                new String[]{Environment.getExternalStorageDirectory().toString()},
                null,
                (path, uri) -> {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                });
    }

   /* public static Bitmap blur(Context context, Bitmap image, int radius) {

    }*/
}
