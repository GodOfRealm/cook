package com.libalum;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WL on 2016/4/7.
 */
public class ImageSDK {
    public static final String KEY_PICTURE_ALREADY_SELECT = "key_picture_already_select";
    public static final String KEY_PICTURE_ARRAYLIST = "key_picture_arraylist";//获取多张图片
    public static final String KEY_PICTURE_IMAGE = "key_picture_image";//获取单张图片
    public static final String KEY_OPEN_MULTIPLE = "key_open_multiple";//是否打开多功能编辑器
    public static final String KEY_OPEN_JIG_MULTIPLE = "key_open_jig_multiple";//是否打开多功能编辑器下的拼图界面
    public static final String KEY_IMAGE_EDIT_ACTION = "key_image_edit_action";//是否打开多功能编辑器
    public static final String KEY_IMAGE_WIDTH = "key_image_width";//裁切图片宽度
    public static final String KEY_IMAGE_HEIGHT = "key_image_height";//裁切图片高度
    public static final String KEY_IMAGE_QUITITY = "key_image_quility";//图片质量
    public static final String KEY_IMAGE_COMPRESS_OPTION = "key_image_compress_option"; //图片压缩选项
    public static final String KEY_RESULT_COMPRESS_STATE = "key_result_compress_state";//处理结果是否压缩
    public static final String KEY_IS_FRONT_POSTION = "key_is_front_postion";//是否前置摄像头
    public static final String KEY_FRAME_ID = "key_frame_id";  //相框id
    public static final String KEY_FRAME_GROUP_ID = "key_frame_group_id";  //封面分组id
    public static final String KEY_MAX_NUM = "key_max_num";  //最多几张
    public static final String KEY_IS_SHOW_DIY = "key_is_show_diy";//是否显示div字
    public static final String KEY_RESULT_IS_VIDEO = "key_result_is_video";//返回图片地址类型：视频或图片
    public static final String KEY_VIDEO_PATH = "key_video_path";

    public static final String ALBUM_NAME = "DIY_PIC";
    public static final int REQUEST_CODE = 100;
    public static final int RESULT_CODE = -1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 102;
    public static final int REQUEST_CODE_TAKE_VIDEO = 103;//选取了一个视频
    //1.注册头像, 2.更换背景, 3.更换头像, 4.上传照片, 5.发布动态, 6.发活动上传照片 7.聊天发图片
    public static final int KEY_FROM_DIY = 501; //DIY功能
    public static final String KEY_FROM_PAGE = "KEY_FROM_PAGE";
    public static int MaxNum = 9;//图片上限

    /**
     * 多选图片界面(发布动态图片...)
     * onActivityResult拦截返回的图片,ImageSdk.getImage(intent)获取图片
     * onActivityResult 返回 ImageSDK.RESULT_CODE
     *
     * @param activity
     * @param requestCode
     * @param maxImageCount
     * @param alreadySelectNum
     * @param option
     */
    public static void showMultiAlbum(Activity activity, int requestCode, int maxImageCount, int alreadySelectNum, ImageCompressOption option, int from) {
        Intent intent = new Intent(activity, AlbumSelectorActivity.class);
        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, maxImageCount);
        intent.putExtra(KEY_PICTURE_ALREADY_SELECT, alreadySelectNum);
        intent.putExtra(KEY_IMAGE_EDIT_ACTION, ImageEditAction.Type_MultiPhotoEditer.name());
        intent.putExtra(KEY_FROM_PAGE, from);
        intent.putExtra(KEY_IMAGE_COMPRESS_OPTION, option);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 单选图片界面（动态投票、头像...）
     * onActivityResult拦截返回的图片,ImageSdk.getImage(intent)获取图片
     * onActivityResult 返回 ImageSDK.RESULT_CODE
     *
     * @param activity
     * @param requestCode
     * @param width
     * @param height
     * @param quility
     */
    public static void showSingleAlbumAndCut(final Activity activity, int requestCode, int width, int height, int quility, int from) {
        showSingleAlbumAndCut(activity, requestCode, width, height, quility, false, from);
    }

