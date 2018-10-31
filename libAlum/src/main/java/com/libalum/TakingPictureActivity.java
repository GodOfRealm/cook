package com.libalum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.libalum.clip.NewCropActivity;
import com.libalum.utils.BitmapUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.libalum.AlbumSelectorActivity.INTENT_EXTRA_NEED_CROP;

public class TakingPictureActivity extends Activity implements OnClickListener {
    private static final String TAG = "TakingPicturesActivity";
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_CROP = 0x222;
    private ImageView mImage;
    private Uri mSavedPicUri;
    private boolean isNeedCrop;

    public TakingPictureActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.activity_camera);
        isNeedCrop = getIntent().getBooleanExtra(INTENT_EXTRA_NEED_CROP, false);
        Button cancel = this.findViewById(R.id.pic_back);
        Button send = this.findViewById(R.id.pic_send);
        this.mImage = this.findViewById(R.id.pic_img);
        cancel.setOnClickListener(this);
        send.setOnClickListener(this);
        if (savedInstanceState == null) {
            this.startCamera();
        } else {
            String str = savedInstanceState.getString("photo_uri");
            if (str != null) {
                this.mSavedPicUri = Uri.parse(str);

                try {
                    this.mImage.setImageBitmap(BitmapUtil.getResizedBitmap(this, this.mSavedPicUri, 960, 960));
                } catch (IOException var6) {
                    var6.printStackTrace();
                }
            }
        }

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onClick(View v) {
        File file = new File(this.mSavedPicUri.getPath());
        if (!file.exists()) {
            this.finish();
        }

        if (v.getId() == R.id.pic_send) {
            if (this.mSavedPicUri != null) {
                Intent data = new Intent();
                data.setData(this.mSavedPicUri);
                this.setResult(-1, data);
            }

            this.finish();
        } else if (v.getId() == R.id.pic_back) {
            this.finish();
        }

    }

    @SuppressLint("WrongConstant")
    private void startCamera() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!path.exists()) {
            path.mkdirs();
        }

        String name = System.currentTimeMillis() + ".jpg";
        File file = new File(path, name);
        this.mSavedPicUri = Uri.fromFile(file);
        Log.d("TakingPicturesActivity", "startCamera output pic uri =" + this.mSavedPicUri);
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(intent, 65536);
        Uri uri = null;

        try {
            uri = FileProvider.getUriForFile(this, this.getPackageName() + this.getString(R.string.pic_authorities_fileprovider), file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Iterator var7 = resInfoList.iterator();
            while (var7.hasNext()) {
                ResolveInfo resolveInfo = (ResolveInfo) var7.next();
                String packageName = resolveInfo.activityInfo.packageName;
                this.grantUriPermission(packageName, uri, 2);
                this.grantUriPermission(packageName, uri, 1);
            }

            intent.putExtra("output", uri);
        } catch (Exception var11) {
            var11.printStackTrace();
        }
        intent.addCategory("android.intent.category.DEFAULT");

        try {
            this.startActivityForResult(intent, 2);
        } catch (SecurityException var10) {
            Log.e("TakingPicturesActivity", "REQUEST_CAMERA SecurityException!!!");
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NewCropActivity.RESULT_CROP && data != null) {
            setResult(-1, data);
            finish();
            return;
        }
        if (resultCode != -1) {
            this.finish();
        } else {
            switch (requestCode) {
                case 2:
                    if (this.mSavedPicUri != null) {
                        if (isNeedCrop) {
                            NewCropActivity.openCropActivity(this, mSavedPicUri, REQUEST_CROP);
                            break;
                        }
                        try {
                            this.mImage.setImageBitmap(BitmapUtil.getResizedBitmap(this, this.mSavedPicUri, 960, 960));
                        } catch (IOException var5) {
                            var5.printStackTrace();
                        }
                    }

                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("TakingPicturesActivity", "onRestoreInstanceState");
        this.mSavedPicUri = Uri.parse(savedInstanceState.getString("photo_uri"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        Log.e("TakingPicturesActivity", "onSaveInstanceState");
        outState.putString("photo_uri", this.mSavedPicUri.toString());
        super.onSaveInstanceState(outState);
    }
}
