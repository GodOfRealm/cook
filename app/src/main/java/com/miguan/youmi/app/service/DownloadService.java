package com.miguan.youmi.app.service;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.blankj.utilcode.util.PermissionUtils;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.util.EventBusUtil;

import java.io.File;

/**
 * Copyright (c) 2017/4/27. Miguan Inc. All rights reserved.
 */

public class DownloadService extends Service {

    private BroadcastReceiver mReceiver;

    private DownloadManager mDownloadManager;

    private String mFilepath;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getExtras() != null) {
            String title = intent.getExtras().getString("title");
            String url = intent.getExtras().getString("url");
            mFilepath = intent.getExtras().getString("path");
            final String filename = intent.getExtras().getString("name");

            if (TextUtils.isEmpty(mFilepath)) {
                mFilepath = findDownLoadDirectory();
            }

            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    install(context, mFilepath, filename);
                    stopSelf();
                }
            };
            registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            startDownload(url, filename, title);
        }

        return Service.START_REDELIVER_INTENT;
    }

    /**
     * 开始下载
     */
    public void startDownload(String url, String name, String title) {

        checkPer(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setMimeType("application/vnd.android.package-archive");
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setTitle(title);
                    mDownloadManager.enqueue(request);
                }
            }

            @Override
            public void onDenied() {

            }
        });
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public void install(Context context, String path, String name) {
        if (Build.VERSION.SDK_INT >= 26) {
            checkPer(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    publicApk(context, path, name);
                }

                @Override
                public void onDenied() {

                }
            });
        } else {
            publicApk(context, path, name);
        }

    }

    private void checkPer(PermissionUtils.SimpleCallback callback) {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtils.permission(permissions)
                .rationale(shouldRequest ->
                        EventBusUtil.postAppMessage(EventBusTags
                                .DEAL_WITH_NO_PERMISSION, R.string.permission_write))
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (callback != null) {
                            callback.onGranted();
                        }
                    }

                    @Override
                    public void onDenied() {
                        EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_write);
                    }
                })
                .request();
    }

    private void publicApk(Context context, String path, String name) {
        checkPer(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // 由于没有在Activity环境下启动Activity,设置下面的标签
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件

                File file = new File(path, name);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri uri = FileProvider.getUriForFile(context, "com.miguan.youmi.fileprovider", file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }

                context.startActivity(intent);
            }

            @Override
            public void onDenied() {

            }
        });
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private String findDownLoadDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        } else {
            return Environment.getRootDirectory() + "/" + "download/";
        }
    }

}
