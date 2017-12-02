package com.quangtd.photoeditor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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

    public static String scaleBitmapFitScreen(Bitmap bitmap, int width, int height) {
        float aX = 1, aY = 1;
        if (bitmap.getWidth() < width) {
            aX = width * 1.0f / bitmap.getWidth();
        }
        if (bitmap.getHeight() < height) {
            aY = height * 1.0f / bitmap.getHeight();
        }
        aX = (aX < aY) ? aX : aY;
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * aX), (int) (bitmap.getHeight() * aX), true);
        return saveImage(scaled);
    }

    public static String editAndSaveImage(Bitmap bitmap, Effect effect, List<Decor> decors) {
        Bitmap edited = editImage(bitmap, effect, decors);
        return saveImage(edited);
    }

    private static Bitmap editImage(Bitmap bitmap, Effect effect, List<Decor> decors) {
        Bitmap mutableBitmap = convertToMutable(bitmap);
        Canvas canvas = new Canvas(mutableBitmap);
        Bitmap bmEffect = effect.getBitmap();
        //draw effect
        if (bmEffect != null) {
            Bitmap bmEffectScaled = Bitmap.createScaledBitmap(bmEffect, mutableBitmap.getWidth(), mutableBitmap.getHeight(), true);
            Paint paintEffect = new Paint(Paint.FILTER_BITMAP_FLAG);
            paintEffect.setAlpha(effect.getAlpha());
            canvas.drawBitmap(bmEffectScaled, (bitmap.getWidth() - bmEffectScaled.getWidth()) / 2, (bitmap.getHeight() - bmEffectScaled.getHeight()) / 2, paintEffect);
        }
        //draw sticker
        for (int i = 0; i < decors.size(); i++) {
            Decor decor = decors.get(i);
            Matrix matrix = decor.getMatrix(bitmap.getWidth() * 1.0f / WIDTH_PREVIEW, bitmap.getHeight() * 1.0f / HEIGHT_PREVIEW);
            canvas.drawBitmap(decor.getBitmap(), matrix, decor.getPaint());
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
}
