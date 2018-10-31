package com.libalum.shortvideo;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.libalum.R;
import com.libalum.utils.ShortVideoUtils;


/**
 * n
 * Created by lmy on 2018/3/26.
 * 视频预览页
 */

public class ShortVideoPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_VIDEO_LENGTH = 10999;
    public static String EXTRA_PATH = "extra_path";
    public static String EXTRA_MAX_LENGTH = "max_length";
    /**
     * 确定选择的视频
     */
    public static String RESULT_EXTRA_VIDEO = "result_extra_video";
    public final int RESULT_SHORT_EDIT = 101;
    /**
     * 视频地址
     */
    String videoPath;
    MediaMetadataRetriever videoRetriever;
    /**
     * 时长毫秒
     */
    int durationMs;
    /**
     * 调节的开始值：暂无
     */
    int selectedBeginMs;
    /**
     * 调节的结束值：暂无，为视频结尾
     */
    int selectedEndMs;
    private Handler mUiHandler;
    private int videoMaxLength; // 最长时间 -秒
    private ImageView backBtn;

    private VideoView preview;

    private View previewMask;

    private ImageView playBtn;

    private TextView complete;

    private TextView tvTip;

    private TextView tvEdit;

//    PLShortVideoTrimmer videoTrimmer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.short_video_preview_activity);

        videoPath = getIntent().getStringExtra(EXTRA_PATH);
        videoMaxLength = getIntent().getIntExtra(EXTRA_MAX_LENGTH, MAX_VIDEO_LENGTH);

        backBtn = findViewById(R.id.back_btn);
        preview = findViewById(R.id.preview);
        playBtn = findViewById(R.id.play_btn);
        complete = findViewById(R.id.tv_complete);
        previewMask = findViewById(R.id.preview_mask);
        tvTip = findViewById(R.id.tv_tip);
        tvEdit = findViewById(R.id.tv_edit);

        if (TextUtils.isEmpty(videoPath)) {
            Toast.makeText(this, "加载错误", Toast.LENGTH_SHORT).show();
        } else {
            initMediaData();
            initLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseVideo();
        stopPlayChecker();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (videoRetriever != null) {
                videoRetriever.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化 media处理信息
     */
    private void initMediaData() {
        videoRetriever = new MediaMetadataRetriever();
        videoRetriever.setDataSource(videoPath);
        durationMs = selectedEndMs = ShortVideoUtils.getVideoDuration(videoRetriever);

    }

    /**
     * 初始化控件
     */
    private void initLayout() {
        if (durationMs == 0) {
            preview.setVisibility(View.GONE);
        } else {
            preview.setVisibility(View.VISIBLE);
        }
        if (durationMs >= videoMaxLength) {
            tvEdit.setVisibility(View.VISIBLE);
            tvTip.setVisibility(View.VISIBLE);
            complete.setVisibility(View.GONE);
        }

        preview.setVideoPath(videoPath);
        preview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playVideo();
            }
        });

        tvEdit.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        previewMask.setOnClickListener(this);
        complete.setOnClickListener(this);
    }

    private void ensureHandler() {
        if (mUiHandler == null) {
            mUiHandler = new Handler(Looper.getMainLooper());
        }
    }

    protected void post(Runnable r) {
        ensureHandler();
        mUiHandler.post(r);
    }

    protected void post(Runnable r, long delay) {
        ensureHandler();
        mUiHandler.postDelayed(r, delay);
    }

    protected void removeAllPost() {
        if (mUiHandler != null) {
            mUiHandler.removeCallbacksAndMessages(null);
        }
    }

    private void stopPlayChecker() {
        removeAllPost();
    }


    /**
     * 暂停
     */
    private void pauseVideo() {
        if (preview.canPause()) {
            preview.pause();
        }
    }

    /**
     * 播放
     */
    private void playVideo() {
        if (preview != null) {
            preview.seekTo(0);
            preview.start();
        }
        playBtn.setVisibility(View.GONE);
        startPlayChecker();
    }

    private void startPlayChecker() {
        stopPlayChecker();
        post(new PlayEndChecker(), 100);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.preview_mask) {
            preViewMaskClick();
        } else if (id == R.id.back_btn) {
            finish();
        } else if (id == R.id.tv_complete) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_EXTRA_VIDEO, videoPath);
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == R.id.tv_edit) {
            Intent intent = new Intent(this, ShortVideoEditActivity.class);
            intent.putExtra(EXTRA_PATH, videoPath);
            startActivityForResult(intent, RESULT_SHORT_EDIT);
        }
    }

    private void preViewMaskClick() {
        if (preview.isPlaying()) {
            preview.pause();
            playBtn.setVisibility(View.VISIBLE);
        } else {
            preview.start();
            playBtn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String trimVideoPath = data.getStringExtra(RESULT_EXTRA_VIDEO);
            if (TextUtils.isEmpty(trimVideoPath)) {
                Toast.makeText(this, "视频裁剪失败", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(RESULT_EXTRA_VIDEO, trimVideoPath);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private class PlayEndChecker implements Runnable {
        @Override
        public void run() {
            if (preview.getCurrentPosition() >= selectedEndMs) {
                preview.seekTo(selectedBeginMs);
            }
            post(this, 100);
        }
    }
}
