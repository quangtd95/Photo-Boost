package com.quangtd.photoeditor.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utilities for getting screen size or convert "dp" to "px".
 */
public final class ScreenUtils {
    private static final String TAG = ScreenUtils.class.getSimpleName();

    private ScreenUtils() {
        // no instance
    }

    /**
     * This method is used to get height of screen.
     *
     * @param context context
     * @return return height screen in pixel
     */
    public static int getHeightScreen(Context context) {
        return getScreenSize(context).getHeight();
    }

    /**
     * This method is used to get width of screen.
     *
     * @param context context
     * @return return width of screen in pixel
     */
    public static int getWidthScreen(Context context) {
        return getScreenSize(context).getWidth();
    }

    public static ScreenSize getScreenSize(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        // For JellyBean 4.2 (API 17) and onward
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            return new ScreenSize(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }

        Method getRawH;
        Method getRawW;
        try {
            getRawH = Display.class.getMethod("getRawHeight");
            getRawW = Display.class.getMethod("getRawWidth");
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException error: ", e);
            return new ScreenSize(0, 0);
        }

        try {
            return new ScreenSize((int) getRawW.invoke(display), (int) getRawH.invoke(display));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            Log.e(TAG, "error: ", e);
            return new ScreenSize(0, 0);
        }
    }

    /**
     * @param context is current context
     * @param spValue is value you want to convert for
     * @return return value in pixel
     */
    public static int convertSPtoPX(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return Math.round(spValue * fontScale);
    }

    /**
     * Convert dp to pixel
     *
     * @param dp      dp value
     * @param context content
     * @return return value in pixel
     */
    public static int convertDpToPixel(Context context, int dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * This method is used to get height of status bar
     *
     * @param context is current context
     * @return return height of status bar
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @SuppressLint("NewApi")
    public static int getSoftButtonsBarHeight(Activity activity) {
        // getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            DisplayMetrics metrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    /**
     * Class that manage the size of screen.
     */
    public static class ScreenSize {
        private int width;
        private int height;

        public ScreenSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
