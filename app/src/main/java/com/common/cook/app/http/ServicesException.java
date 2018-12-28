package com.common.cook.app.http;

import java.io.IOException;

/**
 */

public class ServicesException extends IOException {

    private int code;

    private String msg;

    public ServicesException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean needShowToast() {
        boolean show = true;
        switch (code) {
            case HttpResponseCode.ERROR_BUY_DIAMOND:
            case HttpResponseCode.ERROR_USER_DISABLE:
                //购买钻石(目前购买挂件余额不足，会返回该值)
                show = false;
                break;
        }
        return show;
    }
}
