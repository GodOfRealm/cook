package com.miguan.youmi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.miguan.youmi.app.constant.ExtraConstant;
import com.miguan.youmi.bean.account.Token;
import com.miguan.youmi.bean.account.User;
import com.miguan.youmi.bean.main.ServerConfig;
import com.miguan.youmi.core.util.GsonUtil;

/**
 * Copyright (c) 2018 Miguan Inc All rights reserved.
 * Created by Liaopeikun on 2018/6/21
 */
public class DaoSharedPreferences {

    private static Context sContext;

    private static DaoSharedPreferences sInstance;

    private SharedPreferences mPreferences;

    private SharedPreferences.Editor mEditor;

    private static final String NAME = "MimiDb";

    private static final String TOKEN = "token";
    private static final String USER = "user";
    private static final String VIDEO_AUTOPLAY_TYPE = "video_autoplay_type";//视频自动播放类型
    private static final String IS_TONE = "is_tone"; // 是否开启提示音
    private static final String IS_VIBRATION = "is_vibration"; // 是否开启振动
    private static final String IS_NEWMESSAGE = "is_newmessage"; // 是否接收新消息提示
    private static final String IS_NOTIFICATION = "is_notification"; // 是否通知显示消息详情
    private static final String SHOW_GUIDE = "first_login"; //是否显示引导页
    private static final String LAST_USER = "last_user"; // 最后一个登陆用户的信息
    private static final String LAST_USER_SENSITIVE_TEL = "last_user_sensitive_tel"; // 最后一个登陆用户的手机号码
    public static final String CUSTOMER_SERVICE_USER_INFO = "customer_service_user_info";
    public static final String NEW_LIKE = "new_like";
    public static final String IS_SPLASH_VOICE = "is_splash_voice"; // 是否播放启动音

    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    private DaoSharedPreferences() {
        if (sContext == null) {
            throw new RuntimeException("请在Application中初始化!");
        }
        mPreferences = sContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static DaoSharedPreferences getInstance() {
        if (sInstance == null) {
            sInstance = new DaoSharedPreferences();
        }

        return sInstance;
    }

    /**
     * 是否第一次登录
     *
     * @return
     */
    public boolean isFirstLogin() {
        return mPreferences.getBoolean(SHOW_GUIDE, true);
    }

    /**
     * 设置第一次登录
     */
    public void setFirstLogin() {
        mEditor.putBoolean(SHOW_GUIDE, false);
        mEditor.apply();
    }


    /**
     * 获取登录令牌
     */
    public Token getToken() {
        Token token = null;
        String userInfoStr = mPreferences.getString(TOKEN, "");
        if (!TextUtils.isEmpty(userInfoStr)) {
            token = GsonUtil.parse(userInfoStr, Token.class);
        }
        return token;
    }

    /**
     * 设置登录信息
     *
     * @param token
     */
    public void setToken(Token token) {
        mEditor.putString(TOKEN, token == null ? "" : GsonUtil.toJson(token));
        mEditor.apply();
    }

    /**
     * Desc: 获取所有配置
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @return server config
     */
    public ServerConfig getConfig() {
        ServerConfig ServerConfig = null;
        String config = SPUtils.getInstance().getString(ExtraConstant.SERVER_CONFIG, "");
        if (!TextUtils.isEmpty(config)) {
            ServerConfig = GsonUtil.parse(config, ServerConfig.class);
        }
        return ServerConfig;
    }

    /**
     * Desc: 设置配置
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @param config
     */
    public void setConfig(ServerConfig config) {
        SPUtils.getInstance().put(ExtraConstant.SERVER_CONFIG, config == null ? "" : GsonUtil.toJson(config));
    }

    /**
     * 获取用户信息
     */
    public User getUser() {
        User userInfo = null;
        String userInfoStr = mPreferences.getString(USER, "");
        if (!TextUtils.isEmpty(userInfoStr)) {
            userInfo = GsonUtil.parse(userInfoStr, User.class);
        }
        return userInfo;
    }

    /**
     * 设置用户信息
     *
     * @param user
     */
    public void setUser(User user) {
        mEditor.putString(USER, user == null ? "" : GsonUtil.toJson(user));
        mEditor.apply();
    }

    /**
     * 获取最后一个登陆用户的信息
     */
    public User getLastUser(String sensitive_tel) {
        User userInfo = null;
        String userInfoStr = mPreferences.getString(LAST_USER + sensitive_tel, "");
        if (!TextUtils.isEmpty(userInfoStr)) {
            userInfo = GsonUtil.parse(userInfoStr, User.class);
        }
        return userInfo;
    }

    /**
     * 设置最后一个登陆用户的用户信息
     *
     * @param user
     */
    public void setLastUser(User user, String sensitive_tel) {
        mEditor.putString(LAST_USER + sensitive_tel, user == null ? "" : GsonUtil.toJson(user));
        mEditor.apply();
    }

    /**
     * 获取最后一个登陆用户的手机
     */
    public String getLastUserTel() {
        return mPreferences.getString(LAST_USER_SENSITIVE_TEL, "");
    }

    /**
     * 设置最后一个登陆用户的手机
     */
    public void setLastUserTel(String sensitive_tel) {
        mEditor.putString(LAST_USER_SENSITIVE_TEL, sensitive_tel);
        mEditor.apply();
    }

    /**
     * 获取视频自动播放，0->使用移动流量和Wifi时自动播放,1->仅wifi自动播放,2->关闭自动播放,默认wifi播放
     */
    public int getVideoAutoPlayType() {
        return mPreferences.getInt(VIDEO_AUTOPLAY_TYPE, 1);
    }

    /**
     * 设置视频自动播放，0->使用移动流量和Wifi时自动播放,1->仅wifi自动播放,2->关闭自动播放
     *
     * @param type
     */
    public void setVideoAutoPlayType(int type) {
        mEditor.putInt(VIDEO_AUTOPLAY_TYPE, type);
        mEditor.apply();
    }

    /**
     * 是否开启提示音
     *
     * @return
     */
    public boolean isTone() {
        User user = PickUtils.getUser();
        return mPreferences.getBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_TONE + user.getUid() : IS_TONE, true);
    }

