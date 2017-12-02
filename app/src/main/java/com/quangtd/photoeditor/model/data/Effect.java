package com.quangtd.photoeditor.model.data;

import android.graphics.Bitmap;

import lombok.Data;

/**
 * QuangTD on 10/25/2017.
 */

@Data
public class Effect {
    private Bitmap bitmap;
    private int alpha;

    public Effect(Bitmap bitmap, int alpha) {
        this.bitmap = bitmap;
        this.alpha = alpha;
    }
    public Effect(){
        //no-op
    }
}
