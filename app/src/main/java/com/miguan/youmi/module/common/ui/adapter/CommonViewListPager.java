package com.miguan.youmi.module.common.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by SonnyJack on 2018/7/11 10:53.
 */
public class CommonViewListPager<T extends View> extends PagerAdapter {

    private List<T> mViewList;

    public CommonViewListPager(List<T> viewList) {
        mViewList = viewList;
    }

    @Override
    public int getCount() {
        return null == mViewList ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        View view = (View) object;
        container.removeView(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }
}
