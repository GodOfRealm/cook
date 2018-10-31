package com.miguan.youmi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Desc: 如果接口定义返回此类型，将不剥离数据直接解析最外层的数据
 *  一般用于不关心数据类型的接口（比如一些点赞、评论等，我只需要知道是否成功并不关心数据）
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-05
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class Response implements Parcelable {

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
    }

    public Response() {
    }

    protected Response(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel source) {
            return new Response(source);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

}
