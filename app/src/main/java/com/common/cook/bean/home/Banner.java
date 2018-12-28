package com.common.cook.bean.home;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SonnyJack on 2018/7/3 16:55.
 */
public class Banner implements Parcelable {

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
    private String content;
    private String image;
    private String title;

    public Banner() {

    }

    private String id;

    protected Banner(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.image = in.readString();
        this.title = in.readString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.image);
        dest.writeString(this.title);
    }
}
