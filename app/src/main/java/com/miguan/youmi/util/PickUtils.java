package com.miguan.youmi.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.utils.ArmsUtils;
import com.meituan.android.walle.WalleChannelReader;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.app.constant.ExtraConstant;
import com.miguan.youmi.bean.account.GenderType;
import com.miguan.youmi.bean.account.Token;
import com.miguan.youmi.bean.account.User;
import com.miguan.youmi.bean.main.ServerConfig;
import com.miguan.youmi.core.util.GsonUtil;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


import static android.Manifest.permission.READ_PHONE_STATE;

/**
 * Desc: APP特有的辅助方法
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class PickUtils {

    private static Context sContext;

    private static String sImei = "";

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param application
     */
    public static void init(Application application) {
        sContext = application.getApplicationContext();
        DaoSharedPreferences.init(sContext);
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @return 获取青牛文件域名
     */
    public static String getUdomain() {
        return getServerConfig().getUdomain();
    }

    /**
     * Desc: 获得配置数据
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @return server config
     */
    public static ServerConfig getServerConfig() {
        ServerConfig config = (ServerConfig) ArmsUtils.obtainAppComponentFromContext(sContext).extras().get(ExtraConstant.SERVER_CONFIG);
        if (config == null) {
            config = DaoSharedPreferences.getInstance().getConfig();
        }
        return config;
    }

    /**
     * Desc: 设置配置数据
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-09
     *
     * @param config
     */
    public static void setServerConfig(ServerConfig config) {
        ArmsUtils.obtainAppComponentFromContext(sContext).extras().put(ExtraConstant.SERVER_CONFIG, config);
        DaoSharedPreferences.getInstance().setConfig(config);
    }

    /**
     * Desc: 保存本地登录信息到本地
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param token
     */
    public static void setToken(Token token) {
        ArmsUtils.obtainAppComponentFromContext(sContext).extras().put(ExtraConstant.TOKEN_ENTITY, token);
        DaoSharedPreferences.getInstance().setToken(token);
    }

    /**
     * Desc: 获得本地登录实体
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @return user
     */
    public static Token getTokenEntity() {
        Token token = (Token) ArmsUtils.obtainAppComponentFromContext(sContext).extras().get(ExtraConstant.TOKEN_ENTITY);
        if (token == null) {
            token = DaoSharedPreferences.getInstance().getToken();
        }
        return token;
    }

    /**
     * Desc: 获取登录Token
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-24
     *
     * @return string
     */
    public static String getToken() {
        return getTokenEntity() != null && !TextUtils.isEmpty(getTokenEntity().getToken()) ? getTokenEntity().getToken() : "";
    }

    /**
     * Desc: 判断是否登录
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-24
     *
     * @return boolean
     */
    public static boolean isLogin() {
        return getTokenEntity() != null && !TextUtils.isEmpty(getTokenEntity().getToken()) && !TextUtils.isEmpty(getUserId());
    }

    /**
     * Desc: 保存客服数据
     * <p>
     * Author: 张文顺
     * Date: 2018-07-04
     *
     * @param customerList
     */
    public static void setCustomerList(@NonNull String customerList) {
        SPUtils.getInstance().put(DaoSharedPreferences.CUSTOMER_SERVICE_USER_INFO, customerList);
    }


    /**
     * Desc: 保存本地用户数据
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @param user
     */
    public static void setUser(@NonNull User user) {
        ArmsUtils.obtainAppComponentFromContext(sContext).extras().put(ExtraConstant.USER_INFO, user);
        DaoSharedPreferences.getInstance().setUser(user);

    }


    /**
     * Desc: 更新任务等级
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-30
     *
     * @param sum_task_point
     * @param taskLevelIcon
     */
    public static void updateTaskLevel(double sum_task_point, String taskLevelIcon) {
        User user = getUser();
        if (null == user) {
            return;
        }
        user.setSum_task_point(sum_task_point);
        user.setTask_level_icon(taskLevelIcon);
        setUser(user);
    }

    /**
     * Desc: 退出登录删除本地数据
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-03
     */
    public static void logout() {
        setToken(null);
        ArmsUtils.obtainAppComponentFromContext(sContext).extras().put(ExtraConstant.USER_INFO, null);
        DaoSharedPreferences.getInstance().setUser(null);
        SensorsDataAPI.sharedInstance().logout();
    }

    /**
     * Desc: 获得本地登录用户
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-04
     *
     * @return user
     */
    public static User getUser() {
        User user = (User) ArmsUtils.obtainAppComponentFromContext(sContext).extras().get(ExtraConstant.USER_INFO);
        if (user == null) {
            user = DaoSharedPreferences.getInstance().getUser();
            ArmsUtils.obtainAppComponentFromContext(sContext).extras().put(ExtraConstant.USER_INFO, user);
        }
        return user;
    }

    /**
     * Desc: 保存最后一个登陆用户的用户信息
     * <p>
     * Author: 张文顺
     * Date: 2018-07-04
     *
     * @param user
     * @param sensitive_tel
     */
    public static void setLastUser(@NonNull User user, String sensitive_tel) {
        DaoSharedPreferences.getInstance().setLastUser(user, sensitive_tel);
    }

    /**
     * Desc: 获得最后一个登陆用户的用户信息
     * <p>
     * Author: 张文顺
     * Date: 2018-07-04
     *
     * @param sensitive_tel
     * @return user
     */
    public static User getLastUser(String sensitive_tel) {
        return DaoSharedPreferences.getInstance().getLastUser(sensitive_tel);
    }

    /**
     * 获取最后一个登陆用户的手机
     *
     * @return string
     */
    public static String getLastUserTel() {
        return DaoSharedPreferences.getInstance().getLastUserTel();
    }

    /**
     * 设置最后一个登陆用户的手机
     *
     * @param sensitive_tel
     */
    public static void setLastUserTel(String sensitive_tel) {
        DaoSharedPreferences.getInstance().setLastUserTel(sensitive_tel);
    }

    /**
     * Desc: 根据手机号码匹配最后登陆用户的头像
     * <p>
     * Author: 张文顺
     * Date: 2018-07-04
     *
     * @param sensitive_tel
     * @return user
     */
    public static String matchLastPhoto(String sensitive_tel) {
        User lastUser = PickUtils.getLastUser(sensitive_tel);
        if (lastUser == null) {
            return null;
        } else {
            if (TextUtils.equals(lastUser.getSensitive_tel(), sensitive_tel)) {
                return lastUser.getPhoto();
            }
        }
        return null;
    }

    /**
     * Desc: 获得本地登录用户ID
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-17
     *
     * @return string
     */
    public static String getUserId() {
        User user = getUser();
        if (null == user) {
            return "";
        }
        return user.getUser_id();
    }

    /**
     * Desc: 获得本地登录用户头像
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-17
     *
     * @return string
     */
    public static String getUserPhoto() {
        User user = getUser();
        if (null == user) {
            return "";
        }
        return user.getPhoto();
    }


    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-10-25
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(READ_PHONE_STATE)
    public static void setImei() {
        String deviceId;
        try {
            deviceId = PhoneUtils.getDeviceId();
        } catch (Exception e) {
            deviceId = DeviceUtils.getAndroidID();
        }
        sImei = TextUtils.isEmpty(deviceId) ? DeviceUtils.getAndroidID() : deviceId;
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @return string
     */
    @SuppressLint("MissingPermission")
    public static String getImei() {
        String key = "last_check_time";
        long time = SPUtils.getInstance().getLong(key, 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (isLogin() && (currentTimeMillis - time >= 24 * 60 * 60 * 1000 * 7)) {
            PermissionUtils.permission(PermissionConstants.PHONE)
                    .rationale(rationale -> EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_phone))
                    .callback(new PermissionUtils.SimpleCallback() {
                        @Override
                        public void onGranted() {
                            //sImei = PhoneUtils.getIMEI();
                            setImei();
                        }

                        @Override
                        public void onDenied() {
                            EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_phone);
                        }
                    })
                    .request();
            SPUtils.getInstance().put(key, currentTimeMillis);
        } else if (time <= 0) {
            SPUtils.getInstance().put(key, currentTimeMillis);
        }

        return sImei;
    }

    /**
     * Desc: 获取内存中的未读消息
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-08
     *
     * @return int
     */
    public static Map<String, Integer> getUnreadMsgCount() {
        String key = (PickUtils.getUser() != null ? PickUtils.getUser().getUser_id() : "") + "unread_msg_count";
        String readStr = SPUtils.getInstance().getString(key, "");
        Map<String, Integer> unreadList = GsonUtil.parse(readStr, new TypeToken<Map<String, Integer>>() {
        }.getType());
        return unreadList;
    }

    /**
     * Desc: 设置未读消息到内存中
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-08
     *
     * @param unreadMsgList
     */
    public static void setUnreadMsgCount(Map<String, Integer> unreadMsgList) {
        String key = (PickUtils.getUser() != null ? PickUtils.getUser().getUser_id() : "") + "unread_msg_count";

        SPUtils.getInstance().put(key, unreadMsgList == null ? "" : GsonUtil.toJson(unreadMsgList));
    }


    /**
     * 获取渠道
     *
     * @return
     */
    public static String getChannel() {
        String channel = WalleChannelReader.getChannel(sContext);
        if (channel == null) {
            channel = "MG18_pick_001";//官网渠道
        }
        return channel;
    }

    /**
     * Desc: 获得是否需要更新
     * <p>
     * Author: 张文顺
     * Date: 2018-08-09
     *
     * @return server config
     */
    public static boolean isNeedUpdate() {
        return (boolean) ArmsUtils.obtainAppComponentFromContext(sContext).extras().get(ExtraConstant.NEED_UPDATE);
    }

    /**
     * Desc: 设置是否需要更新
     * <p>
     * Author: 张文顺
     * Date: 2018-08-09
     *
     * @param update
     */
    public static void setNeedUpdate(boolean update) {
        ArmsUtils.obtainAppComponentFromContext(sContext).extras().put(ExtraConstant.NEED_UPDATE, update);
    }


    /**
     * Desc: 通过字符串获取性别类型
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-17
     *
     * @param gender
     * @return int
     */
    public static @GenderType.Val
    int getGender(String gender) {
        if (gender.equals("男")) {
            return GenderType.MALE;
        } else if (gender.equals("女")) {
            return GenderType.FEMALE;
        } else {
            return GenderType.UNKNOWN;
        }
    }


    /**
     * 获取文字长度 <p>
     * 中文为2字节，英文为1字节
     *
     * @param ch
     * @return
     */
    public static int getStringLength(CharSequence ch) {
        if (!TextUtils.isEmpty(ch)) {
            int varlength = 0;
            for (int i = 0; i < ch.length(); i++) {
                char c = ch.charAt(i);
                // changed by zyf 0825 , bug 6918，加入中文标点范围 ， TODO 标点范围有待具体化
                if (c > 0x7F) {
                    varlength += 2;
                } else {
                    varlength++;
                }
            }
            return varlength;
        }
        return 0;
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @param context
     * @return boolean
     */
    public static boolean isNotificationEnabled(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                ApplicationInfo appInfo = context.getApplicationInfo();
                String pkg = context.getApplicationContext().getPackageName();
                int uid = appInfo.uid;
                Class appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE,
                        String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            }
        } catch (Exception ignore) {

        }
        return false;
    }

    /**
     * Desc:是否接收新消息提示
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-10
     *
     * @return boolean
     */
    public static boolean isReceiveNotification() {
        return DaoSharedPreferences.getInstance().isNewMessage();
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @param isNewMessage
     */
    public static void setIsReceiveNotification(boolean isNewMessage) {
        DaoSharedPreferences.getInstance().setisNewMessage(isNewMessage);
        if (sContext != null) {
            if (!isNewMessage) {
                JPushInterface.stopPush(sContext);
            } else {
                JPushInterface.resumePush(sContext);
            }
        }
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @return boolean
     */
    public static boolean isPlaySound() {
        return DaoSharedPreferences.getInstance().isTone();
    }

    /**
     * Desc: 设置是否播放提示音
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-10
     *
     * @param isTone
     */
    public static void setIsPlaySound(boolean isTone) {
        DaoSharedPreferences.getInstance().setIsTone(isTone);
    }

    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @return boolean
     */
    public static boolean isVibration() {
        return DaoSharedPreferences.getInstance().isVibration();
    }

    /**
     * Desc:设置新消息是否震动
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-11
     *
     * @param vibration
     */
    public static void setIsVibration(boolean vibration) {
        DaoSharedPreferences.getInstance().setIsVibration(vibration);
        if (vibration) {
            JPushInterface.setSilenceTime(sContext, 0, 0, 0, 0);
        } else {
            JPushInterface.setSilenceTime(sContext, 0, 0, 23, 0);
        }
    }


    /**
     * Desc:
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @return boolean
     */
    public static boolean isShowContent() {
        return DaoSharedPreferences.getInstance().isNotification();
    }

    /**
     * Desc:格式转换
     * <p>
     * Author: 张文顺
     * Date: 2018-08-27
     *
     * @param value
     * @return string
     */
    public static String doubleFormat(double value) {
        return String.format(Locale.CHINA, "%.2f", value);
    }

    /**
     * Desc:  double价格转为int类型
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-05
     *
     * @param price
     * @return int
     */
    public static int formatPrice(double price) {
        int result = (int) price;
        return result;
    }

    /**
     * Desc:View的Context转为Activity
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-28
     *
     * @param view
     * @return activity
     */
    public static Activity getActivity(View view) {
        if (null == view) {
            return null;
        }
        // Gross way of unwrapping the Activity so we can get the FragmentManager
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * Desc: 判断是否是网络地址
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-28
     *
     * @param url
     * @return boolean
     */
    public static boolean isHttpUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith("http://") || url.startsWith("https://");
    }

    /**
     * Desc: 网络地址添加参数
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-10
     *
     * @param httpUrl
     * @param key
     * @param value
     * @return string
     */
    public static String addParamsForHttpUrl(String httpUrl, String key, String value) {
        if (TextUtils.isEmpty(httpUrl)) {
            return httpUrl;
        }
        StringBuilder stringBuilder = new StringBuilder(httpUrl);
        if (httpUrl.indexOf("?") >= 0) {
            stringBuilder.append("&&");
        } else {
            stringBuilder.append("?");
        }
        stringBuilder.append(key)
                .append("=")
                .append(value);
        return stringBuilder.toString();
    }

    /**
     * Desc:输入控制上下限
     * <p>
     * Author: 张文顺
     * Date: 2018-11-06
     *
     * @param edit
     * @param MIN_MARK
     * @param MAX_MARK
     */
    public static void setRegion(final EditText edit, final double MIN_MARK, final double MAX_MARK) {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start > 1) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double num = Double.parseDouble(s.toString());
                        if (num > MAX_MARK) {
                            s = String.valueOf(new DecimalFormat("0.00").format(MAX_MARK));
                            edit.setText(s);
                        } else if (num < MIN_MARK) {
                            s = String.valueOf(new DecimalFormat("0.00").format(MIN_MARK));
                            edit.setText(s);
                        }
                        edit.setSelection(s.length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("")) {
                    if (MIN_MARK != -1 && MAX_MARK != -1) {
                        double markVal = 0;
                        try {
                            markVal = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            markVal = 0;
                        }
                        if (markVal > MAX_MARK) {
                            String s1 = String.valueOf(new DecimalFormat("0.00").format(MAX_MARK));
                            edit.setText(s1);
                            edit.setSelection(s1.length());
                        }
                        return;
                    }
                }
            }
        });
    }
}
