package com.libalum;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * 图片加载帮助类
 * Created by ZuoZiJi-Y.J on 2016/4/5.
 */
public class ImageLoadHelper {
    //region 服务器获取图片大小
    public static final String SUF_S1 = "!s1";
    public static final String SUF_S2 = "!s2";
    public static final String SUF_S3 = "!S3";
    public static final String SUF_S4 = "!s4";
    public static final String SUF_S6 = "!s6";
    public static final String SUF_L1 = "!L1";
    public static final String SUF_L2 = "!L2";
    public static final String SUF_L3 = "!L3";
    public static final String SUF_V4 = "!V4";
    public static final String SUF_V6 = "!V6";
    public static final String SUF_AV6 = "!AV6";
    public static final String SUF_V7 = "!V7";
    public static final String SUF_AV7 = "!AV7";
    public static final String SUF_XX = "!XX";
    public static final String SUF_XL = "!XL";
    public static final String SUF_LT = "!LT";
    public static final String SUF_W7 = "!W7";
    public static final String SUF_W8 = "!W8";
    public static final String SUF_V9 = "!V9";
    public static final String SUF_W6 = "!W6";
    public static final String SUF_HM = "!hm";
    public static final String SUF_VM = "!vm";
    private static final String TAG = ImageLoadHelper.class.getSimpleName();

    //region 图片加载
    public static RequestManager with(Context context) {
        return Glide.with(context);
    }

    public static RequestManager with(Activity activity) {
        return Glide.with(activity);
    }

    public static RequestManager with(FragmentActivity activity) {
        return Glide.with(activity);
    }

    @TargetApi(11)
    public static RequestManager with(Fragment fragment) {
        return Glide.with(fragment);
    }

    public static RequestManager with(android.support.v4.app.Fragment fragment) {
        return Glide.with(fragment);
    }

    public static void load(Context context, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url is Empty!");
            return;
        }
        if (context == null) {
            Log.e(TAG, "context is NULL");
            return;
        }
        if (imageView == null) {
            Log.e(TAG, "imageView is NULL");
            return;
        }

        try {
            with(context).load(url).into(imageView);
        } catch (Exception e) {

        }

    }

    @TargetApi(11)
    public static void load(Fragment fragment, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url is Empty!");
            return;
        }
        if (fragment == null) {
            Log.e(TAG, "context is NULL");
            return;
        }
        if (imageView == null) {
            Log.e(TAG, "imageView is NULL");
            return;
        }

        try {
            with(fragment).load(url).into(imageView);
        } catch (Exception e) {

        }

    }

    public static void load(android.support.v4.app.Fragment fragment, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            Log.e(TAG, "url is Empty!");
            return;
        }
        if (fragment == null) {
            Log.e(TAG, "context is NULL");
            return;
        }
        if (imageView == null) {
            Log.e(TAG, "imageView is NULL");
            return;
        }
        try {
            with(fragment).load(url).into(imageView);
        } catch (Exception e) {

        }

    }

    /**
     * 用户小头像 <p>
     * 120×120,指定宽高缩放,居中剪裁
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufS1(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }

        if (url.endsWith(SUF_S1) || url.endsWith("S1")) {
            return url;
        }
        return url + SUF_S1;
    }

    /**
     * 发现首页用户头像 <p>
     * 240×240,指定宽高缩放,居中剪裁
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufS2(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_S2) || url.endsWith("S2")) {
            return url;
        }
        return url + SUF_S2;
    }

    /**
     * 动态投票项、左右选项: <p>
     * 使用方式: 原图URL+“!S3”
     * 使用: 图片缩略
     * 规格:320×240,指定宽高缩放
     */
    public static String getPictureUrlOfSufS3(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_S3)) {
            return url;
        }
        return url + SUF_S3;
    }

    public static String getPictureUrlOfSufS4(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_S4)) {
            return url;
        }
        return url + SUF_S4;
    }

    /**
     * 点赞小头像 <p>
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufS6(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }

        if (url.endsWith(SUF_S6) || url.endsWith("s6")) {
            return url;
        }
        return url + SUF_S6;
    }

    /**
     * 查看其它的用户背景图片 <p>
     * 使用方式: 原图URL+“L1”
     * 处理方式： 指定宽高缩放,居中剪裁。
     * 图片尺寸： 宽度780px , 高度1020px
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufL1(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_L1)) {
            return url;
        }
        return url + SUF_L1;
    }

    /**
     * 邀约图片列表 <p>
     * 使用方式: 原图URL+“L2”
     * 处理方式： 指定宽高缩放,居中剪裁。
     * 图片尺寸： 宽度780px , 高度585px
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufL2(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_L2)) {
            return url;
        }
        return url + SUF_L2;
    }

    /**
     * 查看其它的用户展示图片大图、邀约图片大图 <p>
     * 使用方式: 原图URL+“L3”
     * 处理方式： 指定宽高缩放。
     * 图片尺寸： 宽度780px , 高度1020px
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufL3(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_L3)) {
            return url;
        }
        return url + SUF_L3;
    }

    /**
     * 长文限宽大图 <p>
     * 使用方式: 原图URL+“LT”
     * 处理方式： 限定宽度绽放。
     * 图片尺寸： 宽度980px, 高度自适应
     *
     * @param url
     * @return
     */
    public static String getPictureUrlOfSufLT(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (url.endsWith(SUF_L3)) {
            return url;
        }
        return url + SUF_L3;
    }
    //endregion

}
