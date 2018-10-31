package com.miguan.youmi.core.notch;

/**
 * Created by SonnyJack on 2018/8/30 14:41.
 */
public class Notch {

    boolean hasNotch = false;//是否有刘海屏
    boolean notchOpen = false;//刘海屏是否开启
    int notchHeight = 0;//刘海屏高度
    int notchWidth = 0;//刘海屏宽度

    public boolean isHasNotch() {
        return hasNotch;
    }

    public void setHasNotch(boolean hasNotch) {
        this.hasNotch = hasNotch;
    }

    public boolean isNotchOpen() {
        return notchOpen;
    }

    public void setNotchOpen(boolean notchOpen) {
        this.notchOpen = notchOpen;
    }

    public int getNotchHeight() {
        return notchHeight;
    }

    public void setNotchHeight(int notchHeight) {
        this.notchHeight = notchHeight;
    }

    public int getNotchWidth() {
        return notchWidth;
    }

    public void setNotchWidth(int notchWidth) {
        this.notchWidth = notchWidth;
    }
}
