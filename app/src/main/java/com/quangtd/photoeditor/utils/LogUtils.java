package com.quangtd.photoeditor.utils;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogUtils {
    static LogConfig logConfig = new LogConfig() {
    };

    public LogUtils() {
    }

    public static void setLogConfig(LogConfig logConfig) {
        logConfig = logConfig;
    }

    public static LogConfig getLogConfig() {
        return logConfig;
    }

    public static void d(String tag, String msg) {
        if (logConfig.isDebugMode()) {
            Log.d(tag, msg);
        }

    }

    public static void d(String tag, String msg, Throwable tr) {
        if (logConfig.isDebugMode()) {
            Log.d(tag, msg, tr);
        }

    }

    public static void e(String tag, String msg) {
        if (logConfig.isDebugMode()) {
            Log.e(tag, msg);
        }

    }

    public static void e(String tag, String msg, Throwable tr) {
        if (logConfig.isDebugMode()) {
            Log.e(tag, msg, tr);
        }

    }

    public static void i(String tag, String msg) {
        if (logConfig.isDebugMode()) {
            Log.i(tag, msg);
        }

    }

    public static void i(String tag, String msg, Throwable tr) {
        if (logConfig.isDebugMode()) {
            Log.i(tag, msg, tr);
        }

    }

    public static void v(String tag, String msg) {
        if (logConfig.isDebugMode()) {
            Log.v(tag, msg);
        }

    }

    public static void v(String tag, String msg, Throwable tr) {
        if (logConfig.isDebugMode()) {
            Log.v(tag, msg, tr);
        }

    }

    public static void w(String tag, String msg) {
        if (logConfig.isDebugMode()) {
            Log.w(tag, msg);
        }

    }

    public static void w(String tag, String msg, Throwable tr) {
        if (logConfig.isDebugMode()) {
            Log.w(tag, msg, tr);
        }

    }

    public static void saveFile(String tag, String msg) {
        if (logConfig.getBackupPath() != null) {
            try {
                BufferedWriter e = new BufferedWriter(new FileWriter(logConfig.getBackupPath(), true));
                e.append("-----------------------\n" + tag + " : " + msg);
                e.newLine();
                e.close();
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }
}
