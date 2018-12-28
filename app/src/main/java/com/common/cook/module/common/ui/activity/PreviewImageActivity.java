package com.common.cook.module.common.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jess.arms.di.component.AppComponent;
import com.common.cook.R;
import com.common.cook.app.BaseActivity;
import com.common.cook.app.ViewConfig;
import com.common.cook.app.constant.ARouterPaths;
import com.common.cook.app.constant.Constant;
import com.common.cook.app.constant.EventBusTags;
import com.common.cook.app.constant.ExtraConstant;

import com.common.cook.bean.Download;
import com.common.cook.bean.Image;
import com.common.cook.module.common.contract.DownloadContract;
import com.common.cook.module.common.contract.PreviewImageContract;

import com.common.cook.module.common.di.component.DaggerPreviewImageComponent;
import com.common.cook.module.common.di.module.DownloadModule;
import com.common.cook.module.common.di.module.PreviewImageModule;
import com.common.cook.module.common.presenter.DownloadPresenter;
import com.common.cook.module.common.presenter.PreviewImagePresenter;
import com.common.cook.module.common.ui.adapter.PreviewImagePagerAdapter;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Desc: PhotoView全屏预览图片
 * <p>
 * Author: SonnyJack
 * Date: 2018-07-19
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@Route(path = ARouterPaths.COMMON_PREVIEW_IMAGE)
public class PreviewImageActivity extends BaseActivity<PreviewImagePresenter> implements
        PreviewImageContract.View,
        DownloadContract.View,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.preview_image_vp_content)
    ViewPager mViewPager;

    @BindView(R.id.preview_image_tv_indicator)
    TextView mTextView;

    @Inject
    DownloadPresenter mDownloadPresenter;

    private ArrayList<Image> mImageArrayList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPreviewImageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .previewImageModule(new PreviewImageModule(this))
                .downloadModule(new DownloadModule(this))
                .build()
                .inject(this);
    }

    @Override
    public ViewConfig getViewConfig() {
        //setStatusBarDarkFont状态栏字体颜色亮色(白色)
        //setNotchFullScreen刘海屏的时候不全屏显示
        return super.getViewConfig().setShowStatusBar(false).setStatusBarDarkFont(false).setNotchFullScreen(false);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.common_activity_preview_image; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mImageArrayList = intent.getParcelableArrayListExtra(ExtraConstant.PREVIEW_IMAGE_DATA);
        int position = intent.getIntExtra(ExtraConstant.PREVIEW_IMAGE_POSITION, 0);
        if (null == mImageArrayList) {
            mImageArrayList = new ArrayList<>();
        }
        PreviewImagePagerAdapter previewImagePagerAdapter = new PreviewImagePagerAdapter(getActivity(), mImageArrayList);
        mViewPager.setAdapter(previewImagePagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(position);
        //position == 0 时   不会调用onPageSelected方法
        if (position == 0) {
            setPosition(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setPosition(int position) {
        mTextView.setText(position + 1 + "/" + mImageArrayList.size());
    }

    /**
     * Desc: 下载图片
     * <p>
     * Author: SonnyJack
     * Date: 2018-07-19
     */
    @OnClick(R.id.preview_image_iv_download)
    public void download() {
        int position = mViewPager.getCurrentItem();
        Image image = mImageArrayList.get(position);
        if (null == image) {
            return;
        }
        showMessage(getString(R.string.common_start_download));
        mDownloadPresenter.download(EventBusTags.DOWNLOAD_PREVIEW_IMAGE, image.getPath(), Constant.IMAGE_PATH);
    }

    @Subscriber(tag = EventBusTags.DOWNLOAD_PREVIEW_IMAGE)
    public void downloadComplete(Download download) {
        if (!download.isSuccess()) {
            showMessage(R.string.common_download_error);
            return;
        }
        showMessage(getString(R.string.common_download_success));
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(download.getLocalUrl())));
        sendBroadcast(intent);
    }
}
