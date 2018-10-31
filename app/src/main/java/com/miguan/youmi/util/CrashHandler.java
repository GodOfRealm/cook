package com.miguan.youmi.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.format.DateUtils;

import com.blankj.utilcode.util.FileUtils;
import com.miguan.youmi.app.constant.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * Desc:崩溃日志保存
 * 路径：/storage/emulated/0/com.miguan.youmi/log
 * 每个文件保存当天的日志
 * <p>
 * Author: QiuRonaC
 * Date: 2018-08-14
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class CrashHandler {
    public static void initialCrashHandler(Context context) {
        Thread.UncaughtExceptionHandler defaultCrashHandle = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                dumpCrashLogFile(context, ex);
                if (null != defaultCrashHandle && ex != null) {
                    defaultCrashHandle.uncaughtException(thread, ex);
                }
            }

            private void dumpCrashLogFile(Context context, Throwable e) {
                final String filePath = Constant.OUTPUT_DIR + "/log/";
                String dateTime = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL);
                final String fileName = "gc_" + dateTime + ".log";
                File file = new File(filePath + fileName);
                PrintWriter pw;
                try {
                    if (!file.exists()) {
                        FileUtils.createOrExistsDir(filePath);
                        FileUtils.createOrExistsFile(file);
                    }

                    pw = new PrintWriter(new FileOutputStream(file, true));

                    SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                    format.applyPattern("y-MM-dd HH:mm:ss");
                    dateTime = format.format(System.currentTimeMillis());
                    pw.println(dateTime);
                    dumpPhoneInfo(context, pw);
                    pw.print("\r\n\r\n------------------------------------------------");

                    e.printStackTrace(pw);
                    pw.print("\r\n\r\n------------------------------------------------");
                    pw.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            private void dumpPhoneInfo(Context context, PrintWriter pw) {
                try {
                    PackageManager pm = context.getPackageManager();
                    PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                    //application version info
                    pw.print("App Version: ");
                    pw.print(pi.versionName);
                    pw.print("_");
                    pw.println(pi.versionCode);
                    //android edition
                    pw.print("OS Version: ");
                    pw.print(Build.VERSION.RELEASE);
                    pw.print("_");
                    pw.println(Build.VERSION.SDK_INT);
                    //machine factory
                    pw.print("Vendor: ");
                    pw.println(Build.MANUFACTURER);
                    //machine model
                    pw.print("Model: ");
                    pw.println(Build.MODEL);
                    //cpu abi
                    pw.print("CPU ABI: ");
                    pw.println(Build.CPU_ABI);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