    public void setIsTone(boolean isTone) {
        User user = PickUtils.getUser();
        mEditor.putBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_TONE + user.getUid() : IS_TONE, isTone);
        mEditor.apply();
    }

    /**
     * 是否开启振动
     *
     * @return
     */
    public boolean isVibration() {
        User user = PickUtils.getUser();
        return mPreferences.getBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_VIBRATION + user.getUid() : IS_VIBRATION, true);
    }

    public void setIsVibration(boolean isVibration) {
        User user = PickUtils.getUser();
        mEditor.putBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_VIBRATION + user.getUid() : IS_VIBRATION, isVibration);
        mEditor.apply();
    }

    /**
     * 是否开启接收新消息提示
     *
     * @return
     */
    public boolean isNewMessage() {
        User user = PickUtils.getUser();
        return mPreferences.getBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_NEWMESSAGE + user.getUid() : IS_NEWMESSAGE, true);
    }

    public void setisNewMessage(boolean isNewMessage) {
        User user = PickUtils.getUser();
        mEditor.putBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_NEWMESSAGE + user.getUid() : IS_NEWMESSAGE, isNewMessage);
        mEditor.apply();
    }

    /**
     * 是否通知显示消息详情
     *
     * @return
     */
    public boolean isNotification() {
        User user = PickUtils.getUser();
        return mPreferences.getBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_NOTIFICATION + user.getUid() : IS_NOTIFICATION, true);
    }

    public void setisNotification(boolean isNotification) {
        User user = PickUtils.getUser();
        mEditor.putBoolean((user != null && !TextUtils.isEmpty(user.getUid())) ? IS_NOTIFICATION + user.getUid() : IS_NOTIFICATION, isNotification);
        mEditor.apply();
    }

}
