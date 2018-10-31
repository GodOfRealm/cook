package com.miguan.youmi.util;

import android.content.Context;
import android.media.AudioManager;

import com.miguan.youmi.app.PickApplication;
import com.miguan.youmi.core.util.PickToast;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnInfoListener;

import java.io.IOException;

/**
 * Desc: 播放语音辅助类
 * <p>
 * Author: 廖培坤
 * Date: 2018-08-08
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class PlayerManager {

    private static PlayerManager mInstance;

    private PLMediaPlayer mMediaPlayer;
    private AVOptions mAVOptions;
    private AudioManager mAudioManager;

    private boolean isPause;
    private boolean isSpeakerPhoneOn = true;//是否是扬声器模式或者听筒模式，默认是，

    private final int ACTION_START = 1, ACTION_RESUME = 2, ACTION_PAUSE = 3, ACTION_STOP = 4;

    private OnPlayStatusListener mOnPlayStatusListener;

    public static PlayerManager getInstance() {
        if (mInstance == null) {
            synchronized (PlayerManager.class) {
                if (mInstance == null) {
                    mInstance = new PlayerManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Desc: 播放音频，可以传入网络地址
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     *
     * @param filePath
     * @param onCompletionListener
     * @throws IOException
     */
    public void playAudio(String filePath, PLOnCompletionListener onCompletionListener) {
        playAudio(filePath, onCompletionListener, null);
    }

    /**
     * Desc: 播放音频，可以传入网络地址
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     *
     * @param filePath
     * @param onCompletionListener
     * @throws IOException
     */
    public void playAudio(String filePath, PLOnCompletionListener onCompletionListener, OnPlayStatusListener onPlayStatusListener) {
        try {
            if (mMediaPlayer == null) {
                mAVOptions = new AVOptions();
                mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
                mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_AUTO);

                mMediaPlayer = new PLMediaPlayer(PickApplication.getInstance(), mAVOptions);
            }
            mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                if (mAudioManager == null) {
                    mAudioManager = (AudioManager) PickApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
                }
                if (!isSpeakerPhoneOn) {
                    mAudioManager.setSpeakerphoneOn(false);
                    mAudioManager.requestAudioFocus(null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN);
                    mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                } else {
                    mAudioManager.setSpeakerphoneOn(true);
                    mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    mAudioManager.setMode(AudioManager.MODE_NORMAL);
                }

                mMediaPlayer.start();
                mOnPlayStatusListener = onPlayStatusListener;
                playStatusCallBack(ACTION_START);//执行开始播放回调
            });
            playStatusCallBack(ACTION_STOP);//执行停止回调，用以通知上次播放
            mMediaPlayer.setOnCompletionListener(() -> {
                mOnPlayStatusListener = null;
                if (null != onCompletionListener) {
                    onCompletionListener.onCompletion();
                }
            });
            mMediaPlayer.setOnInfoListener((i, i1) -> {
                switch (i) {
                    case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                        if (mOnPlayStatusListener != null) {
                            mOnPlayStatusListener.onPlaying(i1, mMediaPlayer.getDuration());
                        }
                        break;
                }
            });
            AudioManager audioManager = (AudioManager) PickApplication.getInstance().getSystemService(Context.AUDIO_SERVICE);
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
            stop();
            PickToast.show("播放出错");
        }
    }

    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
        }
        playStatusCallBack(ACTION_PAUSE);
    }

    public void stop() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            isPause = false;
        }
        playStatusCallBack(ACTION_STOP);
    }

    public void resume() {
        if (mMediaPlayer != null && isPause) {
            mMediaPlayer.start();
            isPause = false;
        }
        playStatusCallBack(ACTION_RESUME);
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public long getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    /**
     * Desc: 当前播放的位置
     * <p>
     * Author: SonnyJack
     * Date: 2018-08-28
     *
     * @return boolean
     */
    public long getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    /**
     * Desc: 是否开启扬声器模式
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     *
     * @return boolean
     */
    public boolean isSpeakerPhoneOn() {
        return isSpeakerPhoneOn;
    }

    /**
     * Desc: 是否正在播放
     * <p>
     * Author: 廖培坤
     * Date: 2018-08-08
     *
     * @return boolean
     */
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    /**
     * Desc: 开启或者关闭扬声器模式
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-20
     *
     * @param speakerphoneOn
     */
    public void setSpeakerPhoneOn(boolean speakerphoneOn) {
        this.isSpeakerPhoneOn = speakerphoneOn;
    }

    private void playStatusCallBack(int action) {
        if (null == mOnPlayStatusListener) {
            return;
        }
        switch (action) {
            case ACTION_START:
                mOnPlayStatusListener.onStart(mMediaPlayer);
                break;
            case ACTION_RESUME:
                mOnPlayStatusListener.onResume(mMediaPlayer);
                break;
            case ACTION_PAUSE:
                mOnPlayStatusListener.onPause(mMediaPlayer);
                break;
            case ACTION_STOP:
                //通知停止播放后，将回调置为空
                mOnPlayStatusListener.onStop(mMediaPlayer);
                mOnPlayStatusListener = null;
                break;
        }
    }

    public static class OnPlayStatusListener {

        public void onStart(PLMediaPlayer mediaPlayer) {

        }

        public void onStop(PLMediaPlayer mediaPlayer) {

        }

        public void onResume(PLMediaPlayer mediaPlayer) {

        }

        public void onPause(PLMediaPlayer mediaPlayer) {

        }

        public void onPlaying(int currentDuration, long maxDuration) {

        }

    }
}
