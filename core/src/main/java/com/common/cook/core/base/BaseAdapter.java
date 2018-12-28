package com.common.cook.core.base;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.cook.core.util.CollectionUtil;

import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, ViewHolder> {

    /**
     * 当前页码
     */
    private int mPageCount;

    private int mPageSize;

    private boolean mFilterUnique = true;

    public BaseAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    public BaseAdapter() {
        super(0);
    }

    public void setFilterUnique(boolean filterUnique) {
        mFilterUnique = filterUnique;
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        boolean loadMoreEnable = isLoadMoreEnable();
        super.setNewData(data);
        // setNewData后只要有设置loadMore监听，就会将loadMoreEnable设置为true，之前设置的false失效
        // 所以super之后再次设置为false
        if (!loadMoreEnable) {
            setEnableLoadMore(false);
        }
        mPageCount = 1;
        mPageSize = CollectionUtil.getSize(data);
        setAutoLoadMoreSize((int) (CollectionUtil.getSize(data) * 0.7f));
    }

    @Override
    public void addData(@NonNull Collection<? extends T> newData) {
        try {
            if (mFilterUnique) {
                int size = CollectionUtil.getSize(getData());
                List<T> subList = getData().subList(size - mPageSize, size);
                newData.removeAll(subList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.addData(newData);
        mPageCount++;
    }

    public void addData(int position, @NonNull List<T> data, boolean filterUnique, int direction) {
        if (filterUnique) {
            try {
                List<T> list = getData();
                List<T> subList;
                if (direction == -1) {
                    subList = list.subList(Math.max(0, position - mPageSize), position);
                } else {
                    subList = list.subList(position, Math.min(list.size(), position + mPageSize));
                }
                data.removeAll(subList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.addData(position, data);
    }

    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
        mPageCount = 0;
    }

    public int getDataCount() {
        return getData().size();
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int pageNo) {
        this.mPageCount = pageNo;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (CollectionUtil.isEmpty(payloads)) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            convert(holder, getItem(position - getHeaderLayoutCount()), payloads);
        }
    }

    public void refreshNotifyItemChanged(int position, Object object) {
        notifyItemChanged(position + getHeaderLayoutCount(), object);
    }

    protected void convert(ViewHolder holder, T item, List<Object> payloads) {

    }
}
