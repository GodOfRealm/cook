package com.common.cook.bean.account;

import android.os.Parcel;
import android.os.Parcelable;

public class Token implements Parcelable {

    /**
     * 是否第一次登录
     */
    private boolean is_need_complete;

    /**
     * 登录标识
     */
    private String token;

    /**
     * 是否绑定手机
     */
    private boolean is_bind_tel;

    public boolean isFirstLogin() {
        return is_need_complete;
    }

    public void setFirstLogin(boolean isFirstLogin) {
        this.is_need_complete = isFirstLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBindTel() {
        return is_bind_tel;
    }

    public void setBindTel(boolean is_bind_tel) {
        this.is_bind_tel = is_bind_tel;
    }

    public Token() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.is_need_complete ? (byte) 1 : (byte) 0);
        dest.writeString(this.token);
        dest.writeByte(this.is_bind_tel ? (byte) 1 : (byte) 0);
    }

    protected Token(Parcel in) {
        this.is_need_complete = in.readByte() != 0;
        this.token = in.readString();
        this.is_bind_tel = in.readByte() != 0;
    }

    public static final Creator<Token> CREATOR = new Creator<Token>() {
        @Override
        public Token createFromParcel(Parcel source) {
            return new Token(source);
        }

        @Override
        public Token[] newArray(int size) {
            return new Token[size];
        }
    };
}
