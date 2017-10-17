package com.quangtd.photoeditor.model.response;

import android.os.Parcelable;

/**
 * @author binhtt <binhjdev@gmail.com>
 * @version 1.0.0
 * @since 21/09/2017
 */

public abstract class BaseModel implements Parcelable {
    public String toString() {
        return this.getClass().getName();
    }

}
