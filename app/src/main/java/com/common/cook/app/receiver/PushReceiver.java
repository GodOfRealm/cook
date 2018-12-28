package com.common.cook.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.common.cook.app.constant.EventBusTags;
import com.common.cook.bean.account.User;
import com.common.cook.util.CommonUtils;
import com.common.cook.util.DaoSharedPreferences;
import com.common.cook.util.EventBusUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * Desc:自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 * <p>
 * Author: 廖培坤
 * Date: 2018-08-02
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class PushReceiver extends BroadcastReceiver {

    /**
     * 系统消息
     */
    public static final int TYPE_SYSTEM = 0;

    /**
     * 活动消息
     */
    public static final int TYPE_ACTIVE = 1;

    /**
     * 接单
     */
    public static final int TYPE_ORDER_ACCEPT = 2;

    /**
     * 退单
     */
    public static final int TYPE_ORDER_CHARGEBACK = 3;

    /**
     * 抢单
     */
    public static final int TYPE_GRAB_NEW_TASK = 4;

    /**
     * 派单
     */
    public static final int TYPE_ORDER_ASSIGN = 5;

    /**
     * 退款
     */
    public static final int TYPE_ORDER_REFUND = 6;

    /**
     * 闪电接单
     */
    public static final int TYPE_GRAB_ORDER_ACCEPT = 7;
    /**
     * 闪电单，用户选择你啦
     */
    public static final int TYPE_GRAB_STATUS_TASK = 8;
    /**
     * 不显示通知栏的系统消息
     */
    public static final int TYPE_SYSTEM_MESSAGE = 9;

    /**
     * 动态互动消息
     */
    public static final int TYPE_FEED_INTERACTION = 10;
    /**
     * 等级升级通知
     */
    public static final int TYPE_LEVEL_UPGRADE = 11;
    /**
     * 签到通知
     */
    public static final int SIGN_IN = 12;

    public static final int TYPE_NEW_LIKE = 13;

    /**
     * 下单方申诉结果
     */
    public static final int TYPE_COMPLAINT_FROM_USER = 16;
    /**
     * 接单方申诉结果
     */
    public static final int TYPE_COMPLAINT_FROM_TALENT = 17;

    public static final int TYPE_ORDER_REFUND_AGREE = 18;//接单方退款确认(同意退款)
    public static final int TYPE_ORDER_NO_BEGIN = 19;//接单方接单确认(还没开始服务)

    @SuppressWarnings("unused")
    private static final String TAG = PushReceiver.class.getSimpleName();

    public Context mContext;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.mContext = context;
        Bundle bundle = intent.getExtras();
        LogUtils.d(PushReceiver.class, "[PushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        try {
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JSONObject extrasJson = new JSONObject(extras);
            int type;
            String value;
            type = extrasJson.optInt("type");
            value = extrasJson.optString("value");
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                receivedMessage(context, type, value);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                notifySystemUnreadNum(type);
                receivedNotification(context, type, value);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                openNotification(context, type, value);
            }
        } catch (Exception ignore) {
        }
    }

    //收到推送刷新系统未读消息数
    private void notifySystemUnreadNum(int type) {
        User user = CommonUtils.getUser();
        if (user != null) {
            Map<String, Integer> unreadMsgCount = CommonUtils.getUnreadMsgCount();
            if (unreadMsgCount == null) unreadMsgCount = new HashMap<>();
            int count;
            StringBuilder key = new StringBuilder(user.getUser_id());
            switch (type) {
                case TYPE_SYSTEM:
                case TYPE_ACTIVE:

                case TYPE_ORDER_ACCEPT:
                case TYPE_ORDER_CHARGEBACK:
                case TYPE_ORDER_REFUND:

                case TYPE_ORDER_ASSIGN:

                    break;
                case TYPE_GRAB_NEW_TASK:
                case TYPE_GRAB_STATUS_TASK:

                    break;
                case TYPE_FEED_INTERACTION:

                    break;
            }
            //如果不是已有的消息类型，则不要计数，防止后期新增类型，旧版本红点无法消除的情况
            if (TextUtils.equals(user.getUser_id(), key.toString())) {
                return;
            }
            if (unreadMsgCount.containsKey(key.toString())) {
                count = unreadMsgCount.get(key.toString()) + 1;
            } else {
                count = 1;
            }

            unreadMsgCount.put(key.toString(), count);
            CommonUtils.setUnreadMsgCount(unreadMsgCount);
            EventBusUtil.postEvent(EventBusTags.UPDATE_UNREAD_MSG);
        }
    }

    /**
     * Desc: 接收到通知消息，会在通知栏显示
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-02
     *
     * @param context
     * @param type
     * @param value
     */
    private void receivedNotification(final Context context, int type, String value) {
        switch (type) {
            case TYPE_GRAB_ORDER_ACCEPT:
                //有人接单【闪电单】
                EventBusUtil.postEvent(EventBusTags.ORDER_PUBLISH_ACCEPT, value);
                break;
            case TYPE_GRAB_NEW_TASK://抢单
            case TYPE_GRAB_STATUS_TASK://闪电单，用户选择达人你
                EventBusUtil.postEvent(EventBusTags.ORDER_GRAB_MESSAGE);
                break;
        }
        // 应用内就清除
        EventBusUtil.postAppMessage(EventBusTags.CLEAR_ALL_NOTIFICATION);
    }

    /**
     * Desc: 接收到自定义消息，不会在通知栏显示
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-02
     *
     * @param context
     * @param type
     * @param value
     */
    private void receivedMessage(final Context context, int type, String value) {
        switch (type) {
            case TYPE_SYSTEM_MESSAGE:
            case TYPE_FEED_INTERACTION:
            case TYPE_ORDER_ASSIGN://派单
                notifySystemUnreadNum(type);
                break;
            case TYPE_LEVEL_UPGRADE:
                EventBusUtil.postAppMessage(EventBusTags.COMMON_UPGRADE, value);
                break;
            case SIGN_IN:
                EventBusUtil.postEvent(EventBusTags.SIGN_IN, value);
                break;
            case TYPE_NEW_LIKE:
                String key = CommonUtils.getUserId() + DaoSharedPreferences.NEW_LIKE;
                int count = SPUtils.getInstance().getInt(key, 0) + 1;
                SPUtils.getInstance().put(key, count);
                EventBusUtil.postEvent(EventBusTags.NEW_LIKE);
                break;
            case TYPE_COMPLAINT_FROM_USER:
                break;
            case TYPE_COMPLAINT_FROM_TALENT:
                break;
        }
    }



    /**
     * Desc: 点击任务栏跳转处理
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-02
     *
     * @param context
     * @param type    0-系统消息 1-活动 2-接单 3-退单 4-抢单 5-派单 6-退款
     * @param value
     */
    private void openNotification(Context context, int type, String value) {
        switch (type) {
            case TYPE_SYSTEM:
//                MsgSystem msgSystem = GsonUtil.parse(value, MsgSystem.class);
//                CommonHelper.INSTANCE.openSystemMsg(msgSystem, true);
                break;
            case TYPE_ACTIVE:
//                Navigator.getInstance().getCommonNavigator().openWebActivity(value);
//                CommonUtils.clearUnreadMsgByType(UnreadMsgType.SYSTEM);
//                EventBusUtil.postEvent(EventBusTags.UPDATE_UNREAD_MSG);
                break;
            case TYPE_ORDER_ACCEPT://接单
            case TYPE_ORDER_CHARGEBACK://退单
            case TYPE_ORDER_REFUND://退款
            case TYPE_GRAB_STATUS_TASK://闪电单，用户选择达人你
            case TYPE_ORDER_REFUND_AGREE://接单方退款确认(同意退款)  type = 1 -> 同意   type = 2 -> 拒绝
                break;
            case TYPE_GRAB_NEW_TASK://有人发布闪电单了，跳到抢单中心抢单
                break;
            case TYPE_ORDER_ASSIGN:
                //派单中心
                break;
            case TYPE_GRAB_ORDER_ACCEPT:
                //有人接单【闪电单】 跳到发布闪电订单页面
                break;
            case TYPE_ORDER_NO_BEGIN:
                //接单方接单确认(还没开始服务)

                break;
        }
    }



    /**
     * Desc: 打印所有Intent消息
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-02
     *
     * @param bundle
     * @return string
     */
    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

}
