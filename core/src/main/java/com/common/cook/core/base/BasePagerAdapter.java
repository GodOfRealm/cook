package com.common.cook.core.base;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Desc:简单的ViewpagerAdapter
 * <p>
 * Author: QiuRonaC
 * Date: 2018-09-11
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public abstract class BasePagerAdapter extends PagerAdapter {
    private final int mCount;
    private final int mLayoutRes;

    public BasePagerAdapter(int count, int layoutRes) {
        mCount = count;
        mLayoutRes = layoutRes;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = getView(container, position);
        container.addView(inflate);
        convert(inflate, position);
        return inflate;
    }

    protected View getView(@NonNull ViewGroup container, int position) {
        return LayoutInflater.from(container.getContext()).inflate(mLayoutRes, container, false);
    }

    public abstract void convert(View view, int position);

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
