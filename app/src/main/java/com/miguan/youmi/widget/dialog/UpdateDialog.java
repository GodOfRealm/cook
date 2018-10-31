package com.miguan.youmi.widget.dialog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.PermissionUtils;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.app.constant.ExtraConstant;
import com.miguan.youmi.app.service.DownloadService;
import com.miguan.youmi.bean.user.VersionBean;
import com.miguan.youmi.core.widget.radius.RadiusTextView;
import com.miguan.youmi.util.EventBusUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 更新弹窗
 * 作者: zws 2018/7/12 0012 11:22
 * 功能描述:
 * 备注:
 */
public class UpdateDialog extends BaseDialog {
    @BindView(R.id.user_tv_version_update_dialog_version)
    TextView mTvVersion;
    @BindView(R.id.user_tv_version_update_dialog_message_content)
    TextView mTvUpdateMessageContent;
    @BindView(R.id.user_tv_version_update_dialog_update)
    RadiusTextView mTvUpdateRightNow;
    private VersionBean mVersion; // 版本号

    public static UpdateDialog newInstance(Context context, VersionBean version) {
        UpdateDialog dialog = new UpdateDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraConstant.EXTRA_VERSION, version);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.user_dialog_version_update;
    }

    @Override
    protected void initView(View view, AlertDialog dialog) {

        if (getArguments() != null) {
            mVersion = getArguments().getParcelable(ExtraConstant.EXTRA_VERSION);
            if (!TextUtils.isEmpty(mVersion.getVersion_number()))
                mTvVersion.setText(String.format("v.%s", mVersion.getVersion_number()));
            if (!TextUtils.isEmpty(mVersion.getContent()))
                mTvUpdateMessageContent.setText(mVersion.getContent());
            setCancelable(!mVersion.isMandatory());
        }

    }

    @OnClick({R.id.user_tv_version_update_dialog_update})
    public void onViewCicked(View view) {
        switch (view.getId()) {
            case R.id.user_tv_version_update_dialog_update:
                downLoadApk();
                dismiss();
                break;
        }
    }

    private void downLoadApk() {
        String[] permissions = new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtils.permission(permissions)
                .rationale(shouldRequest ->
                        EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_write))
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        if (mVersion.getUrl().startsWith("http:") || mVersion.getUrl().startsWith("https:")) {
                            PickToast.show("开始在后台下载");
                            Intent intent = new Intent(getContext(), DownloadService.class);
                            intent.putExtra("title", "正在下载" + getContext().getString(R.string.app_name));
                            intent.putExtra("url", mVersion.getUrl());
                            intent.putExtra("path", findDownLoadDirectory());
                            intent.putExtra("name", "pick" + System.currentTimeMillis() + ".apk");
                            getContext().startService(intent);
                        } else {
                            PickToast.show("后台返回链接有误");
                        }
                    }

                    @Override
                    public void onDenied() {
                        EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_write);
                    }
                })
                .request();
    }

    private String findDownLoadDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        } else {
            return Environment.getRootDirectory() + "/" + "download/";
        }
    }
}
