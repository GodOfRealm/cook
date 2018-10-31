package com.miguan.youmi.util;

import android.media.MediaRecorder;

import com.miguan.youmi.app.constant.Constant;

import java.io.File;
import java.io.IOException;

/**
 * Desc: 录制音频管理类
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-20
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class RecorderManager {

    private static RecorderManager sRecorderManager;

    public MediaRecorder mMediaRecorder;
    public boolean isPrepared = false;
    public AudioStateChangeListener mAudioStateChangeListener;
    private String mCurrentFilePath;

    private RecorderManager() {
    }

    public static RecorderManager getInstance() {
        if (sRecorderManager == null) {
            synchronized (RecorderManager.class) {
                if (sRecorderManager == null) {
                    sRecorderManager = new RecorderManager();
                }
            }
        }
        return sRecorderManager;
    }

    public void setOnAudioStateChangeListener(AudioStateChangeListener listener) {
        mAudioStateChangeListener = listener;
    }

    /**
     * Desc: 开始录音
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     *
     * @param sFile
     */
    public void startRecordAudio(File sFile) {
        try {
            isPrepared = false;

            File file;
            if (sFile == null) {
                file = new File(Constant.VOICE_CERTIFY_PATH, System.currentTimeMillis() + ".aac");
            } else {
                file = sFile;
            }

            mCurrentFilePath = file.getAbsolutePath();
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isPrepared = true;

            if (mAudioStateChangeListener != null) {
                mAudioStateChangeListener.onStart();
            }
        } catch (IllegalStateException | IOException e) {
            cancel();
            e.printStackTrace();
        }
    }

    /**
     * Desc: 停止录制并释放资源
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     */
    public void release() {
        try {
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (Exception ignore) {
        }
    }

    /**
     * Desc: 取消录制，会删除已录制的文件
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     */
    public void cancel() {
        release();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }
    }

    public String getCurrentPath() {
        return mCurrentFilePath;
    }

    /**
     * Desc: 获取当前录音的音量
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     *
     * @param maxLevel
     * @return int
     */
    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                // 录音
                int voiceLevel = maxLevel * mMediaRecorder.getMaxAmplitude() / 22768;
                return voiceLevel > 6 ? 6 : voiceLevel;
            } catch (Exception e) {
            }
        }
        return 1;
    }

    public interface AudioStateChangeListener {
        void onStart();
    }

}
