package com.libalum.clip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.libalum.R;
import com.common.cook.core.util.CacheUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * Desc:
 * <p>
 * Author: QiuRonaC
 * Date: 2018-08-30
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class NewCropActivity extends AppCompatActivity {

    public
    static final int RESULT_CROP = 0x11003;

    ClipImageLayout mClipImageLayout;
    private File mFileParent;

    public static void openCropActivity(Activity context, Uri data, int requestCode) {
        Intent intent = new Intent(context, NewCropActivity.class);
        intent.setData(data);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_crop);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        File cacheDir = CacheUtil.getDiskCacheDir(this);
        File parentFile = new File(cacheDir, "/head");
        FileUtils.createOrExistsDir(parentFile);
        mFileParent = new File(parentFile, System.currentTimeMillis() + ".png");
        FileUtils.createOrExistsFile(mFileParent);
        try {
            mClipImageLayout = findViewById(R.id.id_clipImageLayout);
            mClipImageLayout.setImage(this, getIntent().getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.base_toolbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((TextView) findViewById(R.id.base_toolbar_title)).setText(getTitle());
        TextView tvRight = findViewById(R.id.base_toolbar_text_right);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确定");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightClick(v);
            }
        });
    }

    public void rightClick(View view) {
        Bitmap bitmap = mClipImageLayout.clip();
        saveBitmap(bitmap);
        Intent intent = new Intent();
        intent.setData(Uri.fromFile(mFileParent));
        setResult(RESULT_CROP, intent);
        finish();
    }


    public void saveBitmap(Bitmap mBitmap) {
        try {
            byte[] bytes = ImageDisplayer.compressQualityBitmap(mBitmap, 400);
            FileOutputStream fos = new FileOutputStream(mFileParent);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}