package com.miguan.youmi.bean.main;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Desc: 需要服务器配置的一些数据
 * <p>
 * Author: 廖培坤
 * Date: 2018-08-09
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class ServerConfig implements Parcelable {

    private String id;
    private String banner_home;
    private String title;
    private String content;
    private String udomain;
    private String version_number;
    /**
     * 用户等级说明
     */
    private String vip_description;
    private String url;
    /**
     * 分享地址
     */
    private String share_download;
    /**
     * 分享标题
     */
    private String share_title;
    /**
     * 分享内容
     */
    private String share_content;
    private String mandatory;
    /**
     * 达人服务协议
     */
    private String talent_protocol;
    /**
     * 用户头像规范
     */
    private String talent_standard;
    private String created_at;
    /**
     * 邀请活动
     */
    private String invitation;
    private String channel;
    private String photo_standard;
    private String update_required;
    /**
     * 登录注册用户协议
     */
    private String register;
    /**
     * 帮助中心
     */
    private String help_center;
    /**
     * 现金余额相关解答
     */
    private String wallet_qa;
    /**
     * 任务大厅
     */
    private String task_index;
    /**
     * 等级说明
     */
    private String level_qa;
    /**
     * vip说明
     */
    private String vip_qa;
    private int is_guide_recommend_opened; // 是否打开问卷调查 0不打开 1-打开
    private int is_invite_opened; // 是否打开邀请活动开关(1是0否)

    private int is_launch_page_opened;//是否开启启动页 0：否   1：是
    private int is_chatroom_opened;//是否开启聊天室   0：否  1：是

    private int order_wait_timeout_in_minutes;//闪电订单等待时长
    private int order_pay_timeout_in_minutes;//订单支付有效时长

    public ServerConfig() {
    }

    public String getWallet_qa() {
        return wallet_qa;
    }

    public String getVipQa() {
        return vip_qa;
    }

    public void setWallet_qa(String wallet_qa) {
        this.wallet_qa = wallet_qa;
    }

    public String getHelp_center() {
        return help_center;
    }

    public void setHelp_center(String help_center) {
        this.help_center = help_center;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner_home() {
        return banner_home;
    }

    public void setBanner_home(String banner_home) {
        this.banner_home = banner_home;
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

    public String getUdomain() {
        return udomain;
    }

    public void setUdomain(String udomain) {
        this.udomain = udomain;
    }

    public String getVersion_number() {
        return version_number;
    }

    public void setVersion_number(String version_number) {
        this.version_number = version_number;
    }

    public String getVip_description() {
        return vip_description;
    }

    public void setVip_description(String vip_description) {
        this.vip_description = vip_description;
    }

    public String getTask_index() {
        return task_index;
    }

    public void setTask_index(String task_index) {
        this.task_index = task_index;
    }

    public String getLevel_qa() {
        return level_qa;
    }

    public void setLevel_qa(String level_qa) {
        this.level_qa = level_qa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShare_download() {
        return share_download;
    }

    public void setShare_download(String share_download) {
        this.share_download = share_download;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_content() {
        return share_content;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getTalent_protocol() {
        return talent_protocol;
    }

    public void setTalent_protocol(String talent_protocol) {
        this.talent_protocol = talent_protocol;
    }

    public String getTalent_standard() {
        return talent_standard;
    }

    public void setTalent_standard(String talent_standard) {
        this.talent_standard = talent_standard;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getInvitation() {
        return invitation;
    }

    public void setInvitation(String invitation) {
        this.invitation = invitation;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPhoto_standard() {
        return photo_standard;
    }

    public void setPhoto_standard(String photo_standard) {
        this.photo_standard = photo_standard;
    }

    public String getUpdate_required() {
        return update_required;
    }

    public void setUpdate_required(String update_required) {
        this.update_required = update_required;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getIs_launch_page_opened() {
        return is_launch_page_opened;
    }

    public void setIs_launch_page_opened(int is_launch_page_opened) {
        this.is_launch_page_opened = is_launch_page_opened;
    }

    public int getIs_chatroom_opened() {
        return is_chatroom_opened;
    }

    public void setIs_chatroom_opened(int is_chatroom_opened) {
        this.is_chatroom_opened = is_chatroom_opened;
    }

    public int getOrder_wait_timeout_in_minutes() {
        return order_wait_timeout_in_minutes;
    }

    public void setOrder_wait_timeout_in_minutes(int order_wait_timeout_in_minutes) {
        this.order_wait_timeout_in_minutes = order_wait_timeout_in_minutes;
    }

    public int getOrder_pay_timeout_in_minutes() {
        return order_pay_timeout_in_minutes;
    }

    public void setOrder_pay_timeout_in_minutes(int order_pay_timeout_in_minutes) {
        this.order_pay_timeout_in_minutes = order_pay_timeout_in_minutes;
    }

    protected ServerConfig(Parcel in) {
        this.id = in.readString();
        this.banner_home = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.udomain = in.readString();
        this.version_number = in.readString();
        this.vip_description = in.readString();
        this.url = in.readString();
        this.share_download = in.readString();
        this.share_title = in.readString();
        this.share_content = in.readString();
        this.mandatory = in.readString();
        this.talent_protocol = in.readString();
        this.talent_standard = in.readString();
        this.created_at = in.readString();
        this.invitation = in.readString();
        this.channel = in.readString();
        this.photo_standard = in.readString();
        this.update_required = in.readString();
        this.register = in.readString();
        this.help_center = in.readString();
        this.wallet_qa = in.readString();
        this.task_index = in.readString();
        this.level_qa = in.readString();
        this.vip_qa = in.readString();
        this.is_guide_recommend_opened = in.readInt();
        this.is_invite_opened = in.readInt();
        this.is_launch_page_opened = in.readInt();
        this.is_chatroom_opened = in.readInt();
        this.order_wait_timeout_in_minutes = in.readInt();
        this.order_pay_timeout_in_minutes = in.readInt();
    }

    public boolean showInVite() {
        return is_invite_opened == 1;
    }

    public int getIs_guide_recommend_opened() {
        return is_guide_recommend_opened;
    }

    public void setIs_guide_recommend_opened(int is_guide_recommend_opened) {
        this.is_guide_recommend_opened = is_guide_recommend_opened;
    }

    public void setIs_invite_opened(int is_invite_opened) {
        this.is_invite_opened = is_invite_opened;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.banner_home);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.udomain);
        dest.writeString(this.version_number);
        dest.writeString(this.vip_description);
        dest.writeString(this.url);
        dest.writeString(this.share_download);
        dest.writeString(this.share_title);
        dest.writeString(this.share_content);
        dest.writeString(this.mandatory);
        dest.writeString(this.talent_protocol);
        dest.writeString(this.talent_standard);
        dest.writeString(this.created_at);
        dest.writeString(this.invitation);
        dest.writeString(this.channel);
        dest.writeString(this.photo_standard);
        dest.writeString(this.update_required);
        dest.writeString(this.register);
        dest.writeString(this.help_center);
        dest.writeString(this.wallet_qa);
        dest.writeString(this.task_index);
        dest.writeString(this.level_qa);
        dest.writeString(this.vip_qa);
        dest.writeInt(this.is_guide_recommend_opened);
        dest.writeInt(this.is_invite_opened);
        dest.writeInt(this.is_launch_page_opened);
        dest.writeInt(this.is_chatroom_opened);
        dest.writeInt(this.order_wait_timeout_in_minutes);
        dest.writeInt(this.order_pay_timeout_in_minutes);
    }

    public static final Creator<ServerConfig> CREATOR = new Creator<ServerConfig>() {
        @Override
        public ServerConfig createFromParcel(Parcel source) {
            return new ServerConfig(source);
        }

        @Override
        public ServerConfig[] newArray(int size) {
            return new ServerConfig[size];
        }
    };
}
