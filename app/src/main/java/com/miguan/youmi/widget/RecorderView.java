package com.miguan.youmi.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.miguan.youmi.R;
import com.miguan.youmi.app.constant.Constant;
import com.miguan.youmi.app.constant.EventBusTags;
import com.miguan.youmi.core.util.PickToast;
import com.miguan.youmi.util.EventBusUtil;
import com.miguan.youmi.util.PlayerManager;
import com.miguan.youmi.util.RecorderManager;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Desc:
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-20
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RecorderView extends LinearLayout implements Chronometer.OnChronometerTickListener {

    /**
     * 初始状态，当前可录制
     */
    private static final int STATE_UN_START = 1;

    /**
     * 正在录制，可停止
     */
    private static final int STATE_RECORDING = 2;

    /**
     * 录制完成，可播放、重置、完成
     */
    private static final int STATE_COMPLETE = 3;

    /**
     * 正在播放，可暂停
     */
    private static final int STATE_PLAYING = 4;

    @BindView(R.id.recorder_chr_time)
    Chronometer mChTime;
    @BindView(R.id.recorder_tv_max_duration)
    TextView mTvMaxDuration;
    @BindView(R.id.recorder_donut_progress)
    DonutProgress mDonutProgress;
    @BindView(R.id.recorder_iv_button)
    ImageView mIvButton;
    @BindView(R.id.recorder_iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.recorder_fl_button)
    FrameLayout mFlButton;
    @BindView(R.id.recorder_iv_complete)
    ImageView mIvComplete;
    @BindView(R.id.recorder_tv_text)
    TextView mTvText;

    private RecorderManager mRecorderManager;
    private PlayerManager mPlayerManager;

    private int mMaxDuration = 15; // 录制最大时长
    private long mVoiceLength;
    private int mCurState = STATE_UN_START;

    private OnCompleteClickListener mOnCompleteClickListener;

    public RecorderView(Context context) {
        super(context);
        init();
    }

    public RecorderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnCompleteClickListener(OnCompleteClickListener listener) {
        this.mOnCompleteClickListener = listener;
    }

    private void init() {
        EventBus.getDefault().register(this);

        View.inflate(getContext(), R.layout.recorder_include_view, this);
        ButterKnife.bind(this);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);

        mRecorderManager = RecorderManager.getInstance();
        mPlayerManager = PlayerManager.getInstance();
        mChTime.setOnChronometerTickListener(this);
        mChTime.setFormat("%s");
        mDonutProgress.setMax(mMaxDuration);
        mTvMaxDuration.setText(String.format("录制时长为5-%d秒", mMaxDuration));
    }

    @OnClick({R.id.recorder_iv_delete,})
    void onDeleteClick(View view) {
        if (mCurState == STATE_COMPLETE) {
            mRecorderManager.cancel();
            mCurState = STATE_UN_START;
            updateView();
        }
    }

    @OnClick(R.id.recorder_iv_complete)
    void onCompleteClick() {
        if (mCurState == STATE_COMPLETE && mOnCompleteClickListener != null) {
            mOnCompleteClickListener.onCompleteClick(getFilePath(), mVoiceLength);
        }
    }

    @OnClick(R.id.recorder_fl_button)
    void onButtonClick() {
        switch (mCurState) {
            case STATE_UN_START:
                PermissionUtils.permission(PermissionConstants.MICROPHONE, PermissionConstants.STORAGE)
                        .rationale(rational -> EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION,
                                R.string.permission_microphone))
                        .callback(new PermissionUtils.SimpleCallback() {
                            @Override
                            public void onGranted() {
                                File file = new File(Constant.VOICE_CERTIFY_PATH, System.currentTimeMillis() + ".aac");
                                if (FileUtils.createOrExistsFile(file)) {
                                    mRecorderManager.startRecordAudio(file);

                                    mChTime.setBase(SystemClock.elapsedRealtime());
                                    mChTime.start();

                                    mCurState = STATE_RECORDING;
                                }
                            }

                            @Override
                            public void onDenied() {
                                EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION,
                                        R.string.permission_microphone);
                            }
                        })
                        .request();
                break;
            case STATE_RECORDING:
                stopRecord();
                break;
            case STATE_COMPLETE:
                startPlay();
                break;
            case STATE_PLAYING:
                stopPlay();
                break;
        }
        updateView();
    }

    /**
     * Desc: 停止录音
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     */
    private void stopRecord() {
        mChTime.stop();

        if (mVoiceLength >= 5) {
            mRecorderManager.release();
            mCurState = STATE_COMPLETE;
        } else {
            mRecorderManager.cancel();
            mCurState = STATE_UN_START;
            PickToast.show("录音小于5秒");
        }
    }

    /**
     * Desc: 开始播放
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-17
     */
    private void startPlay() {
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .rationale(rationale -> EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_write))
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        mPlayerManager.playAudio(mRecorderManager.getCurrentPath(), () -> {
                            stopPlay();
                        }, new PlayerManager.OnPlayStatusListener() {
                            @Override
                            public void onPlaying(int currentDuration, long maxDuration) {
                                setTimeFormat((maxDuration - currentDuration) / 1000);
                            }
                        });
                        mCurState = STATE_PLAYING;
                    }

                    @Override
                    public void onDenied() {
                        EventBusUtil.postAppMessage(EventBusTags.DEAL_WITH_NO_PERMISSION, R.string.permission_write);
                    }
                })
                .request();
    }

    /**
     * Desc: 停止播放
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     */
    private void stopPlay() {
        mPlayerManager.stop();
        mCurState = STATE_COMPLETE;
        setTimeFormat(mVoiceLength);
        updateView();
    }

    /**
     * Desc: 更新不同状态的界面
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     */
    private void updateView() {
        switch (mCurState) {
            case STATE_UN_START:
                mChTime.stop();
                mChTime.setBase(SystemClock.elapsedRealtime());

                mIvDelete.setVisibility(INVISIBLE);
                mIvComplete.setVisibility(INVISIBLE);
                mIvButton.setImageResource(R.mipmap.recorder_ic_start);
                mTvText.setText("点击开始录音");
                break;
            case STATE_PLAYING:
                mIvDelete.setImageResource(R.mipmap.recorder_ic_delete_disable);
                mIvComplete.setImageResource(R.mipmap.recorder_ic_complete_disable);
                mIvButton.setImageResource(R.mipmap.recorder_ic_pause);
                mTvText.setText("点击暂停");
                break;
            case STATE_RECORDING:
                mIvButton.setImageResource(R.mipmap.recorder_ic_end);
                mTvText.setText("再次点击结束录制");
                break;
            case STATE_COMPLETE:
                mIvDelete.setImageResource(R.mipmap.recorder_ic_delete_normal);
                mIvComplete.setImageResource(R.mipmap.recorder_ic_complete_normal);
                mIvDelete.setVisibility(VISIBLE);
                mIvComplete.setVisibility(VISIBLE);
                mIvButton.setImageResource(R.mipmap.recorder_ic_play);
                mTvText.setText("点击播放");
                break;
        }
    }

    private void setTimeFormat(long duration) {
        int minute = (int) (duration / 60);
        int second = (int) (duration % 60);
        mChTime.setText(getContext().getString(R.string.time_format, minute, second));
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        mVoiceLength = (SystemClock.elapsedRealtime() - mChTime.getBase()) / 1000;
        mDonutProgress.setProgress(mVoiceLength);
        if (mVoiceLength >= mMaxDuration) { // 超过最大时长就停止
            stopRecord();
            updateView();
        }
    }

    /**
     * Desc: 停止当前一切
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     */
    public void stopAll() {
        mRecorderManager.release();
        mPlayerManager.stop();
        mPlayerManager.release();
    }

    public String getFilePath() {
        return mRecorderManager.getCurrentPath();
    }

    public interface OnCompleteClickListener {
        void onCompleteClick(String filePath, long length);
    }

    /**
     * Desc: 设置最大时长
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-04
     *
     * @param duration
     */
    public void setMaxDuration(int duration) {
        this.mMaxDuration = duration;
        mDonutProgress.setMax(duration);
        mTvMaxDuration.setText(String.format("录制时长为5-%d秒", mMaxDuration));
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Subscriber(tag = EventBusTags.ON_RECORDER_PAUSE)
    public void onPause(Bundle bundle) {
        if (mCurState == STATE_RECORDING) {
            stopRecord();
            updateView();
        } else if (mCurState == STATE_PLAYING) {
            stopPlay();
        }
    }

}
