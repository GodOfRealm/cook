package com.libalum.shortvideo;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 剪辑dialog
 */
public class ShortVideoProgressDialog extends ProgressDialog {
    public ShortVideoProgressDialog(Context context) {
        super(context);
        setMessage("处理中...");
        setMax(100);
        setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }
}
