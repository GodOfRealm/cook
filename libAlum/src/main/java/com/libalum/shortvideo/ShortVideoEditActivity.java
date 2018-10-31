package com.libalum.shortvideo;

import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.blankj.utilcode.util.ScreenUtils;
import com.libalum.R;
import com.libalum.shortvideo.adapter.VideoFrameListAdapter;
import com.libalum.shortvideo.entity.VideoFrame;
import com.libalum.shortvideo.widget.RangeSeekBar;
import com.libalum.utils.ShortVideoUtils;
import com.miguan.youmi.core.thread.ExecutorServiceHelp;
import com.qiniu.pili.droid.shortvideo.PLShortVideoTrimmer;
import com.qiniu.pili.droid.shortvideo.PLVideoSaveListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lmy on 2018/3/26.
 * 视频编辑页
 */

public class ShortVideoEditActivity extends AppCompatActivity implements View.OnClickListener, PLVideoSaveListener {

    public static final long MAX_CUT_DURATION = 10 * 1000L;//视频最多剪切多长时间
    private static final String TAG = ShortVideoEditActivity.class.getSimpleName();
    private static final long MIN_CUT_DURATION = 3 * 1000L;// 最小剪辑时间3s
    private static final int MAX_COUNT_RANGE = 10;//seekBar的区域内一共有多少张图片
    public static String EXTRA_PATH = "extra_path";
    /**
     * 确定选择的视频
     */
    public static String RESULT_EXTRA_VIDEO = "result_extra_video";
    private final int SeekBarPadding = 40;
    /**
     * 视频地址
     */
    String videoPath;

    MediaMetadataRetriever videoRetriever;

    int srcDurationMs;
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

    int frameCount;

    int frameWidth;

    /**
     * 每一帧预览图代表的时长
     */
    float frameDurationMs;

    /**
     * 每个像素代表的时长
     */
    float pxDurationMs;
    /**
     * 帧列表recycle
     */
    RecyclerView frameListView;
    LinearLayoutManager frameLayoutManager;
    PLShortVideoTrimmer videoTrimmer;
    /**
     * 剪辑的dialog
     */
    ShortVideoProgressDialog progressDialog;
    /**
     * seek选择中
     */
    boolean durationSeeking;
    private Handler mUiHandler;
    private TextView tvCancel;
    private VideoView preview;
    private View previewMask;
    private ImageView playBtn;
    private TextView complete;
    private VideoFrameListAdapter frameListAdapter;
    /**
     * 填充seekBar
     */
    private ViewGroup llSeek;
    private RangeSeekBar seekBar;
    /**
     * recycle滑动的距离
     */
    private int offsetBeginMs;
    //    LoadingDialog progressDialog;
    private final RangeSeekBar.OnRangeSeekBarChangeListener mOnRangeSeekBarChangeListener = new RangeSeekBar.OnRangeSeekBarChangeListener() {
        @Override
        public void onRangeSeekBarActionUp(long minValue, long maxValue, RangeSeekBar.Thumb pressedThumb, MotionEvent event) {
            int width = seekBar.getWidth() - 2 * DensityUtil.dp2px(SeekBarPadding);
            if (pressedThumb == RangeSeekBar.Thumb.MIN) {
//                frameListView.setPadding((int) (width * minValue * 1f / MAX_CUT_DURATION) + DensityUtil.dp2px(SeekBarPadding), 0, frameListView.getPaddingRight(), 0);
            } else if (pressedThumb == RangeSeekBar.Thumb.MAX) {
                frameListView.setPadding(frameListView.getPaddingLeft(), 0, (int) (width * (MAX_CUT_DURATION - maxValue) * 1f / MAX_CUT_DURATION) + DensityUtil.dp2px(SeekBarPadding), 0);
            }
            //使其更新 scrollOffset
            frameListView.scrollBy(1, 0);
            int scrollOffset = frameListView.computeHorizontalScrollOffset();
            offsetBeginMs = (int) (scrollOffset * pxDurationMs);
            updateTrimDuration();
            playVideo();
        }

        @Override
        public void onRangeSeekBarActionMove(long minValue, long maxValue, RangeSeekBar.Thumb pressedThumb) {
            Log.d(TAG, "-----selectedEndMs----->>>>>>" + selectedEndMs);
            updateSeekChange();
            if (pressedThumb == RangeSeekBar.Thumb.MIN) {//左编辑
                seek(selectedBeginMs);
            } else if (pressedThumb == RangeSeekBar.Thumb.MAX) {//右编辑
                seek(selectedEndMs);
            }
        }

        @Override
        public void onRangeSeekBarActionDown(long minValue, long maxValue, RangeSeekBar.Thumb pressedThumb) {
        }

    };
    /**
     * 裁剪video路径
     */
    private String trimVideoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.short_video_activity_edit);

        videoPath = getIntent().getStringExtra(EXTRA_PATH);

        tvCancel = findViewById(R.id.tv_cancel);
        preview = findViewById(R.id.preview);
        playBtn = findViewById(R.id.play_btn);
        complete = findViewById(R.id.tv_complete);
        previewMask = findViewById(R.id.preview_mask);
        frameListView = findViewById(R.id.frame_list_view);
        llSeek = findViewById(R.id.ll_seek);

        if (TextUtils.isEmpty(videoPath)) {
            Toast.makeText(this, "加载错误", Toast.LENGTH_SHORT).show();
        } else {
            initMediaData();
            initLayout();
            initData();
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
            if (videoTrimmer != null) {
                videoTrimmer.cancelTrim();
                videoTrimmer.destroy();
            }
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
        srcDurationMs = durationMs = selectedEndMs = ShortVideoUtils.getVideoDuration(videoRetriever);

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

        //帧列表
        frameLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        frameListView.setLayoutManager(frameLayoutManager);
        frameListAdapter = new VideoFrameListAdapter();
        frameListAdapter.setVideoRetriever(videoRetriever);
        frameListView.setAdapter(frameListAdapter);

//        seekBar = new RangeSeekBar(this, 0, MAX_CUT_DURATION);
        seekBar = findViewById(R.id.rangeSeekBar);
        int padding = DensityUtil.dp2px(24);
        seekBar.setPadding(padding, 0, padding, 0);
        seekBar.setSelectedMinValue(0L);
        seekBar.setSelectedMaxValue(MAX_CUT_DURATION);
        seekBar.setMin_cut_time(MIN_CUT_DURATION);//设置最小裁剪时间
        seekBar.setNotifyWhileDragging(true);
        seekBar.setOnRangeSeekBarChangeListener(mOnRangeSeekBarChangeListener);
//        llSeek.addView(seekBar);

        //视频
        preview.setVideoPath(videoPath);
        preview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer plMediaPlayer) {
                playVideo();
            }
        });

        initFrameSpec();

        progressDialog = new ShortVideoProgressDialog(this);
        progressDialog.setCancelable(false);

