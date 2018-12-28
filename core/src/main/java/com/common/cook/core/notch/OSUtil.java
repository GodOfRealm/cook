package com.common.cook.core.notch;

import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by SonnyJack on 2018/8/30 14:55.
 */
public class OSUtil {
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";//小米
    private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";//华为
    private static final String KEY_VIVO_VERSION_NAME = "ro.vivo.os.version";//Vivo
    private static final String KEY_OPPO_VERSION_NAME = "ro.build.version.opporom";//Oppo
    private static final String KEY_SMARTISAN_VERSION_NAME = "ro.smartisan.version";//锤子
    private static final String KEY_DISPLAY = "ro.build.display.id";//魅族

    /**
     * 判断是否为miui
     * Is miui boolean.
     *
     * @return the boolean
     */
    public static boolean isMIUI() {
        return !TextUtils.isEmpty(getMIUIVersion());
    }

    /**
     * 获得miui的版本
     * Gets miui version.
     *
     * @return the miui version
     */
    public static String getMIUIVersion() {
        return getSystemProperty(KEY_MIUI_VERSION_NAME, "");
    }

    /**
     * 判断miui版本是否大于等于 versionNum
     * Is miui versionNum later boolean.
     *
     * @return the boolean
     */
    public static boolean isMIUINumLater(int versionNum) {
        String version = getMIUIVersion();
        int num;
        if ((!version.isEmpty())) {
            try {
                num = Integer.valueOf(version.substring(1));
                return num >= versionNum;
            } catch (NumberFormatException e) {
                return false;
            }
        } else
            return false;
    }

    /**
     * 判断是否为emui
     * Is emui boolean.
     *
     * @return the boolean
     */
    public static boolean isEMUI() {
        String property = getSystemProperty(KEY_EMUI_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断是否为flymeOS
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    public static boolean isFlymeOS() {
        String flymeOsFlag = getSystemProperty(KEY_DISPLAY, "");
        return flymeOsFlag.toLowerCase().contains("flyme");
    }

    /**
     * 判断是否为VivoOS
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    public static boolean isVivoOs() {
        String property = getSystemProperty(KEY_VIVO_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断是否为Oppo Os
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    public static boolean isOppoOs() {
        String property = getSystemProperty(KEY_OPPO_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断是否为锤子 Os
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    public static boolean isSmartisanOs() {
        String property = getSystemProperty(KEY_SMARTISAN_VERSION_NAME, "");
        return !TextUtils.isEmpty(property);
    }

    public static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
