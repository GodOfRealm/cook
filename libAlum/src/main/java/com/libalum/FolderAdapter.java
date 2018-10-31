package com.libalum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class FolderAdapter extends BaseAdapter {

    ArrayList<ImageFloder> mDirPaths;
    private Context mContext;
    private ImageFloder currentImageFolder;


    public FolderAdapter(Context mContext, ArrayList<ImageFloder> mDirPaths, ImageFloder currentImageFolder) {
        this.mContext = mContext;
        this.mDirPaths = mDirPaths;
        this.currentImageFolder = currentImageFolder;
    }

    @Override
    public int getCount() {
        return mDirPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FolderViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.list_dir_item, null);
            holder = new FolderViewHolder();
            holder.ivDirIcon = convertView.findViewById(R.id.id_dir_item_image);
            holder.id_dir_item_name = convertView.findViewById(R.id.id_dir_item_name);
            holder.id_dir_item_count = convertView.findViewById(R.id.id_dir_item_count);
            holder.choose = convertView.findViewById(R.id.choose);
            convertView.setTag(holder);
        } else {
            holder = (FolderViewHolder) convertView.getTag();
        }
        ImageFloder item = mDirPaths.get(position);
        holder.ivDirIcon.setTag(item.getFirstImagePath());
        ImageLoadHelper.load(mContext, "file://" + item.getFirstImagePath(), holder.ivDirIcon);

        String formatSize = String.format(Locale.getDefault(), "%d张", item.isVideoFolder() ? item.getSize() : item.images.size());
        holder.id_dir_item_count.setText(formatSize);
        holder.id_dir_item_name.setText(item.getName());
        holder.choose.setVisibility((currentImageFolder == item) ? View.VISIBLE : View.GONE);
        return convertView;
    }

    public void setCurrentImageFolder(ImageFloder currentImageFolder) {
        this.currentImageFolder = currentImageFolder;
    }
}

class FolderViewHolder {
    ImageView ivDirIcon;
    ImageView choose;
    TextView id_dir_item_name;
    TextView id_dir_item_count;
}
