package com.miguan.youmi.util;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;

import com.jess.arms.integration.AppManager;

import org.simple.eventbus.EventBus;

/**
 * 发送event_bus事件工具
 * Created by liyu on 15/8/13.
 */
public class EventBusUtil {

    public static void postEvent(String eventTag) {
        postEvent(eventTag, null);
    }

    public static void postEvent(String eventTag, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        EventBus.getDefault().post(bundle, eventTag);
    }

    public static void postEvent(String eventTag, @NonNull Object object) {
        EventBus.getDefault().post(object, eventTag);
    }

    /**
     * Desc: 发送AppManager事件
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-11
     *
     * @param event
     */
    public static void postAppMessage(int event) {
        Message message = new Message();
        message.what = event;
        AppManager.post(message);
    }

    /**
     * Desc: 发送AppManager事件
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-11
     *
     * @param event
     */
    public static void postAppMessage(int event, Object object) {
        Message message = new Message();
        message.what = event;
        message.obj = object;
        AppManager.post(message);
    }

}
