package com.libalum;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libalum.clip.NewCropActivity;
import com.libalum.shortvideo.ShortVideoPreviewActivity;
import com.libalum.utils.PermissionCheckUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;

/**
 * Created by wzy on 2016/4/11.
 */
public class AlbumSelectorActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String INTENT_MAX_NUM = "intent_max_num";
    public static final String INTENT_ISHOW_ADD = "intent_ishow_add";//是否显示照相
    public static final String INTENT_EXTRA_SHOW_VIDEO = "intent_extra_show_video";//是否显示视频，默认false不显示
    public static final String INTENT_EXTRA_ONLY_SHOW_VIDEO = "intent_extra_only_show_video";//只显示视频，默认false不显示
    public static final String INTENT_EXTRA_TO_FEED_LIST = "intent_extra_to_feed_list";//是否跳转到动态列表
    public static final String INTENT_EXTRA_NEED_CROP = "intent_extra_need_crop";//是否需要剪裁
    public static final int DEFAULT_WIDTH = 640;
    public static final int DEFAULT_HEIGHT = 640;
    public static final int REQUEST_CROP_CODE = 111;
    /**
     * 最多选择图片的个数
     */
    public static int MAX_NUM = 9;
    // Get relevant columns for use later.
    String[] projection = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE,
            MediaStore.Video.VideoColumns.DURATION
    };
    Uri queryUri = MediaStore.Files.getContentUri("external");
    private boolean isShowTake;
    /**
     * 是否显示视频
     */
    private boolean isShowVideo;
    /**
     * 只显示视频
     */
    private boolean isOnlyShowVideo;
    private TextView mTvOk;
    private GridView mGridview;
    private AlbumSelectorAdapter mPictureadapter;
    int SHORT_VIDEO_MAX_DURATION = 30999;//最长视频时间

    private boolean isNeedCrop;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashMap<String, Integer> mTmpDir = new HashMap<String, Integer>();
    private ArrayList<ImageFloder> mDirPaths = new ArrayList<ImageFloder>();
    private ContentResolver mContentResolver;

    private ImageView mIvBack;
    private Button mBtn_select;
    private TextView mTv_number;
    private ListView mListview;
    private FolderAdapter mFolderAdapter;
    private ImageFloder mImageAll, mCurrentImageFolder;
    private RelativeLayout mBottomLayout;

    /**
     * 已选择的图片
     */
    private ArrayList<String> mSelectedPicture = new ArrayList<String>();
    private CheckedTextView mOriginalChecked;

    public void select(View v) {
        if (mListview.getVisibility() == View.VISIBLE) {
            hideListAnimation();
        } else {
            mListview.setVisibility(View.VISIBLE);
            showListAnimation();
            mFolderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ok) {
            handleComplete();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_album_selector);
        MAX_NUM = getIntent().getIntExtra(INTENT_MAX_NUM, MAX_NUM);
        isShowTake = getIntent().getBooleanExtra(INTENT_ISHOW_ADD, true);
        isShowVideo = getIntent().getBooleanExtra(INTENT_EXTRA_SHOW_VIDEO, false);
        isOnlyShowVideo = getIntent().getBooleanExtra(INTENT_EXTRA_ONLY_SHOW_VIDEO, false);
        isNeedCrop = getIntent().getBooleanExtra(INTENT_EXTRA_NEED_CROP, false) && MAX_NUM == 1;

        mContentResolver = getContentResolver();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPictureadapter.notifyDataSetChanged();
        if (mSelectedPicture != null) {
            if (mSelectedPicture.size() > 0) {
                mTvOk.setOnClickListener(this);
                mTv_number.setText("" + mSelectedPicture.size());
                mTv_number.setVisibility(View.VISIBLE);
            } else {
                mTvOk.setOnClickListener(null);
                mTv_number.setVisibility(View.GONE);
            }

            resetOriginalText();
        }
    }

    public void resetOriginalText() {
        if (mOriginalChecked != null) {
            mOriginalChecked.setText(mOriginalChecked.isChecked() ? String.format(Locale.getDefault(), "原图(%sMB)", new DecimalFormat("#0.00").format(getFileSize())) : "原图");
            mOriginalChecked.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private void initView() {
        mBtn_select = findViewById(R.id.btn_select);
        mTv_number = findViewById(R.id.tv_number);
        mGridview = findViewById(R.id.gridview);
        mIvBack = findViewById(R.id.btn_back);
        mListview = findViewById(R.id.listview);
        mTvOk = findViewById(R.id.ok);
        mBottomLayout = findViewById(R.id.bottom);

        mTvOk.setVisibility(isOnlyShowVideo ? View.GONE : View.VISIBLE);
        mBottomLayout.setVisibility(isOnlyShowVideo ? View.GONE : View.VISIBLE);

        mOriginalChecked = findViewById(R.id.original_check);

    }

    private void initData() {
        mImageAll = new ImageFloder();
        mImageAll.setDir(isOnlyShowVideo ? "/所有视频" : "/所有图片");
        mCurrentImageFolder = mImageAll;
        mDirPaths.add(mImageAll);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPictureadapter = new AlbumSelectorAdapter(this, mCurrentImageFolder, mSelectedPicture);
        mPictureadapter.setOkTextView(mTvOk);
        mPictureadapter.setMaxNumber(MAX_NUM);
        mPictureadapter.setShowTake(isShowTake);
        mPictureadapter.setHasSelectedImage(!isShowVideo);
        if (isOnlyShowVideo) {
            mPictureadapter.setMaxVideoLength(SHORT_VIDEO_MAX_DURATION);
        }
        mGridview.setAdapter(mPictureadapter);

        mPictureadapter.setCameraListener(new AlbumSelectorAdapter.CameraListener() {
            @Override
            public void onOpenCamera() {
                goCamera();
            }
        });
        mPictureadapter.setBigPictureCompleteListener(new AlbumSelectorAdapter.BigPictureCompleteListener() {
            @Override
            public void onComplete(List<String> imageSelected) {
                handleComplete();
            }
        });
        mFolderAdapter = new FolderAdapter(this, mDirPaths, mCurrentImageFolder);
        mListview.setAdapter(mFolderAdapter);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageFloder folder = mDirPaths.get(position);
                mCurrentImageFolder = folder;
                if (!folder.isVideoFolder()) {
                    mGridview.setNumColumns(4);
                    mGridview.setAdapter(mPictureadapter);
                    mPictureadapter.setCurrentImageFolder(mCurrentImageFolder);
                    mPictureadapter.notifyDataSetChanged();
                    mBottomLayout.setVisibility(View.VISIBLE);
                }
                hideListAnimation();
                mBtn_select.setText(mCurrentImageFolder.getName());
                mGridview.smoothScrollToPosition(1);

                if (mFolderAdapter != null) {
                    mFolderAdapter.setCurrentImageFolder(mCurrentImageFolder);
                    mFolderAdapter.notifyDataSetChanged();
                }
            }
        });

        mBtn_select.setText(isOnlyShowVideo ? "视频" : "相册");
        mTv_number.setVisibility(View.VISIBLE);
        getThumbnail();


        if (MAX_NUM == 1) {
            mTvOk.setText("确定");
        }

        mOriginalChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOriginalChecked.setChecked(!mOriginalChecked.isChecked());
                resetOriginalText();
            }
        });


    }

    public double getFileSize() {
        long fileSize = 0;
        for (int i = 0; i < mSelectedPicture.size(); i++) {
            File file = new File(mSelectedPicture.get(i));
            if (file.exists()) {
                fileSize = fileSize + file.length();
            }
        }
        return (double) fileSize / 1024 / 1024;
    }

    /**
     * 使用相机拍照
     */
    private void goCamera() {
        String[] permissions = new String[]{CAMERA};
        if (PermissionCheckUtil.checkPermissions(AlbumSelectorActivity.this, permissions)) {
            Intent i = new Intent(AlbumSelectorActivity.this, TakingPictureActivity.class);
            i.putExtra(INTENT_EXTRA_NEED_CROP, isNeedCrop);
            startActivityForResult(i, ImageSDK.REQUEST_CODE_TAKE_PICTURE);
        }
    }

    /**
     * 点完成，是否进入多功能编辑界面/裁切界面
     */
    private void handleComplete() {
        if (isNeedCrop) {
            NewCropActivity.openCropActivity(this, Uri.parse(mSelectedPicture.get(0)), REQUEST_CROP_CODE);
        } else {
            imgComplete();
        }
    }

    /**
     * 图片选择完成
     */
    private void imgComplete() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(ImageSDK.KEY_PICTURE_ARRAYLIST, mSelectedPicture);
        intent.putExtra(ImageSDK.KEY_RESULT_IS_VIDEO, false);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void videoComplete(String videoPath) {
        Intent intent = new Intent();
        intent.putExtra(ImageSDK.KEY_VIDEO_PATH, videoPath);
        intent.putExtra(ImageSDK.KEY_RESULT_IS_VIDEO, true);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 获取 contentResolver查询条件
     *
     * @return
     */
    private String getResolverSelection() {
        StringBuilder sb = new StringBuilder();
        if (isOnlyShowVideo) {
            sb.append(MediaStore.Files.FileColumns.MEDIA_TYPE)
                    .append("=")
                    .append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
        } else {
            sb.append(MediaStore.Files.FileColumns.MEDIA_TYPE)
                    .append("=")
                    .append(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
            if (isShowVideo) {
                sb.append(" OR ")
                        .append(MediaStore.Files.FileColumns.MEDIA_TYPE)
                        .append("=")
                        .append(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
            }
        }
        return sb.toString();
    }

    /**
     * 得到缩略图
     */
    private void getThumbnail() {
        Cursor mCursor = mContentResolver.query(queryUri, projection, getResolverSelection(), null, MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {

                int _path = mCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int _type = mCursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE);
                int _duration = mCursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION);
                do {

                    // 获取图片的路径
                    String path = mCursor.getString(_path);
                    int type = mCursor.getInt(_type);
                    if (TextUtils.isEmpty(path)) {
                        continue;
                    }
                    File file = new File(path);
                    if (!file.exists() || isWebp(path)) {
                        continue;
                    }

                    double size = file.length() / 1000;

                    if (size <= 10) {  //小于10kb
                        continue;
                    }

                    long duration = 0;
                    // Log.e("TAG", path);
                    if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) {
                        duration = mCursor.getLong(_duration);
                    }
                    mImageAll.images.add(new ImageItem(path, type, duration));
                    if (TextUtils.isEmpty(mImageAll.getFirstImagePath())) {
                        mImageAll.setFirstImagePath(path);
                    }
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    ImageFloder imageFloder = null;
                    String dirPath = parentFile.getAbsolutePath();
                    if (!mTmpDir.containsKey(dirPath)) {
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                        mDirPaths.add(imageFloder);
                        mTmpDir.put(dirPath, mDirPaths.indexOf(imageFloder));
                    } else {
                        imageFloder = mDirPaths.get(mTmpDir.get(dirPath));
                    }
                    imageFloder.images.add(new ImageItem(path, type, duration));
                } while (mCursor.moveToNext());
            }
            mCursor.close();
            mTmpDir = null;
        }

    }

    /**
     * 是否webp
     */
    private boolean isWebp(String url) {
        boolean isGif = false;
        if (!TextUtils.isEmpty(url)) {
            int index = url.lastIndexOf(".");
            if (index >= 0 && index < url.length()) {
                String prefix = url.substring(index + 1);
                if (TextUtils.equals("webp", prefix)) {
                    isGif = true;
                }
            }
        }

        return isGif;
    }

    private void showListAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1, 0f);
        ta.setDuration(200);
        mListview.startAnimation(ta);
    }

    private void hideListAnimation() {
        TranslateAnimation ta = new TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, 1f);
        ta.setDuration(200);
        mListview.startAnimation(ta);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mListview.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageSDK.REQUEST_CODE_TAKE_PICTURE:
                    handleTakeComplete(data);
                    break;
                case ImageSDK.REQUEST_CODE_TAKE_VIDEO:
                    if (data != null) {
                        String videoPath = data.getStringExtra(ShortVideoPreviewActivity.RESULT_EXTRA_VIDEO);
                        videoComplete(videoPath);
                    }
                    break;
            }
        } else if (requestCode == REQUEST_CROP_CODE && resultCode == NewCropActivity.RESULT_CROP && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                mSelectedPicture.clear();
                mSelectedPicture.add(uri.getPath());
            }
            imgComplete();
        }
    }

    private void handleTakeComplete(Intent data) {
        if (data != null) {
            if (data.getData() != null && "content".equals(data.getData().getScheme())) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(data.getData(), new String[]{"_data"}, null, null, null);
                    if (cursor != null && cursor.getCount() != 0) {
                        cursor.moveToFirst();
                        String path = cursor.getString(0);
                        mSelectedPicture.add(0, path);

                        imgComplete();
                    }
                } catch (Exception e) {
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            } else if (data.getData() != null && "file".equals(data.getData().getScheme())) {
                String path = data.getData().getPath();
                mSelectedPicture.add(0, path);

                imgComplete();
            }
        }
    }
}
