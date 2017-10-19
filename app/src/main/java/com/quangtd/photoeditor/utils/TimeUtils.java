package com.quangtd.photoeditor.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * QuangTD on 10/19/2017.
 */

public class TimeUtils {
    public static String parseTimeStampToString(long timeStamp) {
        try {
            DateFormat sdf = new SimpleDateFormat("MM-dd-yyyy_hh-mm-ss", Locale.US);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            LogUtils.e(TimeUtils.class.getSimpleName(), ex.toString());
        }
        return "";
    }
}
