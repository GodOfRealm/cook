package com.miguan.youmi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.miguan.youmi.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Desc: 获取验证码按钮
 * <p>
 * Author: 廖培坤
 * Date: 2018-07-03
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class CaptchaButton extends AppCompatTextView {
    private static final int DEFAULT_DISABLE_TIME = 5; // 默认倒计时时间 单位秒

    private int mDisableTime; // 倒计时
    private int mEnableColor; // 正常状态文本颜色
    private int mDisableColor; // 不可点击文本颜色
    private String mEnableString = "获取验证码"; // 默认显示文本
    private String mDisableString = "(%ds)重发";
    private Timer mTimer = null;
    private TimerTask mTask = null;
    private OnSendListener mListener = null;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                tickWork();
            }
            super.handleMessage(msg);
        }
    };

    public CaptchaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initView();
        mTimer = new Timer();
    }

    private void init(Context context, AttributeSet attrs) {
        int defaultEnableColor = context.getResources().getColor(R.color.white);
        int defaultDisableColor = context.getResources().getColor(R.color.colorDisable);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CaptchaButton);
        mDisableTime = ta.getInteger(R.styleable.CaptchaButton_cb_time, DEFAULT_DISABLE_TIME);
        mEnableColor = ta.getColor(R.styleable.CaptchaButton_cb_enable_color, defaultEnableColor);
        mDisableColor = ta.getColor(R.styleable.CaptchaButton_cb_disable_color, defaultDisableColor);
        if (ta.hasValue(R.styleable.CaptchaButton_cb_enable_text)) {
            mEnableString = ta.getString(R.styleable.CaptchaButton_cb_enable_text);
        }
        ta.recycle();
    }

    private void initView() {
        this.setGravity(Gravity.CENTER);
        this.setTextColor(mEnableColor);
        this.setText(mEnableString);
        this.setClickable(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && isEnabled()) {
                    mListener.onClickSendValidateButton();
                }
            }
        });
    }

    /**
     * 开始计时
     */
    public void startTickWork() {
        if (isEnabled()) {
            this.setEnabled(false);
            this.setTextColor(mDisableColor);
            mTask = new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0);
                }
            };
            mTimer.schedule(mTask, 0, 1000);
        }
    }

    /**
     * 每秒钟调用一次
     */
    private void tickWork() {
        mDisableTime--;

        this.setText(String.format(mDisableString, mDisableTime));

        if (mListener != null) {
            mListener.onTick();
        }
        if (mDisableTime <= 0) {
            stopTickWork();
        }
    }

    public void stopTickWork() {
        mTask.cancel();
        mTask = null;
        mDisableTime = DEFAULT_DISABLE_TIME;
        this.setText(mEnableString);
        this.setTextColor(mEnableColor);
        this.setEnabled(true);

        if (mListener != null) {
            mListener.onTickStop();
        }
    }

    public void setEnableColor(int enableColor) {
        this.mEnableColor = enableColor;
        this.setTextColor(enableColor);
    }

    public void setDisableColor(int disableColor) {
        this.mDisableColor = disableColor;
        this.setTextColor(disableColor);
    }

    public boolean isDisable() {
        return mDisableTime < DEFAULT_DISABLE_TIME;
    }

    public void setEnableString(String mEnableString) {

        this.mEnableString = mEnableString;

        if (this.mEnableString != null) {

            this.setText(mEnableString);
        }
    }

    public void setOnSendListener(OnSendListener mListener) {
        this.mListener = mListener;
    }

    public interface OnSendListener {
        void onClickSendValidateButton();

        void onTick();

        void onTickStop();
    }

}
