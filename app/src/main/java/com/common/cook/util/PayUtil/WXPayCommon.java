package com.common.cook.util.PayUtil;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.common.cook.app.constant.Constant;
import com.common.cook.bean.user.PayWxInfo;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;


public class WXPayCommon {
    public static final int PAY_SUCC = 0;
    public static final int PAY_ERROR = -1;
    public static final int PAY_CANCEL = -2;//用户取消
    private Context mContext;

    private static WXPayCommon mStance;
    private IWXAPI api;

    public static WXPayCommon getInstance(Context context){
        if (mStance == null){
            mStance = new WXPayCommon(context);
        }
        return mStance;
    }

    private WXPayCommon(Context context){
        mContext = context;
        api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(Constant.WX_APP_ID);
    }

    public void sendPayReq(PayWxInfo product) {
        PayReq req = new PayReq();
        req.appId = product.appid;
        req.partnerId = product.partnerid;
        req.prepayId = product.prepayid;
        req.packageValue = product.packageValue;
        req.nonceStr = product.noncestr;
        req.timeStamp = product.timestamp;
        req.sign = product.sign;
        api.sendReq(req);
    }

    public static boolean isWeixinAvilible(Context context) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
