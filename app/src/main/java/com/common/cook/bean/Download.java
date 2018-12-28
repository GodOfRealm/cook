package com.common.cook.bean;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Desc: 下载的item
 * <p>
 * Author: SonnyJack
 * Date: 2018-09-05
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class Download implements Parcelable {

    private String action;//用于区分下载action
    private String url;//下载的连接
    private String fileDir;//下载完成后要保存的文件夹
    private String fileName;//下载完成后要保存的文件名字
    private String localUrl;//下载完成后文件的路径

    private Object object;

    public Download() {

    }

    public Download(String action) {
        this.action = action;
    }

    public boolean isSuccess(){
        return !TextUtils.isEmpty(localUrl);
    }

    protected Download(Parcel in) {
        action = in.readString();
        url = in.readString();
        fileDir = in.readString();
        fileName = in.readString();
        localUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeString(url);
        dest.writeString(fileDir);
        dest.writeString(fileName);
        dest.writeString(localUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Download> CREATOR = new Creator<Download>() {
        @Override
        public Download createFromParcel(Parcel in) {
            return new Download(in);
        }

        @Override
        public Download[] newArray(int size) {
            return new Download[size];
        }
    };

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
