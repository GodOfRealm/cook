package com.common.cook.core.notch;

import android.app.Activity;
import android.provider.Settings;


/**
 * Desc: MIUI刘海屏信息
 * <p>
 * Author: SonnyJack
 * Date: 2018-08-30
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class MIUINotch implements INotchInfo {

    //https://dev.mi.com/console/doc/detail?pId=1293

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     */
    public MIUINotch(Activity activity) {
        if (null == activity) {
            throw new NullPointerException("activity is null");
        }
        mActivity = activity;
    }

    @Override
    public boolean hasNotchScreen() {
        //1是有刘海屏
        String value = OSUtil.getSystemProperty("ro.miui.notch", "0");
        return "1".equals(value);
    }

    @Override
    public boolean hasOpenNotch() {
        //1的时候是隐藏刘海屏
        return !(Settings.Global.getInt(mActivity.getContentResolver(), "force_black", 0) == 1);
    }

    @Override
    public int[] getNotchSize() {
        int[] size = {0, 0};
        if (OSUtil.isMIUINumLater(10)) {//MIUI10以后提供获取方法
            //宽度
            int resourceId = mActivity.getResources().getIdentifier("notch_width", "dimen", "android");
            if (resourceId > 0) {
                size[0] = (mActivity.getResources().getDimensionPixelSize(resourceId));
            }
            //高度
            resourceId = mActivity.getResources().getIdentifier("notch_height", "dimen", "android");
            if (resourceId > 0) {
                size[1] = mActivity.getResources().getDimensionPixelSize(resourceId);
            }
        } else {
            //小米8的尺寸
            size[0] = 560;
            size[1] = 89;
        }
        return size;
    }
}
