package com.common.cook.core.notch;

import android.app.Activity;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;


/**
 * Desc: EMUI刘海屏信息
 * <p>
 * Author: SonnyJack
 * Date: 2018-08-30
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class EMUINotch implements INotchInfo {

    //https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     */
    public EMUINotch(Activity activity) {
        if (null == activity) {
            throw new NullPointerException("activity is null");
        }
        mActivity = activity;
    }

    @Override
    public boolean hasNotchScreen() {
        boolean ret = false;
        try {
            ClassLoader cl = mActivity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }

    @Override
    public boolean hasOpenNotch() {
        // 0表示“默认”，1表示“隐藏显示区域”
        int isNotchSwitchOpen = Settings.Secure.getInt(mActivity.getContentResolver(), "display_notch_status", 0);
        return isNotchSwitchOpen == 0;
    }

    @Override
    public int[] getNotchSize() {
        int[] ret = null;
        try {
            ClassLoader cl = mActivity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "getNotchSize Exception");
        } finally {
            if (null == ret) {
                ret = new int[]{0, 0};
            }
            return ret;
        }
    }
}
