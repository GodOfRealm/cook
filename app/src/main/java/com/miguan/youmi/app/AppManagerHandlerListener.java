package com.miguan.youmi.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.jess.arms.integration.AppManager;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.app.http.HttpResponseCode;

import com.miguan.youmi.widget.dialog.CustomDialog;
import com.miguan.youmi.widget.dialog.CustomDialogType;

import cn.jpush.android.api.JPushInterface;

import static com.miguan.youmi.app.constant.EventBusTags.CLEAR_ALL_NOTIFICATION;
import static com.miguan.youmi.app.constant.EventBusTags.DEAL_WITH_NO_PERMISSION;
import static com.miguan.youmi.app.constant.EventBusTags.EVENT_RE_LOGIN;
import static com.miguan.youmi.app.constant.EventBusTags.KICKED_OFFLINE_BY_OTHER_CLIENT;
import static com.miguan.youmi.app.constant.EventBusTags.ON_LOGIN_SUCCESS;

/**
 * Desc: 全局事件处理，通过{@link com.jess.arms.integration.AppManager#post}发布事件
 * 通过 Message 对象中不同的 what 区分不同的方法和 Handler 同理
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-04
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class AppManagerHandlerListener implements AppManager.HandleListener {

    @Override
    public void handleMessage(AppManager appManager, Message message) {
        switch (message.what) {
            case KICKED_OFFLINE_BY_OTHER_CLIENT:
                showReLoginDialog(appManager, "当前用户在其他设备上登录，请确保信息安全");
                break;
            case HttpResponseCode.ERROR_USER_DISABLE: // 用户被封号了
                showReLoginDialog(appManager, (String) message.obj);
                break;
            // 退出重新登录
            case EVENT_RE_LOGIN:
                reLogin(appManager);
                break;
            case DEAL_WITH_NO_PERMISSION:
                showPermissionDialog(appManager, (Integer) message.obj);
                break;
            case CLEAR_ALL_NOTIFICATION: // 应用内会清掉通知
                Activity currentActivity = appManager.getCurrentActivity();
                if (currentActivity != null) {
                    JPushInterface.clearAllNotifications(currentActivity);
                }
                break;
            case ON_LOGIN_SUCCESS:
//                Navigator.getInstance().getMainNavigator().openMainActivity(appManager.getCurrentActivity(),
//                        message.obj != null && (boolean) message.obj, null);
//                PickToast.show("登录成功");
                break;

            case EventBusTags.FINISH_ALL_ACTIVITY_EXCEPT_MAIN:
                //关闭所有的Activity(除了MainActivity)
//                appManager.killAll(MainActivity.class);
                break;
            case EventBusTags.OPEN_MAIN_ACTIVITY:
                //打开MainActivity
                Bundle bundle = (Bundle) message.obj;
//                Navigator.getInstance().getMainNavigator().openMainActivity(appManager.findActivity(MainActivity.class), false, bundle);
        }
    }

    private void showReLoginDialog(AppManager appManager, String msg) {
//        CustomDialog dialog = CustomDialog.newInstance(CustomDialogType.TYPE_SINGLE);
//        dialog.setCancelable(false);
//        dialog.setCanTouchOutside(false);
//        dialog.setContentText(msg);
//        PickUtils.logout();
//        dialog.setRightButton(R.string.relogin, v -> {
//            if (ChatRoomManager.getInstance().isInChatRoom()) {
//                ChatRoomManager.getInstance().clearChatRoomInfo();
//            }
//            reLogin(appManager);
//        });
//        dialog.showDialog(((AppCompatActivity) appManager.getTopActivity()).getSupportFragmentManager());
    }

    private void reLogin(AppManager appManager) {
//        Activity currentActivity = appManager.getCurrentActivity();
//        JPushInterface.deleteAlias(currentActivity, 0); // 删除推送
//        PickUtils.logout();
//        Navigator.getInstance().getAccountNavigator().openLoginHomeActivity();
//        appManager.killAll(LoginByCodeActivity.class);
    }

    private void showPermissionDialog(AppManager appManager, int stringRes) {
        Activity activity = appManager.findActivity(PermissionUtils.PermissionActivity.class);
        appManager.removeActivity(activity);
        AppCompatActivity currentActivity = (AppCompatActivity) appManager.getTopActivity();
        CustomDialog dialog = CustomDialog.newInstance(CustomDialogType.TYPE_DOUBLE);
        dialog.setContentText(stringRes);
        dialog.setLeftButton(R.string.cancel, null);
        dialog.setRightButton(R.string.dialog_permission_to_setting, view -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + AppUtils.getAppPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(currentActivity.getPackageManager()) != null) {
                currentActivity.startActivity(intent);
            }
            dialog.dismiss();
        });
        dialog.showDialog(currentActivity.getSupportFragmentManager());
    }

}
