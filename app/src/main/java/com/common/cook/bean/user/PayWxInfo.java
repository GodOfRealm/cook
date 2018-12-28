package com.common.cook.bean.user;

import com.google.gson.annotations.SerializedName;

/**
 * 作者: liyu on  2017/12/2 0002 18:30
 * 功能描述:
 * 备注:
 */
public class PayWxInfo {

    @SerializedName("appid")
    public String appid;

    @SerializedName("partnerid")
    public String partnerid;

    @SerializedName("prepayid")
    public String prepayid;

    @SerializedName("noncestr")
    public String noncestr;

    @SerializedName("timestamp")
    public String timestamp;

    @SerializedName("package")
    public String packageValue;

    @SerializedName("sign")
    public String sign;
}
