package com.miguan.youmi.core.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.miguan.youmi.core.R;

import java.util.List;

/**
 * 多种类型混合的适配器
 * Created by lmy on 2017/1/14.
 */

public class BaseMixAdapter<T> extends BaseAdapter<T> {

    private SparseArray<ItemPresenter<T>> mItemPresenters = new SparseArray<>();

    /**
     * layouts indexed with their types
     */
    private SparseArray<Integer> layouts;

    private static final int DEFAULT_VIEW_TYPE = -0xff;

    public BaseMixAdapter(List<T> data, ItemPresenter<T>... itemPresenters) {
        super(data);
        setHasStableIds(true);
        for (ItemPresenter<T> itemPresenter : itemPresenters) {
            addItemPresenter(itemPresenter);
        }
    }

    public BaseMixAdapter(ItemPresenter<T>... itemPresenters) {
        super();
        setHasStableIds(true);
        for (ItemPresenter<T> itemPresenter : itemPresenters) {
            addItemPresenter(itemPresenter);
        }
    }

    @Override
    protected ViewHolder createBaseViewHolder(View view) {
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setBaseAdapter(this);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        if (position != -1 && position < mData.size())
            return getItem(position).hashCode();
        else {
            return -1;
        }
    }

    public void addItemPresenter(ItemPresenter<T> itemPresenter) {
        if (itemPresenter instanceof MultiItemPresenter) {
            int[] itemTypes = ((MultiItemPresenter) itemPresenter).getItemTypes();
            mItemPresenters.put(itemPresenter.getItemType(), itemPresenter);
            for (int itemType : itemTypes) {
                addLayoutType(itemType, ((MultiItemPresenter) itemPresenter).getLayoutRes(itemType));
            }
        } else {
            int itemType = itemPresenter.getItemType();
            mItemPresenters.put(itemType, itemPresenter);
            addLayoutType(itemType, itemPresenter.getLayoutRes());
        }
    }

    @Override
    protected void convert(ViewHolder helper, T item) {
        ItemPresenter<T> itemPresenter = null;
        if (item != null) {
            itemPresenter = mItemPresenters.get(item.getClass().hashCode());
        }
        if (itemPresenter != null) {
            try {
                itemPresenter.convert(helper, item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void convert(ViewHolder holder, T item, List<Object> payloads) {
        ItemPresenter<T> itemPresenter = null;
        if (item != null) {
            itemPresenter = mItemPresenters.get(item.getClass().hashCode());
        }
        if (itemPresenter != null) {
            try {
                itemPresenter.convert(holder, item, payloads);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int getDefItemViewType(int position) {
        T item = mData.get(position);
        ItemPresenter<T> itemPresenter = null;
        if (item != null) {
            itemPresenter = mItemPresenters.get(item.getClass().hashCode());
        }
        if (itemPresenter != null) {
            if (itemPresenter instanceof MultiItemPresenter) {
                return ((MultiItemPresenter<T>) itemPresenter).getItemType(item);
            } else {
                return itemPresenter.getItemType();
            }
        }
        return DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addLayoutType(DEFAULT_VIEW_TYPE, layoutResId);
    }

    @Override
    protected ViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType) == null ? R.layout.base_item_empty : layouts.get(viewType);
    }

    private void addLayoutType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

    public void removeData(T o) {
        int indexOf = getData().indexOf(o);
        if (indexOf != -1) {
            remove(indexOf);
        }
    }

    public void removeData(int position) {
        if (position >= 0 && position < getData().size()) {
            remove(position);
        }
    }

    @Override
    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        ItemPresenter itemPresenter = mItemPresenters.get(holder.getItemViewType());
        if (itemPresenter != null) {
            itemPresenter.onViewDetached(holder);
        }
        super.onViewRecycled(holder);
    }

    public int indexOfItem(T item) {
        return getData().indexOf(item);
    }

}
