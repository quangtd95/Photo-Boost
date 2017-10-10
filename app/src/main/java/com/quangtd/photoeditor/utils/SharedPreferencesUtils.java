package com.quangtd.photoeditor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by hainguyen on 8/24/17.
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils mInstance = null;
    private final String SHARE_PREFERENCE = "PhotoEditorSharePreference";
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public SharedPreferencesUtils() {
    }

    public static SharedPreferencesUtils getInstance(Context context) {
        Class var1 = SharedPreferencesUtils.class;
        synchronized (SharedPreferencesUtils.class) {
            if (mInstance == null) {
                mInstance = new SharedPreferencesUtils();
                mInstance.setContext(context);
                mInstance.setSharedPreferences();
            }

            return mInstance;
        }
    }

    public static synchronized SharedPreferencesUtils getInstance() {
        if (mInstance == null) {
            try {
                throw new Exception("The getInstance() function have to call after getInstance(Context context) function.");
            } catch (Exception var1) {
                var1.printStackTrace();
            }
        }

        return mInstance;
    }

    private void setSharedPreferences() {
        this.sharedPreferences = this.mContext.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE);
    }

    private void setContext(Context context) {
        this.mContext = context;
    }

    public boolean getBool(String key) {
        return this.sharedPreferences.getBoolean(key, false);
    }

    public float getFloat(String key) {
        return this.sharedPreferences.getFloat(key, -1.0F);
    }

    public int getInt(String key) {
        return this.sharedPreferences.getInt(key, -1);
    }

    public long getLong(String key) {
        return this.sharedPreferences.getLong(key, -1L);
    }

    public String getString(String key) {
        return this.sharedPreferences.getString(key, null);
    }

    public void setBool(String key, Boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value.booleanValue());
        editor.commit();
    }

    public void setFloat(String key, float value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setObject(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, json);
        editor.commit();
    }

    public Object getObject(String key, Class<?> object) {
        try {
            Gson e = new Gson();
            String json = this.sharedPreferences.getString(key, null);
            Object obj = e.fromJson(json, object);
            return obj;
        } catch (Exception var6) {
            return null;
        }
    }

    public boolean isExists(String key) {
        try {
            return this.sharedPreferences.getString(key, null) != null;
        } catch (Exception var3) {
            return true;
        }
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
