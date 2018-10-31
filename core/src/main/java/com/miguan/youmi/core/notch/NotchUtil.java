package com.miguan.youmi.core.notch;

import android.app.Activity;
import android.os.Build;

/**
 * https://blog.csdn.net/qq_30083021/article/details/80564526判断厂商
 * Created by SonnyJack on 2018/8/29 17:03.
 */
public class NotchUtil {

    /**
     * Desc: 是否有刘海屏
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-30
     *
     * @param activity
     * @return Notch  刘海屏信息
     */
    public static Notch getNotchInfo(Activity activity) {
        Notch notch = new Notch();
        INotchInfo notchInfo = null;
        //当前版本大于28(Android  P)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            notchInfo = new AndroidNotch(activity);
        } else if (OSUtil.isMIUI()) {
            //小米
            notchInfo = new MIUINotch(activity);
        } else if (OSUtil.isEMUI()) {
            //华为
            notchInfo = new EMUINotch(activity);
        } else if (OSUtil.isVivoOs()) {
            //Vivo
            notchInfo = new VIVONotch(activity);
        } else if (OSUtil.isOppoOs()) {
            //Oppo
            notchInfo = new OPPONotch(activity);
        } else if (OSUtil.isFlymeOS()) {
            //魅族
        } else if (OSUtil.isSmartisanOs()) {
            //锤子
            notchInfo = new SmartisanNotch(activity);
        }
        if (null != notchInfo) {
            notch.setHasNotch(notchInfo.hasNotchScreen());
            if (notch.hasNotch) {
                notch.setNotchOpen(notchInfo.hasOpenNotch());
                int[] size = notchInfo.getNotchSize();
                notch.setNotchWidth(size[0]);
                notch.setNotchHeight(size[1]);
            }
        }
        return notch;
    }
}