//        progressDialog = new ShortVideoProgressDialog(this);
//        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                videoTrimmer.cancelTrim();
//            }
//        });

        //滑动距离
        frameListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    playVideo();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) return;
                int scrollOffset = frameListView.computeHorizontalScrollOffset();
                Log.d(TAG, "-----scrollOffset----->>>>>>" + scrollOffset);
                offsetBeginMs = (int) (scrollOffset * pxDurationMs);
                Log.d(TAG, "-----offsetBeginMs----->>>>>>" + offsetBeginMs);
                updateTrimDuration();
                seek(selectedBeginMs);
            }
        });

        tvCancel.setOnClickListener(this);
        previewMask.setOnClickListener(this);
        complete.setOnClickListener(this);
    }

    private void initData() {
        if (srcDurationMs == 0) return;
        List<VideoFrame> videoFrames = new ArrayList<>();
        int frameDuration = srcDurationMs / frameCount;
        for (int i = 0; i < frameCount; i++) {
            VideoFrame frame = new VideoFrame();
            frame.time = frameDuration * i;
            videoFrames.add(frame);
        }
        frameListAdapter.setNewData(videoFrames);
    }

    /**
     * recycle 帧列表 滑动更新
     */
    private void updateTrimDuration() {
        playBtn.setVisibility(View.GONE);
//        int leftIndex = rangeSlider.getLeftIndex();
//        int rightIndex = rangeSlider.getRightIndex();
        //直接拉截取框 超过 recycle时 offsetBeginMs < 0
        offsetBeginMs = Math.max(0, offsetBeginMs);

        int beginMs = offsetBeginMs;
        int endMs = (int) Math.min(srcDurationMs, offsetBeginMs + seekBar.getSelectedMaxValue() - seekBar.getSelectedMinValue());
        selectedBeginMs = beginMs;
        selectedEndMs = endMs;
        if (selectedEndMs - selectedBeginMs > MAX_CUT_DURATION) {
            selectedEndMs = (int) (selectedBeginMs + MAX_CUT_DURATION);
        }
        Log.d(TAG, "-----selectedBeginMs----->>>>>>" + selectedBeginMs);
        Log.d(TAG, "-----selectedEndMs----->>>>>>" + selectedEndMs);
        durationMs = selectedEndMs - selectedBeginMs;
//        trimDuration.setText(String.format("%.1f秒", durationMs / 1000f));
        durationSeeking = true;
    }

    /**
     * seek 选择滑动，帧数据更新
     */
    private void updateSeekChange() {
        playBtn.setVisibility(View.GONE);
        //直接拉截取框 超过 recycle时 offsetBeginMs < 0
        offsetBeginMs = Math.max(0, offsetBeginMs);

        int beginMs = offsetBeginMs + (int) seekBar.getSelectedMinValue();
        int endMs = (int) Math.min(srcDurationMs, offsetBeginMs + seekBar.getSelectedMaxValue() - seekBar.getSelectedMinValue());
        selectedBeginMs = beginMs;
        selectedEndMs = endMs;
        if (selectedEndMs - selectedBeginMs > MAX_CUT_DURATION) {
            selectedEndMs = (int) (selectedBeginMs + MAX_CUT_DURATION);
        }
        Log.d(TAG, "-----selectedBeginMs----->>>>>>" + selectedBeginMs);
        Log.d(TAG, "-----selectedEndMs----->>>>>>" + selectedEndMs);
        durationMs = selectedEndMs - selectedBeginMs;
//        trimDuration.setText(String.format("%.1f秒", durationMs / 1000f));
        durationSeeking = true;
    }

    /**
     * 初始化视频关键帧信息
     */
    private void initFrameSpec() {
        int layoutWidth = ScreenUtils.getScreenWidth() - 2 * DensityUtil.dp2px(24);
        int frameHeight = DensityUtil.dp2px(50);
        float frameWidth = frameHeight * 11f / 16f;
        float layoutFrameCount = layoutWidth / frameWidth;
        int frameCount;
        if (srcDurationMs > MAX_CUT_DURATION) {
            float durationRatio = (float) srcDurationMs / MAX_CUT_DURATION;
            frameCount = (int) (layoutFrameCount * durationRatio);
            layoutFrameCount = frameCount / durationRatio;
            frameWidth = layoutWidth / layoutFrameCount;
            llSeek.getLayoutParams().width = (int) (frameWidth * MAX_CUT_DURATION / 1000);
        } else {
            frameCount = (int) layoutFrameCount;
            frameWidth = (float) layoutWidth / frameCount;
            int newLayoutWidth = (int) frameWidth * frameCount;
            if (newLayoutWidth != layoutWidth) {
                frameListView.getLayoutParams().width = newLayoutWidth
                        + frameListView.getPaddingStart() + frameListView.getPaddingEnd();
            }
        }
        this.frameCount = frameCount;
        this.frameListAdapter.setFrameWidth(layoutWidth / 10);
        this.frameWidth = (int) frameWidth;
        this.frameDurationMs = (float) srcDurationMs / frameCount;
        this.pxDurationMs = (float) srcDurationMs / (frameCount * frameWidth);
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
            preview.seekTo(selectedBeginMs);
            preview.start();
        }
        durationSeeking = false;
        playBtn.setVisibility(View.GONE);
        startPlayChecker();
    }

    /**
     * 滑动到选择的地方
     *
     * @param position
     */
    private void seek(int position) {
        if (preview != null) {
            if (preview.canPause()) {
                preview.pause();
            }
            preview.seekTo(position);
        }
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
        } else if (id == R.id.tv_cancel) {
            finish();
        } else if (id == R.id.tv_complete) {
            progressDialog.show();
//            progressDialog.setProgress(0);
            ExecutorServiceHelp.executeBackground(new Runnable() {
                @Override
                public void run() {
                    startVideoTrim();
                }
            });
        }
    }

    private void startVideoTrim() {
        updateTrimDuration();
        if (videoTrimmer != null) {
            try {
                videoTrimmer.cancelTrim();
                videoTrimmer.destroy();
                videoTrimmer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File saveFile = new File(getExternalFilesDir(null), "mimi_trim_video.mp4");
        trimVideoPath = saveFile.getAbsolutePath();
        videoTrimmer = new PLShortVideoTrimmer(this, videoPath, saveFile.getAbsolutePath());
        videoTrimmer.trim(selectedBeginMs, selectedEndMs, this);
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

    /*-------裁剪视频回调 Start------*/
    @Override
    public void onSaveVideoSuccess(String savePath) {
        progressDialog.dismiss();
        File file = new File(savePath);
        if (file.length() > 100 * 1024 * 1024) {
            Toast.makeText(this, String.format("视频文件大小超过了%dMB，请重新裁剪", 100), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(RESULT_EXTRA_VIDEO, savePath);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSaveVideoFailed(int i) {
        post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ShortVideoEditActivity.this, "视频裁剪失败", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onSaveVideoCanceled() {
        post(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onProgressUpdate(final float v) {
//        post(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.setProgress((int) (v * 100));
//            }
//        });

    }
    /*-------裁剪视频回调 End------*/

    private class PlayEndChecker implements Runnable {
        @Override
        public void run() {
            if (!durationSeeking && preview.getCurrentPosition() >= selectedEndMs) {
                preview.seekTo(selectedBeginMs);
            }
            post(this, 100);
        }
    }
}
