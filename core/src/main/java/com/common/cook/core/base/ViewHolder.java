package com.common.cook.core.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.common.cook.core.util.IImageLoader;

/**
 * <p>
 * describe:
 * <p>
 * </p>
 *
 * @author qiurong
 * @date 2018/7/22
 * @since 2.1.4
 */
public class ViewHolder extends BaseViewHolder {

    public Object tag;
    private BaseAdapter mBaseAdapter;

    public ViewHolder(View view) {
        super(view);
    }

    public BaseAdapter getBaseAdapter() {
        return mBaseAdapter;
    }

    public void setBaseAdapter(BaseAdapter baseAdapter) {
        mBaseAdapter = baseAdapter;
    }

    public ViewHolder loadAvatar(int id, String url) {
        IImageLoader.loadAvatar((ImageView) getView(id), url);
        return this;
    }

    public ViewHolder loadImage(int id, String url) {
        IImageLoader.loadImage((ImageView) getView(id), url);
        return this;
    }

    public ViewHolder loadImage(int id, String url, int defaultRes) {
        IImageLoader.loadImage((ImageView) getView(id), url, defaultRes);
        return this;
    }

    public ViewHolder setVisible(boolean visible, int id) {
        View view = getView(id);
        if (null != view) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public TextView getTextView(int id) {
        return getView(id);
    }
}
