package com.quangtd.photoeditor.utils;

import java.io.File;

public abstract class LogConfig {
    private boolean isDebugMode = true;
    private File backupPath;

    public LogConfig() {
    }

    public void setDebugMode(boolean isDebugMode) {
        this.isDebugMode = isDebugMode;
    }

    public boolean isDebugMode() {
        return this.isDebugMode;
    }

    public File getBackupPath() {
        return this.backupPath;
    }
}
