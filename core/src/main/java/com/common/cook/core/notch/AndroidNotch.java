package com.common.cook.core.notch;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.WindowInsets;

import java.util.List;

/**
 * Created by SonnyJack on 2018/8/30 14:57.
 */
public class AndroidNotch implements INotchInfo {

    private Activity mActivity;

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     */
    public AndroidNotch(Activity activity) {
        if (null == activity) {
            throw new NullPointerException("activity is null");
        }
        mActivity = activity;
    }

    @TargetApi(Build.VERSION_CODES.P)
    @Override
    public boolean hasNotchScreen() {
        WindowInsets windowInsets = mActivity.getWindow().getDecorView().getRootWindowInsets();
        if(null == windowInsets){
            return false;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        return null != displayCutout;
    }

    @TargetApi(Build.VERSION_CODES.P)
    @Override
    public boolean hasOpenNotch() {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.P)
    @Override
    public int[] getNotchSize() {
        int[] result = {0, 0};
        WindowInsets windowInsets = mActivity.getWindow().getDecorView().getRootWindowInsets();
        if(null != windowInsets) {
            DisplayCutout displayCutout = windowInsets.getDisplayCutout();
            List<Rect> boundingRect = displayCutout.getBoundingRects();
            if (null != boundingRect && boundingRect.size() > 0) {
                Rect rect = boundingRect.get(0);
                result[0] = rect.right - rect.left;
                result[1] = rect.bottom - rect.top;
            }
        }
        return result;
    }
}
