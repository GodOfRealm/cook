package com.common.cook.app;


import com.common.cook.module.common.CommonNavigator;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Desc:跳转统一入口
 * <p>
 * Author: 廖培坤
 * Date: 2018-06-27
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
@Singleton
public class Navigator {

    private static Navigator sInstance;

    private CommonNavigator mCommonNavigator; // 通用模块路由
//    private AccountNavigator mAccountNavigator; // 账号模块路由
//    private UserNavigator mUserNavigator;
//    private MainNavigator mMainNavigator;//main路由
//    private RepaymentNavigator mRepaymentNavigator;//路由

//    private HomeNavigator mHomeNavigator;//main路由
//    private OrderNavigator mOrderNavigator;//订单路由
//    private MsgNavigator mMsgNavigator;//消息路由
//    private FeedNavigator mFeedNavigator;//动态路由
//    private SkillNavigator mSkillNavigator;//技能路由
//
//
//    private ProfileNavigator mProfileNavigator; // 个人主页
//    private ChatNavigator mChatNavigator;//聊天室

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     *
     * @return navigator
     */
    public static Navigator getInstance() {
        if (null == sInstance) {
            synchronized (Navigator.class) {
                if (null == sInstance) {
                    sInstance = new Navigator();
                }
            }
        }
        return sInstance;
    }

    /**
     * Desc:
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     */
    @Inject
    public Navigator() {
        mCommonNavigator = new CommonNavigator();
//        mMainNavigator = new MainNavigator();
//        mRepaymentNavigator = new RepaymentNavigator();

    }


    /**
     * Desc: 获取通用模块路由
     * <p>
     * Author: 廖培坤
     * Date: 2018-07-06
     *
     * @return common navigator
     */
    public CommonNavigator getCommonNavigator() {
        return mCommonNavigator;
    }

//
    /**
     * Desc:main模块跳转
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     *
     * @return main navigator
     */
//    public MainNavigator getMainNavigator() {
//        return mMainNavigator;
//    }
    /**
     * Desc:模块跳转
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     *
     * @return main navigator
     */
//    public RepaymentNavigator getRepaymentNavigator() {
//        return mRepaymentNavigator;
//    }


}
