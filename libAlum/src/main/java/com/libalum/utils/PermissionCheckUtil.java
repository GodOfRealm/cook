package com.libalum.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.blankj.utilcode.util.AppUtils;
import com.libalum.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2017/11/13. LiaoPeiKun Inc. All rights reserved.
 */

public class PermissionCheckUtil {
    private static final String TAG = PermissionCheckUtil.class.getSimpleName();

    public PermissionCheckUtil() {
    }

    public static boolean requestPermissions(Fragment fragment, @NonNull String[] permissions) {
        return requestPermissions(fragment, permissions, 0);
    }

    @TargetApi(23)
    public static boolean requestPermissions(Fragment fragment, @NonNull final String[] permissions, final int requestCode) {
        if (VERSION.SDK_INT < 23) {
            return true;
        } else if (permissions != null && permissions.length != 0) {
            final Activity context = fragment.getActivity();
            List<String> permissionsNotGranted = new ArrayList();
            final int[] requestResults = new int[permissions.length];
            boolean shouldShowRequestPermissionRationale = false;
            boolean result = false;

            for (int i = 0; i < permissions.length; ++i) {
                requestResults[i] = context.checkCallingOrSelfPermission(permissions[i]);
                if (requestResults[i] != 0) {
                    permissionsNotGranted.add(permissions[i]);
                    if (!shouldShowRequestPermissionRationale && !context.shouldShowRequestPermissionRationale(permissions[i])) {
                        shouldShowRequestPermissionRationale = true;
                    }
                }
            }

            if (shouldShowRequestPermissionRationale) {
                OnClickListener listener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case -2:
                                context.onRequestPermissionsResult(requestCode, permissions, requestResults);
                                break;
                            case -1:
                                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                                intent.setData(uri);
                                context.startActivityForResult(intent, requestCode > 0 ? requestCode : -1);
                        }

                    }
                };
                showPermissionAlert(context, context.getResources().getString(R.string.pic_grant_needed) + getNotGrantedPermissionMsg(context, permissionsNotGranted), listener);
            } else if (permissionsNotGranted.size() > 0) {
                context.requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), requestCode);
            } else {
                result = true;
            }

            return result;
        } else {
            return true;
        }
    }

    public static boolean requestPermissions(Activity activity, @NonNull String[] permissions) {
        return requestPermissions(activity, permissions, 0);
    }

    @TargetApi(23)
    public static boolean requestPermissions(final Activity activity, @NonNull final String[] permissions, final int requestCode) {
        if (VERSION.SDK_INT < 23) {
            return true;
        } else if (permissions != null && permissions.length != 0) {
            List<String> permissionsNotGranted = new ArrayList();
            final int[] requests = new int[permissions.length];
            boolean shouldShowRequestPermissionRationale = false;
            boolean result = false;

            for (int i = 0; i < permissions.length; ++i) {
                requests[i] = activity.checkCallingOrSelfPermission(permissions[i]);
                if (requests[i] != 0) {
                    permissionsNotGranted.add(permissions[i]);
                    if (!shouldShowRequestPermissionRationale && !activity.shouldShowRequestPermissionRationale(permissions[i])) {
                        shouldShowRequestPermissionRationale = true;
                    }
                }
            }

            if (shouldShowRequestPermissionRationale) {
                OnClickListener listener = new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case -2:
                                activity.onRequestPermissionsResult(requestCode, permissions, requests);
                                break;
                            case -1:
                                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                intent.setData(uri);
                                activity.startActivityForResult(intent, requestCode > 0 ? requestCode : -1);
                        }

                    }
                };
                showPermissionAlert(activity, activity.getResources().getString(R.string.pic_grant_needed) + getNotGrantedPermissionMsg(activity, permissionsNotGranted), listener);
            } else if (permissionsNotGranted.size() > 0) {
                activity.requestPermissions(permissionsNotGranted.toArray(new String[permissionsNotGranted.size()]), requestCode);
            } else {
                result = true;
            }

            return result;
        } else {
            return true;
        }
    }

    public static boolean checkPermissions(Context context, @NonNull String[] permissions) {
        if (VERSION.SDK_INT < 23) {
            return true;
        } else if (permissions != null && permissions.length != 0) {
            String[] var2 = permissions;
            int var3 = permissions.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String permission = var2[var4];
                if (context.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    private static String getNotGrantedPermissionMsg(Context context, List<String> permissions) {
        Set<String> permissionsValue = new HashSet();
        Iterator var4 = permissions.iterator();

        while (var4.hasNext()) {
            String permission = (String) var4.next();
            String permissionValue = context.getString(context.getResources().getIdentifier("rc_" + permission, "string", context.getPackageName()), Integer.valueOf(0));
            permissionsValue.add(permissionValue);
        }

        String result = "(";

        String value;
        for (Iterator var8 = permissionsValue.iterator(); var8.hasNext(); result = result + value + " ") {
            value = (String) var8.next();
        }

        result = result.trim() + ")";
        return result;
    }

    @TargetApi(11)
    private static void showPermissionAlert(Context context, String content, final OnClickListener listener) {
        new Builder(context).setMessage(content).setPositiveButton(R.string.pic_btn_ok, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onClick(dialog, which);
                }
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.pic_btn_cancel, listener).setCancelable(false).create().show();
    }

    public static boolean canDrawOverlays(Context context) {
        return canDrawOverlays(context, true);
    }

    public static boolean canDrawOverlays(final Context context, boolean needOpenPermissionSetting) {
        boolean result = true;
        if (VERSION.SDK_INT >= 23) {
            try {
                boolean booleanValue = Settings.canDrawOverlays(context);
                if (!booleanValue && needOpenPermissionSetting) {
                    ArrayList<String> permissionList = new ArrayList<>();
                    permissionList.add(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    showPermissionAlert(context, context.getString(R.string.pic_grant_needed)
                            + getNotGrantedPermissionMsg(context, permissionList), new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (-1 == which) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }
                    });
                }

                return booleanValue;
            } catch (Exception var7) {
                return false;
            }
        } else if (VERSION.SDK_INT < 19) {
            return true;
        } else {
            Object systemService = context.getSystemService(Context.APP_OPS_SERVICE);

            Method method;
            try {
                method = Class.forName("android.app.AppOpsManager").getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
            } catch (NoSuchMethodException var9) {
                method = null;
            } catch (ClassNotFoundException var10) {
                var10.printStackTrace();
                method = null;
            }

            if (method != null) {
                try {
                    Integer tmp = (Integer) method.invoke(systemService, new Object[]{Integer.valueOf(24), Integer.valueOf(context.getApplicationInfo().uid), context.getPackageName()});
                    result = tmp.intValue() == 0;
                } catch (Exception var8) {
                }
            }

            return result;
        }
    }

    public static void openPermissionSetting(Activity activity) {
        if (VERSION.SDK_INT >= 23) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity.getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } else {
            AppUtils.launchAppDetailsSettings();
        }
    }
}
