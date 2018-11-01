package com.miguan.youmi.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.ExtraConstant;
import com.miguan.youmi.app.overlay.IViewConfig;
import com.miguan.youmi.core.notch.Notch;
import com.miguan.youmi.core.notch.NotchUtil;
import com.noober.background.BackgroundLibrary;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * Copyright (c) 2018 Miguan Inc All rights reserved.
 * Created by Liaopeikun on 2018/6/15
 */
public class ActivityLifecycleCallbacksImp implements Application.ActivityLifecycleCallbacks {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        setCustomDensity(activity, activity.getApplication());
    }

    private void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();

        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration configuration) {
                    if (configuration != null && configuration.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });

            final float targetDensity = appDisplayMetrics.widthPixels / 360;
            final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
            final int targetDensityDpi = (int) (160 * targetDensity);

            appDisplayMetrics.density = targetDensity;
            appDisplayMetrics.scaledDensity = targetScaledDensity;
            appDisplayMetrics.densityDpi = targetDensityDpi;

            final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = targetDensity;
            activityDisplayMetrics.scaledDensity = targetScaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!activity.getIntent().getBooleanExtra(ExtraConstant.IS_ACTIVITY_INIT, false)) {
            activity.getIntent().putExtra(ExtraConstant.IS_ACTIVITY_INIT, true);
            ViewConfig viewConfig;
            if (activity instanceof IViewConfig) {
                viewConfig = ((IViewConfig) activity).getViewConfig();
            } else {
                viewConfig = ViewConfig.DEFAULT.clone();
            }
            initToolbar(activity, viewConfig);
            initStatusBar(activity, viewConfig);
            activity.getIntent().putExtra(ExtraConstant.ACTIVITY_CONFIG, viewConfig);

        }
    }

    private void initToolbar(Activity activity, ViewConfig viewConfig) {
        Toolbar toolbar = activity.findViewById(R.id.base_toolbar);
        if (toolbar != null) {
            if (activity instanceof AppCompatActivity) {
                ((AppCompatActivity) activity).setSupportActionBar(toolbar);
                ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                ((AppCompatActivity) activity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }

            if (viewConfig.mToolbarBackgroundColor > 0) {
                toolbar.setBackgroundColor(activity.getResources().getColor(viewConfig.mToolbarBackgroundColor));
            }

            View.OnClickListener onBackClickListener = null == viewConfig.mOnLeftClickListener ? view -> activity.onBackPressed() : viewConfig.mOnLeftClickListener;
            ImageView ivBack = activity.findViewById(R.id.base_toolbar_back);
            if (ivBack != null) {
                if (viewConfig.mToolbarBackVisible) {
                    ivBack.setVisibility(View.VISIBLE);
                    ivBack.setOnClickListener(onBackClickListener);
                    if (viewConfig.mToolbarBackRes > 0) {
                        ivBack.setImageResource(viewConfig.mToolbarBackRes);
                    }
                } else {
                    ivBack.setVisibility(View.GONE);
                }
            } else {
                toolbar.setNavigationOnClickListener(onBackClickListener);
            }

            //Toolbar中间标题
            TextView tvTitle = activity.findViewById(R.id.base_toolbar_title);
            if (tvTitle != null) {
                //文本
                CharSequence title = TextUtils.isEmpty(viewConfig.mToolbarTitle) ? activity.getTitle() : viewConfig.mToolbarTitle;
                if (!TextUtils.isEmpty(title)) {
                    tvTitle.setText(title);
                }
                //文本颜色
                if (viewConfig.mToolbarTitleColor > 0) {
                    tvTitle.setTextColor(activity.getResources().getColor(viewConfig.mToolbarTitleColor));
                }
            }

            //Toolbar右边按钮
            ImageView right = activity.findViewById(R.id.base_toolbar_right);
            if (null != right) {
                if (viewConfig.mToolbarRightVisible && viewConfig.mToolbarRightRes > 0) {
                    right.setVisibility(View.VISIBLE);
                    right.setImageResource(viewConfig.mToolbarRightRes);
                    if (viewConfig.mOnRightClickListener != null) {
                        right.setOnClickListener(viewConfig.mOnRightClickListener);
                    }
                    if (viewConfig.mToolbarRightPadding > 0) {
                        int padding = viewConfig.mToolbarRightPadding;
                        right.setPadding(padding, padding, padding, padding);
                    }
                } else {
                    right.setVisibility(View.GONE);
                }
            }

            //Toolbar右边文本
            TextView tvRight = activity.findViewById(R.id.base_toolbar_text_right);
            if (null != tvRight) {
                if (viewConfig.mToolbarRightTextVisible) {
                    tvRight.setVisibility(View.VISIBLE);
                    tvRight.setText(viewConfig.mToolbarRightText);
                }
            }
        }
    }

    private void initStatusBar(Activity activity, ViewConfig viewConfig) {
        ImmersionBar immersionBar = ImmersionBar.with(activity)
                .statusBarDarkFont(viewConfig.mStatusBarDarkFont, 0.2f)
                .navigationBarEnable(false);
        boolean showStatusBar = viewConfig.mShowStatusBar;
        Toolbar toolbar = activity.findViewById(R.id.base_toolbar);
        View statusBar = activity.findViewById(R.id.base_status_bar);
        Notch notch = NotchUtil.getNotchInfo(activity);
        if (notch.isHasNotch() && !viewConfig.mNotchFullScreen && null == toolbar && null == statusBar) {
            //如果是刘海屏且显示,并且不想满屏显示
            ViewGroup contentLayout = activity.findViewById(android.R.id.content);
            ViewGroup content = (ViewGroup) contentLayout.getChildAt(0);
            contentLayout.removeView(content);
            LinearLayout linearLayout = new LinearLayout(activity);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            View view = LayoutInflater.from(activity).inflate(R.layout.base_include_status_bar, null);
            view.setBackground(content.getBackground());
            linearLayout.addView(view);
            linearLayout.addView(content);
            contentLayout.addView(linearLayout, 0);
            immersionBar.statusBarView(view);
            showStatusBar = true;//刘海屏的显示状态栏，第一次进来布局会往下移
        }
        if (viewConfig.mStatusBarColor > 0) {
            immersionBar.statusBarColor(viewConfig.mStatusBarColor);
        } else {
            immersionBar.transparentBar();
        }
        if (toolbar != null) {
            immersionBar.titleBar(toolbar);
        } else {
            if (statusBar != null) {
                immersionBar.statusBarView(statusBar);
            }
        }
        immersionBar.keyboardEnable(viewConfig.mKeyboardEnable);
        immersionBar.hideBar(showStatusBar ? BarHide.FLAG_SHOW_BAR : BarHide.FLAG_HIDE_STATUS_BAR);
        immersionBar.setOnKeyboardListener(viewConfig.mOnKeyboardListener);
        immersionBar.init();
        viewConfig.setImmersionBar(immersionBar);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        MobclickAgent.onResume(activity);

        JPushInterface.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPause(activity); //统计时长
        JPushInterface.onPause(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ViewConfig viewConfig = (ViewConfig) activity.getIntent().getSerializableExtra(ExtraConstant.ACTIVITY_CONFIG);
        if (viewConfig != null) {
            if (viewConfig.getImmersionBar() != null) {
                viewConfig.getImmersionBar().destroy();
            }
        }
    }

}
