package com.miguan.youmi.core.notch;

import android.app.Activity;
import android.text.TextUtils;

/**
 * Created by SonnyJack on 2018/8/30 15:13.
 */
public class OPPONotch implements INotchInfo {

    //https://open.oppomobile.com/service/message/detail?id=61876

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     */
    public OPPONotch(Activity activity) {
        if (null == activity) {
            throw new NullPointerException("activity is null");
        }
        mActivity = activity;
    }

    @Override
    public boolean hasNotchScreen() {
        return mActivity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    @Override
    public boolean hasOpenNotch() {
        return hasNotchScreen();
    }

    @Override
    public int[] getNotchSize() {
        int[] result = {0, 0};
        String value = OSUtil.getSystemProperty("ro.oppo.screen.heteromorphism", "");
        //[378,0:702,80]，含义如下 ：
        //378：表示竖屏下左上角横坐标
        //0：表示竖屏下左上角竖坐标
        //702：表示竖屏下右下角横坐标
        //80 ：表示竖屏下右下角竖坐标
        if (!TextUtils.isEmpty(value)) {
            try {
                value = value.substring(1, value.length() - 1);
                String[] temp = value.split(":");
                String[] temp1 = temp[0].split(",");
                int left = Integer.parseInt(temp1[0]);
                int top = Integer.parseInt(temp1[1]);
                temp1 = temp[1].split(",");
                int right = Integer.parseInt(temp1[0]);
                int bottom = Integer.parseInt(temp1[1]);
                result[0] = right - left;
                result[1] = bottom - top;
            } catch (Exception e) {
                result[0] = 0;
                result[1] = 0;
            }
        }
        return result;
    }
}
