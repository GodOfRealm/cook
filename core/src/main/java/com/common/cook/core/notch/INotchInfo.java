package com.common.cook.core.notch;

/**
 * Created by SonnyJack on 2018/8/30 14:43.
 */
public interface INotchInfo {
    //是否有刘海屏
    boolean hasNotchScreen();

    //是否打开刘海屏
    boolean hasOpenNotch();

    //刘海屏大小
    int[] getNotchSize();
}
