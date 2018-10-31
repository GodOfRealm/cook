package com.libalum;

import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class ImageFloder {


    public List<ImageItem> images = new ArrayList<ImageItem>();
    /**
     * 图片的文件夹路径
     */
    private String dir;
    /**
     * 第一张图片的路径
     */
    private String firstImagePath = "";
    /**
     * 文件夹的名称
     */
    private String name;
    /**
     * 标识是图片还是视频
     */
    private int folderType;
    private int size;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf);
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public void setFolderTypeVideo(int folderType) {
        this.folderType = folderType;
    }

    public boolean isVideoFolder() {
        return folderType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}