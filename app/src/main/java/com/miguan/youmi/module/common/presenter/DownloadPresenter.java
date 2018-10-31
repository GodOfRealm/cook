package com.miguan.youmi.module.common.presenter;

import android.Manifest;
import android.text.TextUtils;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.miguan.youmi.R;
import com.miguan.youmi.app.DefaultObserverImp;
import com.miguan.youmi.app.constant.Constant;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.bean.Download;
import com.miguan.youmi.module.common.contract.DownloadContract;
import com.miguan.youmi.util.EventBusUtil;

import java.io.File;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


@ActivityScope
public class DownloadPresenter extends BasePresenter<DownloadContract.Model, DownloadContract.View> {

    @Inject
    public DownloadPresenter(DownloadContract.Model model, DownloadContract.View rootView) {
        super(model, rootView);
    }

    public void download(String action, String url) {
        download(action, url, false);
    }

    public void download(String action, String url, boolean showLoading) {
        download(action, url, null, showLoading);
    }

    public void download(String action, String url, String saveFileDir) {
        download(action, url, saveFileDir, false);
    }

    public void download(String action, String url, String saveFileDir, boolean showLoading) {
        download(action, url, saveFileDir, null, showLoading);
    }

    public void download(String action, String url, String saveFileDir, String saveFileName) {
        download(action, url, saveFileDir, saveFileName, false);
    }

    public void download(String action, String url, String saveFileDir, String saveFileName, boolean showLoading) {
        Download download = new Download();
        download.setAction(action);
        download.setUrl(url);
        download.setFileDir(saveFileDir);
        download.setFileName(saveFileName);
        download(download, showLoading);
    }

    public void download(Download download) {
        download(download, false);
    }

    public void download(Download download, boolean showLoading) {
        if (null == download || TextUtils.isEmpty(download.getUrl())) {
            return;
        }
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionUtils.permission(permissions)
                .rationale(shouldRequest -> EventBusUtil.postAppMessage(EventBusTags
                        .DEAL_WITH_NO_PERMISSION, R.string.permission_write))
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        startDownload(download, showLoading);
                    }

                    @Override
                    public void onDenied() {
                        EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_write);
                    }
                })
                .request();

    }

    private void startDownload(Download download, boolean showLoading) {
        if (TextUtils.isEmpty(download.getFileDir())) {
            download.setFileDir(Constant.DOWNLOAD_PATH);
        }
        if (TextUtils.isEmpty(download.getFileName())) {
            download.setFileName(FileUtils.getFileName(download.getUrl()));
        }
        if (showLoading) {
            mRootView.showLoading();
        }
        mModel.download(download.getUrl())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        //保存文件
                        String localUrl = null;
                        try {
                            File file = new File(download.getFileDir(), download.getFileName());
                            FileUtils.deleteFile(file);
                            InputStream inputStream = null == responseBody ? null : responseBody.byteStream();
                            boolean success = FileIOUtils.writeFileFromIS(file.getAbsolutePath(), inputStream);
                            localUrl = success ? file.getAbsolutePath() : null;
                        } catch (Exception e) {

                        }

                        download.setLocalUrl(localUrl);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new DefaultObserverImp<ResponseBody>() {
                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();
                        EventBusUtil.postEvent(download.getAction(), download);
                    }
                });
    }
}
