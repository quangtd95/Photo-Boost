package com.quangtd.photoeditor.utils;

import java.io.File;

/**
 * QuangTD on 12/5/2017.
 */

public class FileUtils {
    public static void deleteFolder(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }
}
