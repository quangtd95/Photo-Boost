package com.quangtd.photoeditor.utils;


import com.quangtd.photoeditor.R;

public class ImageResourceUtils {


    public static int selectImageResourceText(String name, boolean isSelect) {
        switch (name) {
            case "Font":
                return isSelect ? R.drawable.ic_font_text_select : R.drawable.ic_font_text;
            case "Color":
                return isSelect ? R.drawable.ic_color_text_select : R.drawable.ic_color_text;
            case "Setting":
                return isSelect ? R.drawable.ic_adjustment_text : R.drawable.ic_adjustment_text_un;
            default:
                return isSelect ? R.drawable.ic_font_text_select : R.drawable.ic_font_text;
        }
    }
}
