package com.common.cook.core.notch;

import android.app.Activity;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Method;

/**
 * Created by SonnyJack on 2018/8/30 15:41.
 */
public class VIVONotch implements INotchInfo {

    //https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     */
    public VIVONotch(Activity activity) {
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
            Class FtFeature = cl.loadClass("android.util.FtFeature");
            Method get = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) get.invoke(FtFeature, 0x00000020);
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
        return hasNotchScreen();
    }

    @Override
    public int[] getNotchSize() {
        int[] size = {0, 0};
        //根据官网文档， 刘海屏的宽为100dp, 高为27dp
        //https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
        size[0] = dp2px(100);
        size[1] = dp2px(27);
        return size;
    }

    public int dp2px(final float dpValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
