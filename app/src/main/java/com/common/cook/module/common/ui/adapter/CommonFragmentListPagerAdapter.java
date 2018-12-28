package com.common.cook.module.common.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by SonnyJack on 2018/7/3 17:00.
 */
public class CommonFragmentListPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public CommonFragmentListPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public Fragment getItem(int position) {
        if(position < 0 || position >= getCount()){
            return null;
        }
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return null == mFragments ? 0 : mFragments.size();
    }

}
