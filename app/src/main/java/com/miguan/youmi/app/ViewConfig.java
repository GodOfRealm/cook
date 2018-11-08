package com.miguan.youmi.app;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.miguan.youmi.app.overlay.IViewConfig;
import com.miguan.youmi.module.common.model.constant.CustomType;

import java.io.Serializable;

import static com.miguan.youmi.module.common.model.constant.CustomType.HIDE;


/**
 * Desc:用于Activity/Fragment的配置，需要实现{@link IViewConfig}才能生效
 * 后根据具体使用业务进行扩展
 * <p>
 * Author: 廖培坤
 * Date: 2018-06-19
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 * 1、add right image support and custom title by SonnyJack at 2018-06-28
 */
public class ViewConfig implements Serializable, Cloneable {

    public static ViewConfig DEFAULT = new ViewConfig();
    OnKeyboardListener mOnKeyboardListener;

    private ImmersionBar mImmersionBar; // 在Activity中传递用于销毁

    @ColorRes
    int mStatusBarColor; // 任务栏颜色

    boolean mKeyboardEnable = false;

    boolean mToolbarBackVisible = true; // 返回图标是否可见

    @DrawableRes
    int mToolbarBackRes; // 返回图标资源

    boolean mToolbarRightVisible = true; // 右边图标是否可见

    @DrawableRes
    int mToolbarRightRes; // 右边图标资源

    @CustomType.Type
    int mRightCustomType = HIDE; // 右边显示在线客服类型

    int mToolbarRightPadding; // 右边图标Padding

    View.OnClickListener mOnLeftClickListener; // 左边边单击
    View.OnClickListener mOnRightClickListener; // 右边单击
    View.OnClickListener mOnRightTextClickListener; // 右边文本单击


    String mToolbarTitle; // 中间标题
    @ColorRes
    int mToolbarTitleColor; // 中间标题颜色
    boolean mToolbarRightTextVisible = false; // 右边文本是否可见
    String mToolbarRightText; // 右边文本
    @ColorRes
    int mToolbarBackgroundColor; // 标题栏颜色

    boolean mNotchFullScreen = true;//刘海屏是否全屏显示
    boolean mStatusBarDarkFont = true;//状态栏字体深色或亮色(默认深色)
    boolean mShowStatusBar = true;//是否显示状态栏

    public ImmersionBar getImmersionBar() {
        return mImmersionBar;
    }

    public void setImmersionBar(ImmersionBar immersionBar) {
        mImmersionBar = immersionBar;
    }

    public ViewConfig setToolbarBackVisible(boolean visible) {
        mToolbarBackVisible = visible;
        return this;
    }

    public ViewConfig setToolbarBackRes(int res) {
        mToolbarBackRes = res;
        return this;
    }

    public ViewConfig setStatusBarColor(@ColorRes int res) {
        mStatusBarColor = res;
        return this;
    }

    public ViewConfig setToolbarRightVisible(boolean visible) {
        mToolbarRightVisible = visible;
        return this;
    }

    public ViewConfig setToolbarRightRes(int res) {
        mToolbarRightRes = res;
        return this;
    }

    public ViewConfig setToolbarRightPadding(int padding) {
        mToolbarRightPadding = padding;
        return this;
    }


    public ViewConfig setRightCustomType(@CustomType.Type int customType) {
        mRightCustomType = customType;
        return this;
    }
    /**
     * Desc: 设置Toolbar右边图标点击事件
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param listener
     * @return activity config
     */
    public ViewConfig setRightClickListener(View.OnClickListener listener) {
        mOnRightClickListener = listener;
        return this;
    }
    public ViewConfig setRightTextClickListener(View.OnClickListener listener) {
        mOnRightTextClickListener = listener;
        return this;
    }

    public ViewConfig setLeftClickListener(View.OnClickListener listener) {
        mOnLeftClickListener = listener;
        return this;
    }

    public ViewConfig setToolbarTitle(String toolbarTitle) {
        mToolbarTitle = toolbarTitle;
        return this;
    }

    public ViewConfig setToolbarTitleColor(@ColorRes int res) {
        mToolbarTitleColor = res;
        return this;
    }

    public ViewConfig setToolbarRightTextVisible(boolean toolbarRightTextVisible) {
        mToolbarRightTextVisible = toolbarRightTextVisible;
        return this;
    }

    public ViewConfig setToolbarRightText(String toolbarRightText) {
        mToolbarRightText = toolbarRightText;
        return this;
    }

    public ViewConfig setToolbarBackgroundColor(@ColorRes int color) {
        mToolbarBackgroundColor = color;
        return this;
    }

    public ViewConfig setKeyboardEnable(boolean keyboardEnable) {
        mKeyboardEnable = keyboardEnable;
        return this;
    }

    public ViewConfig setNotchFullScreen(boolean notchFullScreen) {
        mNotchFullScreen = notchFullScreen;
        return this;
    }

    public ViewConfig setStatusBarDarkFont(boolean statusBarDarkFont) {
        mStatusBarDarkFont = statusBarDarkFont;
        return this;
    }

    public ViewConfig setShowStatusBar(boolean showStatusBar) {
        mShowStatusBar = showStatusBar;
        return this;
    }

    public ViewConfig setOnKeyboardListener(OnKeyboardListener keyboardListener) {
        this.mOnKeyboardListener = keyboardListener;
        if (keyboardListener != null) {
            setKeyboardEnable(true);
        }
        return this;
    }

    @Override
    protected ViewConfig clone() {
        try {
            return (ViewConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new ViewConfig();
    }

}
