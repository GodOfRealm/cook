package com.common.cook.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.common.cook.app.PickApplication;

/**
 * 作者: zws 2018/11/1 0001 15:28
 * 功能描述:
 * 备注:
 */
public class CountTextView extends TextView {

    public CountTextView(Context context) {
        super(context);
       //设置字体
        setTypeface(PickApplication.getInstance().getTypeface());
    }


    public CountTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置字体
        setTypeface(PickApplication.getInstance().getTypeface());
    }


    public CountTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置字体
        setTypeface(PickApplication.getInstance().getTypeface());
    }
}
