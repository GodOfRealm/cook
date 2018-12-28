package com.common.cook.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.common.cook.R;
import com.common.cook.app.overlay.IViewConfig;

public class FragmentLifecycleCallbacksImp extends FragmentManager.FragmentLifecycleCallbacks {

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);

        ViewConfig viewConfig;
        if (f instanceof IViewConfig) {
            viewConfig = ((IViewConfig) f).getViewConfig();
        } else {
            viewConfig = ViewConfig.DEFAULT.clone();
        }
        initStatusBar(f, v, viewConfig);
    }

    private void initStatusBar(Fragment fragment, View view, ViewConfig viewConfig) {
        View statusBar = view.findViewById(R.id.base_status_bar);
        if (statusBar != null) {
            ImmersionBar immersionBar = ImmersionBar.with(fragment)
                    .statusBarDarkFont(true, 0.2f)
                    .navigationBarEnable(false)
                    .statusBarView(statusBar);
            if (viewConfig.mStatusBarColor > 0) {
                immersionBar.statusBarColor(viewConfig.mStatusBarColor);
            } else {
                immersionBar.transparentBar();
            }
            immersionBar.init();
            viewConfig.setImmersionBar(immersionBar);
        }
    }

}
