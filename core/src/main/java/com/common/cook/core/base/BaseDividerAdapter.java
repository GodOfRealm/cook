package com.common.cook.core.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.common.cook.core.R;

import java.util.Collection;
import java.util.List;

/**
 * Desc: 有分割线，并且最后一个item没有分割线
 * <p>
 * Author: QiuRonaC
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 *
 * @param <T> the type parameter
 */
public abstract class BaseDividerAdapter<T> extends BaseAdapter<T> {

    public BaseDividerAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseDividerAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseDividerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, T item) {
        bind(helper, item);
        dividerVisible(helper, getLastPosition());
    }

    protected abstract void bind(ViewHolder helper, T item);

    private int getLastPosition(){
        return mData.size() - 1 + getHeaderLayoutCount();
    }

    @Override
    public void addData(@NonNull Collection<? extends T> newData) {
        int lastItem = getLastPosition();
        super.addData(newData);
        // 更新上一个item，显示divider
        notifyItemChanged(lastItem);
    }

    @Override
    public void remove(int position) {
        super.remove(position);
        if (position == getLastPosition()) {
            notifyItemChanged(position - 1);
        }
    }

    /**
     * 设置当前Holder的UI的底部分割线的显示情况，如果是最后一个Item则隐藏，其他item显示
     * 分割线的id需要设置为R.id.divider
     *
     * @param targetPos 需要控制隐藏的position
     */
    private void dividerVisible(ViewHolder helper, int targetPos) {
        int dividerVisible = helper.getAdapterPosition() == targetPos ? View.INVISIBLE : View.VISIBLE;
        View view = helper.getView(R.id.base_recycler_item_divider);
        if (view != null) {
            view.setVisibility(dividerVisible);
        }
    }
}
