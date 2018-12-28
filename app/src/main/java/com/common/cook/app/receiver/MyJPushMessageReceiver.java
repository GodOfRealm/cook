package com.common.cook.app.receiver;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.common.cook.app.ServicesObserver;
import com.common.cook.app.constant.Constant;
import com.common.cook.bean.account.User;
import com.common.cook.util.CommonUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import io.reactivex.Observable;

public class MyJPushMessageReceiver extends JPushMessageReceiver {

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        String alias = jPushMessage.getAlias();
        LogUtils.d("alias: " + alias);
        if (jPushMessage.getErrorCode() == 0) {
            setAlias(context, jPushMessage.getSequence(), alias);
        } else if (jPushMessage.getErrorCode() == 6002 || jPushMessage.getErrorCode() == 6014) {
            //返回的错误码为6002 超时,6014 服务器繁忙,都建议延迟重试
            reTry(context, jPushMessage.getSequence(), alias);
        }
        super.onAliasOperatorResult(context, jPushMessage);
    }

    private void setAlias(Context context, int sequence, String alias) {
        User user = CommonUtils.getUser();
        if (user != null) {
            if (TextUtils.isEmpty(alias) || !alias.equals(user.getUser_id())) {
                JPushInterface.setAlias(context, sequence, user.getUser_id());
                Set<String> tagSet = new LinkedHashSet<>();
                tagSet.add(Constant.DEBUG ? "debug" : "");
                JPushInterface.setTags(context, sequence, tagSet);
            }
        }
    }

    private void reTry(Context context, int sequence, String alias) {
        Observable.just("")
                .delay(60, TimeUnit.SECONDS)
                .subscribe(new ServicesObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        setAlias(context, sequence, alias);
                    }

                    @Override
                    public void onError(int code, String msg) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }
                });
    }

}
