package com.miguan.youmi.app.analysis;

import com.miguan.youmi.bean.account.User;

import com.miguan.youmi.util.PickUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Desc:
 * <p>
 * Author: 廖培坤
 * Date: 2018-09-30
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class AnalysisManager {

    private static AnalysisManager sAnalysisManager;

    private AnalysisManager() {
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-09-30
     *
     * @return analysis event manager
     */
    public static AnalysisManager getInstance() {
        if (null == sAnalysisManager) {
            synchronized (AnalysisManager.class) {
                if (null == sAnalysisManager) {
                    sAnalysisManager = new AnalysisManager();
                }
            }
        }
        return sAnalysisManager;
    }

    /**
     * Desc:
     * <p>
     * Author: 廖培坤
     * Date: 2018-09-30
     */
    public void init() {
        try {
            // 设置公共属性
            User user = PickUtils.getUser();
            if (user != null) {
                SensorsDataAPI.sharedInstance().login(user.getUser_id() != null ? user.getUser_id() : "");

                JSONObject properties = new JSONObject();
                boolean isIdentity = PickUtils.getUser() != null && PickUtils.getUser().isIdentity();
                properties.put("userType", isIdentity ? "达人" : "普通");
                properties.put("accountType", user.isIs_test() ? "测试" : "正式");
                properties.put("nickName", user.getNick_name());
                properties.put("userLevel", user.getSum_task_point());
                properties.put("birthday", user.getBirthday());
                properties.put("city", user.getArea());
                properties.put("age", user.getAge());
                SensorsDataAPI.sharedInstance().profileSet(properties);

                JSONObject propertiesOnce = new JSONObject();
                propertiesOnce.put("gender", user.isMale() ? "男" : "女");
                propertiesOnce.put("phoneNumber", user.getSensitive_tel());
//                propertiesOnce.put(SensorsEvent.SIGN_UP_TYPE, user)
                propertiesOnce.put("registerTime", user.getReg_time());
                SensorsDataAPI.sharedInstance().profileSetOnce(propertiesOnce);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desc: 公共属性设置
     * <p>
     * Author: 廖培坤
     * Date: 2018-10-10
     */
    public void registerSuperProperties() {
        try {
            JSONObject propertiesPublic = new JSONObject();
            User user = PickUtils.getUser();
            propertiesPublic.put("platform_type", "Android");
            propertiesPublic.put("user_type", user != null && user.isIdentity() ? "达人" : "普通用户");
            propertiesPublic.put("account_type", user != null && user.isIs_test());
            SensorsDataAPI.sharedInstance().registerSuperProperties(propertiesPublic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }







}
