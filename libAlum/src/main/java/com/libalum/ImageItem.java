package com.libalum;

import android.provider.MediaStore;

public class ImageItem {
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件类型
     */
    private int type;

    private long duration;

    public ImageItem(String path, int type, long duration) {
        this.path = path;
        this.type = type;
        this.duration = duration;
    }


    public String getPath() {
        return path;
    }

    /**
     * 是否是视频
     *
     * @return
     */
    public boolean isVideoFile() {
        return type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDuration() {
        return duration / 1000 / 60 + ":" + duration / 1000;
    }
}
