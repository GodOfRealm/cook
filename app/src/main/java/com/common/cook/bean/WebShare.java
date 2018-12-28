package com.common.cook.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.umeng.socialize.bean.SHARE_MEDIA;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;
import static com.umeng.socialize.bean.SHARE_MEDIA.QZONE;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

/**
 * Desc: H5调用客服端分享  传过来的参数对象
 * <p>
 * Author: SonnyJack
 * Date: 2018-09-10
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class WebShare implements Parcelable {

    public static final int SHARE_TYPE_URL = 1;//网页链接
    public static final int SHARE_TYPE_IMAGE = 2;//图片
    private int type = SHARE_TYPE_URL;//默认是网页链接
    private String title;//标题
    private String content;//内容
    private String shareImageURL;//分享的图片链接
    private String shareURL;//分享的链接
    private String shareCode;//分享的code用于区分什么分享
    public static final Creator<WebShare> CREATOR = new Creator<WebShare>() {
        @Override
        public WebShare createFromParcel(Parcel source) {
            return new WebShare(source);
        }

        @Override
        public WebShare[] newArray(int size) {
            return new WebShare[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareImageURL() {
        return shareImageURL;
    }

    public void setShareImageURL(String shareImageURL) {
        this.shareImageURL = shareImageURL;
    }

    public String getShareURL() {
        return shareURL;
    }

    public void setShareURL(String shareURL) {
        this.shareURL = shareURL;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    private int mShareMedia;

    public WebShare() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected WebShare(Parcel in) {
        this.type = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.shareImageURL = in.readString();
        this.shareURL = in.readString();
        this.shareCode = in.readString();
        this.mShareMedia = in.readInt();
    }

    public SHARE_MEDIA getShareMedia() {
        switch (mShareMedia) {
            case 1:
                return WEIXIN_CIRCLE;
            case 2:
                return QQ;
            case 3:
                return QZONE;
            default:
            case 0:
                return WEIXIN;
        }
    }

    public void setShareMedia(SHARE_MEDIA shareMedia) {
        switch (shareMedia) {
            case WEIXIN:
                mShareMedia = 0;
                break;
            case WEIXIN_CIRCLE:
                mShareMedia = 1;
                break;
            case QQ:
                mShareMedia = 2;
                break;
            case QZONE:
                mShareMedia = 3;
                break;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.shareImageURL);
        dest.writeString(this.shareURL);
        dest.writeString(this.shareCode);
        dest.writeInt(this.mShareMedia);
    }
}
