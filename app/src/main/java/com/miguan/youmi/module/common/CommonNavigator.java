package com.miguan.youmi.module.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.libalum.AlbumSelectorActivity;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.ARouterPaths;
import com.miguan.youmi.app.constant.ActivityRequestCode;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.app.constant.ExtraConstant;
import com.miguan.youmi.bean.Image;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.util.EventBusUtil;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

import static com.miguan.youmi.app.constant.ActivityRequestCode.START_IMAGE_CORP;

public class CommonNavigator {

    /**
     * Desc: 打开网页
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @param url
     */
    public void openWebActivity(String url) {
        ARouter.getInstance()
                .build(ARouterPaths.COMMON_WEB)
                .withString(ExtraConstant.WEB_URL, url)
                .navigation();
    }

    public void openAlbumActivity(Activity activity, int requestCode, int max, boolean showVideo, boolean onlyVideo) {
        openAlbumActivity(activity, requestCode, max, showVideo, onlyVideo, false);
    }

    public void openAlbumActivity(Activity activity, int requestCode, int max, boolean showVideo, boolean onlyVideo, boolean isNeedCrop) {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.CAMERA)
                .rationale(shouldRequest -> postAlbumPermissionEvent())
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(activity, AlbumSelectorActivity.class);
                        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, max);
                        intent.putExtra(AlbumSelectorActivity.INTENT_ISHOW_ADD, true);
                        intent.putExtra(AlbumSelectorActivity.INTENT_EXTRA_SHOW_VIDEO, showVideo);
                        intent.putExtra(AlbumSelectorActivity.INTENT_EXTRA_NEED_CROP, isNeedCrop);
                        intent.putExtra(AlbumSelectorActivity.INTENT_EXTRA_ONLY_SHOW_VIDEO, onlyVideo);
                        activity.startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onDenied() {
                        postAlbumPermissionEvent();
                    }
                })
                .request();
    }

    private void postAlbumPermissionEvent() {
        EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_album);
    }

    /**
     * Desc: 打开相册
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-16
     *
     * @param activity
     * @param max       可选择最大图片数
     * @param showVideo 是否显示视频
     * @param onlyVideo 是否只显示视频
     */
    public void openAlbumActivity(FragmentActivity activity, int max, boolean showVideo, boolean onlyVideo) {
        openAlbumActivity(activity, ActivityRequestCode.ALUM_SELECT_CODE, max, showVideo, onlyVideo);
    }

    /**
     * Desc: 打开相册
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-16
     *
     * @param activity
     */
    public void openAlbumWithVideo(FragmentActivity activity, int requestCode) {
        openAlbumActivity(activity, requestCode, 9, true, false);
    }

    /**
     * Desc:打开相册，完成后剪裁图片
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-31
     */
    public void openAlbumWithCrop(Activity activity) {
        openAlbumWithCrop(activity, ActivityRequestCode.ALUM_SELECT_CODE);
    }

    /**
     * Desc:打开相册，完成后剪裁图片
     * <p>
     * Author: QiuRonaC
     * Date: 2018-09-27
     */
    public void openAlbumWithCrop(Activity activity, int requestCode) {
        openAlbumActivity(activity, requestCode, 1
                , false, false, true);
    }

    /**
     * Desc: 打开裁剪图片
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-10
     *
     * @param activity
     * @param uri
     */
    public void openImageCrop(Activity activity, Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 640);
        intent.putExtra("outputY", 640);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("scaleUpIfNeeded", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Desc: 打开裁剪图片
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-10
     *
     * @param activity
     * @param uri
     */
    public void openImageCrop(Activity activity, Uri uri) {
        openImageCrop(activity, uri, START_IMAGE_CORP);
    }

    /**
     * Desc: 打开图片预览
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-08
     *
     * @param imageArrayList
     * @param position       这里的position从0开始
     */
    public void openPreviewImageActivity(ArrayList<Image> imageArrayList, int position) {
        ARouter.getInstance()
                .build(ARouterPaths.COMMON_PREVIEW_IMAGE)
                .withInt(ExtraConstant.PREVIEW_IMAGE_POSITION, position)
                .withParcelableArrayList(ExtraConstant.PREVIEW_IMAGE_DATA, imageArrayList)
                .navigation();
    }

    /**
     * Desc: 查看单图大图
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-24
     *
     * @param image
     */
    public void openPreviewImageActivity(Image image) {
        ArrayList<Image> list = new ArrayList<>();
        list.add(image);
        openPreviewImageActivity(list, 0);
    }

    /**
     * Desc: 查看单图大图
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-24
     *
     * @param path
     */
    public void openPreviewImageActivity(String path) {
        Image image = new Image();
        image.setPath(path);
        openPreviewImageActivity(image);
    }






    /**
     * Desc: 打开微信
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-08
     *
     * @param activity
     */
    public void openWechat(Activity activity) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            activity.startActivity(intent);
        } else {
            PickToast.show("未安装微信");
        }
    }

    public void openQQ(Activity activity) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            PickToast.show("未安装QQ");
        }
    }


}
