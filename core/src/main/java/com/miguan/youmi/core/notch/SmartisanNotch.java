package com.miguan.youmi.core.notch;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by SonnyJack on 2018/8/30 16:19.
 */
public class SmartisanNotch implements INotchInfo {

    //https://resource.smartisan.com/resource/61263ed9599961d1191cc4381943b47a.pdf

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     */
    public SmartisanNotch(Activity activity) {
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
            Class FtFeature = cl.loadClass("smartisanos.api.DisplayUtilsSmt");
            Method get = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) get.invoke(FtFeature, 0x00000001);
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
        //https://resource.smartisan.com/resource/61263ed9599961d1191cc4381943b47a.pdf
        int[] size = {104, 82};
        return size;
    }
}
