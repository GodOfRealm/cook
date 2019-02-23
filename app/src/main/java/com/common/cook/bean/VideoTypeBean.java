package com.common.cook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Desc:视频类型
 * <p>
 * Author: 张文顺
 * Date: 2019-02-05
 * Copyright: Copyright (c) 2013-2019
 * Update Comments:
 */
public class VideoTypeBean implements Parcelable {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prefix);
        dest.writeString(this.suffix);
    }

    public VideoTypeBean() {
    }

    protected VideoTypeBean(Parcel in) {
        this.prefix = in.readString();
        this.suffix = in.readString();
    }

    public static final Parcelable.Creator<VideoTypeBean> CREATOR = new Parcelable.Creator<VideoTypeBean>() {
        @Override
        public VideoTypeBean createFromParcel(Parcel source) {
            return new VideoTypeBean(source);
        }

        @Override
        public VideoTypeBean[] newArray(int size) {
            return new VideoTypeBean[size];
        }
    };
}
