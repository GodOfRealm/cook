package com.miguan.youmi.core.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Desc:缓存操作类
 * <p>
 * Author: QiuRonaC
 * Date: 2018-08-13
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class CacheUtil {

    /**
     * Desc:获取app缓存路径
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-13
     *
     * @return file
     */
    public static File getDiskCacheDir(Context context) {
        File cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir();
        } else {
            cachePath = context.getCacheDir();
        }
        return cachePath;
    }

    /**
     * Desc:获取格式化后的app缓存大小
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-13
     */
    public static String getCacheSize(Context context) {
        return FileUtils.getDirSize(getDiskCacheDir(context));
    }

    /**
     * Desc:删除app缓存
     * <p>
     * Author: QiuRonaC
     * Date: 2018-08-13
     */
    public static void deleteCache(Context context) {
        File cacheDir = getDiskCacheDir(context);
        if (cacheDir != null) {
            // 删除Glide文件夹会导致部分手机加载不了图片
            Glide.get(context).clearDiskCache();
            File[] list = cacheDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return !TextUtils.equals(name, "Glide");
                }
            });
            for (File aList : list) {
                if (aList.isFile()) {
                    //noinspection ResultOfMethodCallIgnored
                    aList.delete();
                } else {
                    FileUtils.deleteDir(aList);
                }
            }
        }
    }
}
