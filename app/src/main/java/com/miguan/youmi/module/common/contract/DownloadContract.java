package com.miguan.youmi.module.common.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public interface DownloadContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * Desc:
         * <p>
         * Author: SonnyJack
         * Date: 2018-09-18
         *
         * @return activity
         */
        Activity getActivity();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<ResponseBody> download(String url);
    }
}