    /**
     * 单选图片界面（动态投票、头像...）
     * onActivityResult拦截返回的图片,ImageSdk.getImage(intent)获取图片
     * onActivityResult 返回 ImageSDK.RESULT_CODE
     *
     * @param activity
     * @param requestCode
     * @param width
     * @param height
     * @param quility
     */
    public static void showSingleAlbumAndCut(final Activity activity, int requestCode, int width, int height, int quility, boolean isFront, int from) {
        Intent intent = new Intent(activity, AlbumSelectorActivity.class);
        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, 1);
        intent.putExtra(KEY_PICTURE_ALREADY_SELECT, 0);
        intent.putExtra(KEY_IMAGE_EDIT_ACTION, ImageEditAction.Type_CutAndFither.name());
        intent.putExtra(KEY_FROM_PAGE, from);
        intent.putExtra(KEY_IMAGE_WIDTH, width);
        intent.putExtra(KEY_IMAGE_HEIGHT, height);
        intent.putExtra(KEY_IMAGE_QUITITY, quility);
        intent.putExtra(KEY_IS_FRONT_POSTION, isFront);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 打开编辑界面并弹出封面编辑
     */
    public static void showSingleAlbumFrame(final Activity activity, int requestCode, int frameId, int from) {
        Intent intent = new Intent(activity, AlbumSelectorActivity.class);
        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, 1);
        intent.putExtra(KEY_PICTURE_ALREADY_SELECT, 0);
        intent.putExtra(KEY_IMAGE_EDIT_ACTION, ImageEditAction.Type_MultiPhotoEditerAndFrame.name());
        intent.putExtra(KEY_FRAME_ID, frameId);
        intent.putExtra(ImageSDK.KEY_IS_SHOW_DIY, false);
        intent.putExtra(KEY_FROM_PAGE, from);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 直播打开合拍相册
     * 选取单张图片,不可编辑跟DIV
     */
    public static void showSingleAlbum(final Activity activity, int requestCode) {
        Intent intent = new Intent(activity, AlbumSelectorActivity.class);
        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, 1);
        intent.putExtra(KEY_PICTURE_ALREADY_SELECT, 0);
        intent.putExtra(KEY_IMAGE_EDIT_ACTION, ImageEditAction.Type_NoEditer.name());
        intent.putExtra(ImageSDK.KEY_IS_SHOW_DIY, false);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 多功能编辑界面 + 按钮进入多选图片界面
     *
     * @param fragment
     * @param imageCount
     * @param alreadSelectList
     */
    public static void showMultiAlbum(Fragment fragment, int requestCode, int imageCount, List<String> alreadSelectList) {
        Intent intent = new Intent(fragment.getActivity(), AlbumSelectorActivity.class);
        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, imageCount);
        intent.putExtra(KEY_PICTURE_ALREADY_SELECT, alreadSelectList.size());
        intent.putExtra(KEY_IMAGE_EDIT_ACTION, ImageEditAction.Type_MultiPhotoEditer.name());
        intent.putExtra(ImageSDK.KEY_OPEN_MULTIPLE, false);
        intent.putExtra(KEY_IS_SHOW_DIY, false);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 多功能编辑界面---> 拼图引导界面 --> 添加图片 ---> 进入多选图片界面
     *
     * @param fragment
     * @param imageCount
     * @param alreadSelectList
     */
    public static void showJigMultiAlbum(Fragment fragment, int requestCode, int imageCount, List<String> alreadSelectList) {
        Intent intent = new Intent(fragment.getActivity(), AlbumSelectorActivity.class);
        intent.putExtra(AlbumSelectorActivity.INTENT_MAX_NUM, imageCount);
        intent.putExtra(KEY_PICTURE_ALREADY_SELECT, alreadSelectList.size());
        intent.putExtra(KEY_IMAGE_EDIT_ACTION, ImageEditAction.Type_MultiPhotoEditer.name());
        intent.putExtra(ImageSDK.KEY_OPEN_MULTIPLE, false);
        intent.putExtra(KEY_IS_SHOW_DIY, false);
        intent.putExtra(KEY_OPEN_JIG_MULTIPLE, true);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 多选图片界面 返回的所有图片（动态图片...）
     */
    public static ArrayList<String> getImages(Intent intent) {
        return intent.getStringArrayListExtra(KEY_PICTURE_ARRAYLIST);
    }

    /**
     * 单选图片界面 返回的单张图片（动态投票、头像...）
     *
     * @param intent
     * @return
     */
    public static String getImage(Intent intent) {
        if (intent == null) return null;
        return intent.getStringExtra(KEY_PICTURE_IMAGE);
    }

    public static boolean isActivityResultCode(int resultCode) {
        return (resultCode == ImageSDK.RESULT_CODE);
    }

    /**
     * 不用
     */
    public static void showMultiAlbum(Activity activity, int maxImageCount, int alreadySelectNum, int requestCode, int from) {
        showMultiAlbum(activity, requestCode, maxImageCount, alreadySelectNum, null, from);
    }

    /**
     * 图片处理结果是否有压缩
     *
     * @param intent
     * @return 结果 true 有压缩  false 无压缩
     */
    public boolean isResultCompressState(Intent intent) {
        if (intent == null) return false;
        return intent.getBooleanExtra(KEY_RESULT_COMPRESS_STATE, false);
    }

}
