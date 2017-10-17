package com.quangtd.photoeditor.model.response;

/**
 * QuangTD on 8/24/17.
 */

public class BaseResponse {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
