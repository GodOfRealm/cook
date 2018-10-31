package com.miguan.youmi.bean.account;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Desc: 用户类
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class User implements Parcelable {

    /**
     * 是否后台在线
     */
    private boolean background;
    /**
     * 是否可以更改性别
     */
    private boolean is_modify_gender;
    /**
     * 是否绑定手机
     */
    private boolean is_bind_tel;
    /**
     * 是否绑定qq
     */
    private boolean is_bind_qq;
    /**
     * 是否绑定微信
     */
    private boolean is_bind_weixin;
    /**
     * 是否支付宝
     */
    private boolean is_bind_alipay;
    /**
     * 绑定的手机号
     */
    private String tel;
    /**
     * 完整的手机号
     */
    private String sensitive_tel;
    /**
     * 出生日期
     */
    private String birthday;

    /**
     * 粉丝数
     */
    private int count_fans;

    /**
     * 我关注的人数
     */
    private int count_subscribe;

    /**
     * 最近访客总数
     */
    private int count_visitor;

    /**
     * 性别(1男 2女)
     */
    private int gender;

    /**
     * 邀请码
     */
    private String invitation_code;

    private User invite_user;
    private User user_details_vo;

    /**
     * 年龄
     */
    private int age;

    /**
     * 昵称
     */
    private String nick_name;

    /**
     * 头像
     */
    private String photo;

    /**
     * 我是否关注ta(1是 0否)
     */
    private int subscribe;

    private String uid;

    /**
     * 用户Id
     */
    @SerializedName(value = "user_id", alternate = "id")
    private String user_id;

    /**
     * 注册时间
     */
    private String reg_time;

    private String rongyuntoken;

    /**
     * 地区
     */
    private String area;

    /**
     * 身份是否验证(1是 0否)
     */
    private int identity_auth;

    /**
     * 个人简介
     */

    private String introduction;
    /**
     * 最后操作时间
     */
    private String last_operation_time;

    /**
     * 是否来自合作商(1是 0否)
     */
    private String is_partner;

    /**
     * 今日是否签到(1是 0否)
     */
    private int is_today_signin;

    /**
     * 是否为达人(0非达人，1达人)
     */
    private int play_ident;

    /**
     * 语音签名
     */
    private String voice_sign_path;



    /**
     * 用户技能简约版
     */
    private ArrayList<String> skill_tags;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 上线时间
     */
    private String online_tip;

    /**
     * vip
     */
    private String level;




    private boolean is_live;



    /**
     * 技能价格
     */
    private String price;

    /**
     * 评分
     */
    private double average;

    //是否拉黑被拉黑，都是相对于当前登录用户
    private boolean is_black;//是否来黑对方
    private boolean is_blacked;//是否被对方拉黑

    private String task_level_icon;//任务等级图片地址
    private double sum_task_point;//任务等级

    private boolean is_official;//是否是官方号
    private boolean is_test; // 是否是测试号
    private String vip_expired_at; // 会员过期时间
    private int vip_type;//vip类型（0普通用户；1vip；2svip）
    private int order_count; // 接单次数

    public User() {
    }

    public String getSensitive_tel() {
        return sensitive_tel;
    }

    public void setSensitive_tel(String sensitive_tel) {
        this.sensitive_tel = sensitive_tel;
    }

    /**
     * Desc: 是否身份认证/实名认证
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-04
     *
     * @return boolean
     */
    public boolean isVerified() {
        return identity_auth == 1 || (user_details_vo != null && user_details_vo.getIdentity_auth() == 1);
    }

    /**
     * Desc: 是否关注
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-29
     *
     * @return
     */
    public boolean isSubscribe() {
        return subscribe == 1;
    }

    /**
     * Desc: 是否是男性
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-17
     *
     * @return boolean
     */
    public boolean isMale() {
        return gender == GenderType.MALE;
    }

    /**
     * Desc: 是否是女性
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-17
     *
     * @return boolean
     */
    public boolean isFemale() {
        return gender == GenderType.FEMALE;
    }

    /**
     * Desc: 是否认证
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-29
     *
     * @return
     */
    public boolean isIdentity() {
        return play_ident == 1 || (user_details_vo != null && user_details_vo.isIdentity());
    }


    /**
     * Desc: 获取性别文字
     * <p>
     * Author: 廖培坤
     * Date: 2018-10-08
     *
     * @return string
     */
    public String getGenderText() {
        return isMale() ? "男" : (isFemale() ? "女" : "未知");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User o = (User) obj;
        return TextUtils.equals(user_id, o.getUser_id());
    }


    public boolean isIs_modify_gender() {
        return is_modify_gender;
    }

    public void setIs_modify_gender(boolean is_modify_gender) {
        this.is_modify_gender = is_modify_gender;
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public boolean isIs_bind_tel() {
        return is_bind_tel;
    }

    public void setIs_bind_tel(boolean is_bind_tel) {
        this.is_bind_tel = is_bind_tel;
    }

    public boolean isIs_bind_qq() {
        return is_bind_qq;
    }

    public void setIs_bind_qq(boolean is_bind_qq) {
        this.is_bind_qq = is_bind_qq;
    }

    public boolean isIs_bind_alipay() {
        return is_bind_alipay;
    }

    public void setIs_bind_alipay(boolean is_bind_alipay) {
        this.is_bind_alipay = is_bind_alipay;
    }

    public boolean isIs_bind_weixin() {
        return is_bind_weixin;
    }

    public void setIs_bind_weixin(boolean is_bind_weixin) {
        this.is_bind_weixin = is_bind_weixin;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLast_operation_time() {
        return last_operation_time;
    }

    public void setLast_operation_time(String last_operation_time) {
        this.last_operation_time = last_operation_time;
    }

    protected User(Parcel in) {
        this.background = in.readByte() != 0;
        this.is_modify_gender = in.readByte() != 0;
        this.is_bind_tel = in.readByte() != 0;
        this.is_bind_qq = in.readByte() != 0;
        this.is_bind_weixin = in.readByte() != 0;
        this.is_bind_alipay = in.readByte() != 0;
        this.tel = in.readString();
        this.sensitive_tel = in.readString();
        this.birthday = in.readString();
        this.count_fans = in.readInt();
        this.count_subscribe = in.readInt();
        this.count_visitor = in.readInt();
        this.gender = in.readInt();
        this.invitation_code = in.readString();
        this.invite_user = in.readParcelable(User.class.getClassLoader());
        this.user_details_vo = in.readParcelable(User.class.getClassLoader());
        this.age = in.readInt();
        this.nick_name = in.readString();
        this.photo = in.readString();
        this.subscribe = in.readInt();
        this.uid = in.readString();
        this.user_id = in.readString();
        this.reg_time = in.readString();
        this.rongyuntoken = in.readString();
        this.area = in.readString();
        this.identity_auth = in.readInt();
        this.introduction = in.readString();
        this.last_operation_time = in.readString();
        this.is_partner = in.readString();
        this.is_today_signin = in.readInt();
        this.play_ident = in.readInt();
        this.voice_sign_path = in.readString();
        this.skill_tags = in.createStringArrayList();
        this.constellation = in.readString();
        this.online_tip = in.readString();
        this.level = in.readString();
        this.is_live = in.readByte() != 0;
        this.price = in.readString();
        this.average = in.readDouble();
        this.is_black = in.readByte() != 0;
        this.is_blacked = in.readByte() != 0;
        this.task_level_icon = in.readString();
        this.sum_task_point = in.readDouble();
        this.is_official = in.readByte() != 0;
        this.is_test = in.readByte() != 0;
        this.vip_expired_at = in.readString();
        this.vip_type = in.readInt();
        this.order_count = in.readInt();
    }

    public boolean isIs_test() {
        return is_test;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getCount_fans() {
        return count_fans;
    }

    public void setCount_fans(int count_fans) {
        this.count_fans = count_fans;
    }

    public int getCount_subscribe() {
        return count_subscribe;
    }

    public void setCount_subscribe(int count_subscribe) {
        this.count_subscribe = count_subscribe;
    }

    public int getCount_visitor() {
        return count_visitor;
    }

    public void setCount_visitor(int count_visitor) {
        this.count_visitor = count_visitor;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getInvitation_code() {
        return invitation_code;
    }

    public void setInvitation_code(String invitation_code) {
        this.invitation_code = invitation_code;
    }

    public User getInvite_user() {
        return invite_user;
    }

    public User getUserDetailsVo() {
        return user_details_vo;
    }

    public void setInvite_user(User invite_user) {
        this.invite_user = invite_user;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getRongyuntoken() {
        return rongyuntoken;
    }

    public void setRongyuntoken(String rongyuntoken) {
        this.rongyuntoken = rongyuntoken;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getIdentity_auth() {
        return identity_auth;
    }

    public void setIdentity_auth(int identity_auth) {
        this.identity_auth = identity_auth;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIs_partner() {
        return is_partner;
    }

    public void setIs_partner(String is_partner) {
        this.is_partner = is_partner;
    }

    public boolean isSignToday() {
        return is_today_signin == 1;
    }

    public int getIs_today_signin() {
        return is_today_signin;
    }

    public String getVoice_sign_path() {
        return voice_sign_path;
    }

    public void setVoice_sign_path(String voice_sign_path) {
        this.voice_sign_path = voice_sign_path;
    }


    public void setSubscribe(boolean isSubscribe) {
        this.subscribe = isSubscribe ? 1 : 0;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getOnline_tip() {
        return online_tip;
    }

    public void setOnline_tip(String online_tip) {
        this.online_tip = online_tip;
    }

    public void setPlay_ident(int play_ident) {
        this.play_ident = play_ident;
    }




    public boolean isIs_live() {
        return is_live;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }



    public void setIs_live(boolean is_live) {
        this.is_live = is_live;
    }




    public String getPrice() {
        return price;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public boolean getIsBlack() {
        return is_black;
    }

    public void setIs_black(boolean is_black) {
        this.is_black = is_black;
    }

    public boolean getIsBlacked() {
        return is_blacked;
    }

    public void setIsBlacked(boolean is_blacked) {
        this.is_blacked = is_blacked;
    }

    public void setIs_today_signin(int is_today_signin) {
        this.is_today_signin = is_today_signin;
    }

    public ArrayList<String> getSkill_tags() {
        return skill_tags;
    }

    public String getTask_level_icon() {
        return task_level_icon;
    }

    public void setTask_level_icon(String task_level_icon) {
        this.task_level_icon = task_level_icon;
    }

    public double getSum_task_point() {
        return sum_task_point;
    }

    public void setSum_task_point(double sum_task_point) {
        this.sum_task_point = sum_task_point;
    }

    public boolean isIs_official() {
        return is_official;
    }

    public void setIs_official(boolean is_official) {
        this.is_official = is_official;
    }

    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public int getOrder_count() {
        return order_count;
    }

    public String getVip_expired_at() {
        return vip_expired_at;
    }







    public void setPrice(String price) {
        this.price = price;
    }




    public void setSkill_tags(ArrayList<String> skill_tags) {
        this.skill_tags = skill_tags;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public void setIs_test(boolean is_test) {
        this.is_test = is_test;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setVip_expired_at(String vip_expired_at) {
        this.vip_expired_at = vip_expired_at;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.background ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_modify_gender ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_bind_tel ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_bind_qq ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_bind_weixin ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_bind_alipay ? (byte) 1 : (byte) 0);
        dest.writeString(this.tel);
        dest.writeString(this.sensitive_tel);
        dest.writeString(this.birthday);
        dest.writeInt(this.count_fans);
        dest.writeInt(this.count_subscribe);
        dest.writeInt(this.count_visitor);
        dest.writeInt(this.gender);
        dest.writeString(this.invitation_code);
        dest.writeParcelable(this.invite_user, flags);
        dest.writeParcelable(this.user_details_vo, flags);
        dest.writeInt(this.age);
        dest.writeString(this.nick_name);
        dest.writeString(this.photo);
        dest.writeInt(this.subscribe);
        dest.writeString(this.uid);
        dest.writeString(this.user_id);
        dest.writeString(this.reg_time);
        dest.writeString(this.rongyuntoken);
        dest.writeString(this.area);
        dest.writeInt(this.identity_auth);
        dest.writeString(this.introduction);
        dest.writeString(this.last_operation_time);
        dest.writeString(this.is_partner);
        dest.writeInt(this.is_today_signin);
        dest.writeInt(this.play_ident);
        dest.writeString(this.voice_sign_path);
        dest.writeStringList(this.skill_tags);
        dest.writeString(this.constellation);
        dest.writeString(this.online_tip);
        dest.writeString(this.level);
        dest.writeByte(this.is_live ? (byte) 1 : (byte) 0);
        dest.writeString(this.price);
        dest.writeDouble(this.average);
        dest.writeByte(this.is_black ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_blacked ? (byte) 1 : (byte) 0);
        dest.writeString(this.task_level_icon);
        dest.writeDouble(this.sum_task_point);
        dest.writeByte(this.is_official ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_test ? (byte) 1 : (byte) 0);
        dest.writeString(this.vip_expired_at);
        dest.writeInt(this.vip_type);
        dest.writeInt(this.order_count);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
