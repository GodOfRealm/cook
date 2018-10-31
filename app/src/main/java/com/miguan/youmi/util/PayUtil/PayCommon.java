package com.miguan.youmi.util.PayUtil;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;

public class PayCommon {
    private Activity mContext;
    private payListener mPayListener;

    public void setPayListener(payListener payListener) {
        this.mPayListener = payListener;
    }

    public PayCommon(Activity context, payListener listener) {
        mContext = context;
        this.mPayListener = listener;
    }

    /**
     * 支付宝支付
     */
    public void pay(final String payInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mContext);
                // 调用支付接口
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Result resultObj = new Result((String) msg.obj);
                    String resultStatus = resultObj.getResultStatus();
                    LogUtils.e(PayCommon.class, "pay: " + resultObj);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (mPayListener != null) {
                            mPayListener.paySucc();
                        }
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(resultStatus, "4000")) {
                            Toast.makeText(mContext, "请检查是否安装了支付宝", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mPayListener != null) {
                                mPayListener.payFail();
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    private static final int SDK_PAY_FLAG = 1;


    /**
     * @author ly
     * 支付回调
     */
    public interface payListener {
        void paySucc();

        void payFail();
    }
}
