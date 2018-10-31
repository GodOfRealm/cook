package com.miguan.youmi.bean.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者: zws 2018/8/1 0001 13:56
 * 功能描述:
 * 备注:
 */
public class VersionBean implements Parcelable {

//    string
//    allowEmptyValue: false
//    渠道
//
//    content	string
//    allowEmptyValue: false
//    版本更新内容
//
//    created_at	string
//    allowEmptyValue: false
//    版本发布时间
//
//    id	integer($int32)
//    allowEmptyValue: false
//    对应渠道的版本号ID
//
//    mandatory	boolean
//    example: false
//    allowEmptyValue: false
//    是否启用强制更新
//
//    title	string
//    update_required	boolean
//    example: false
//    allowEmptyValue: false
//    是否需要更新, 只有客户端传入自己版本时候才会返回结果
//
//    url	string
//    allowEmptyValue: false
//    版本号下载地址
//
//    version_number	string
//    allowEmptyValue: false
//    版本号

    private String channel;
    private String content;
    private String created_at;
    private int id;
    private boolean mandatory;
    private String title;
    private boolean update_required;
    private String url;
    private String version_number;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isUpdate_required() {
        return update_required;
    }

    public void setUpdate_required(boolean update_required) {
        this.update_required = update_required;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion_number() {
        return version_number;
    }

    public void setVersion_number(String version_number) {
        this.version_number = version_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.channel);
        dest.writeString(this.content);
        dest.writeString(this.created_at);
        dest.writeInt(this.id);
        dest.writeByte(this.mandatory ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeByte(this.update_required ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
        dest.writeString(this.version_number);
    }

    public VersionBean() {
    }

    protected VersionBean(Parcel in) {
        this.channel = in.readString();
        this.content = in.readString();
        this.created_at = in.readString();
        this.id = in.readInt();
        this.mandatory = in.readByte() != 0;
        this.title = in.readString();
        this.update_required = in.readByte() != 0;
        this.url = in.readString();
        this.version_number = in.readString();
    }

    public static final Creator<VersionBean> CREATOR = new Creator<VersionBean>() {
        @Override
        public VersionBean createFromParcel(Parcel source) {
            return new VersionBean(source);
        }

        @Override
        public VersionBean[] newArray(int size) {
            return new VersionBean[size];
        }
    };

    @Override
    public String toString() {
        return "VersionBean{" +
                "channel='" + channel + '\'' +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                ", mandatory=" + mandatory +
                ", title='" + title + '\'' +
                ", update_required=" + update_required +
                ", url='" + url + '\'' +
                ", version_number='" + version_number + '\'' +
                '}';
    }
}
