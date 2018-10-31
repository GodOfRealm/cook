package com.miguan.youmi.app;


import com.miguan.youmi.module.common.CommonNavigator;
import com.miguan.youmi.module.main.MainNavigator;

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
    private MainNavigator mMainNavigator;//main路由
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
//        mAccountNavigator = new AccountNavigator();
//        mUserNavigator = new UserNavigator();
        mMainNavigator = new MainNavigator();
//        mHomeNavigator = new HomeNavigator();
//        mOrderNavigator = new OrderNavigator();
//        mMsgNavigator = new MsgNavigator();
//
//        mFeedNavigator = new FeedNavigator();
//        mProfileNavigator = new ProfileNavigator();
//
//        mSkillNavigator = new SkillNavigator();
//        mChatNavigator = new ChatNavigator();
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
//    /**
//     * Desc: 获取账号模块跳转
//     * <p>
//     * Author: 廖培坤
//     * Date: 2018-06-27
//     *
//     * @return account navigator
//     */
//    public AccountNavigator getAccountNavigator() {
//        return mAccountNavigator;
//    }
//
//    /**
//     * Desc: 获取账号模块跳转
//     * <p>
//     * Author: 廖培坤
//     * Date: 2018-06-27
//     *
//     * @return account navigator
//     */
//    public UserNavigator getUserNavigator() {
//        return mUserNavigator;
//    }
//
    /**
     * Desc:main模块跳转
     * <p>
     * Author: SonnyJack
     * Date: 2018-06-28
     *
     * @return main navigator
     */
    public MainNavigator getMainNavigator() {
        return mMainNavigator;
    }
//
//    /**
//     * Desc: 首页相关跳转
//     * <p>
//     * Author: 廖培坤
//     * Date: 2018-07-30
//     *
//     * @return home navigator
//     */
//    public HomeNavigator getHomeNavigator() {
//        return mHomeNavigator;
//    }
//
//    /**
//     * Desc:订单模块跳转
//     * <p>
//     * Author: 张文顺
//     * Date: 2018-07-10
//     *
//     * @return mOrderNavigator navigator
//     */
//    public OrderNavigator getOrderNavigator() {
//        return mOrderNavigator;
//    }
//
//
//    /**
//     * Desc: 个人主页模块跳转
//     * <p>
//     * Author: 廖培坤
//     * Date: 2018-07-09
//     *
//     * @return user home navigator
//     */
//    public ProfileNavigator getProfileNavigator() {
//        return mProfileNavigator;
//    }
//
//    /**
//     * Desc:动态详情模块跳转
//     * <p>
//     * Author: 张文顺
//     * Date: 2018-07-13
//     *
//     * @return feed navigator
//     */
//    public FeedNavigator getFeedNavigator() {
//        return mFeedNavigator;
//    }
//
//    /**
//     * Desc: 我的消息模块跳转
//     * <p>
//     * Author: QiuRonaC
//     * Date: 2018-07-13
//     *
//     * @return msg navigator
//     */
//    public MsgNavigator getMsgNavigator() {
//        return mMsgNavigator;
//    }
//
//
//    /**
//     * Desc: 技能路由
//     * <p>
//     * Author: SonnyJack
//     * Date: 2018-07-24
//     *
//     * @return skill navigator
//     */
//    public SkillNavigator getSkillNavigator() {
//        return mSkillNavigator;
//    }
//
//
//    /**
//     * Desc: 聊天室路由
//     * <p>
//     * Author: SonnyJack
//     * Date: 2018-09-18
//     *
//     * @return chat navigator
//     */
//    public ChatNavigator getChatNavigator() {
//        return mChatNavigator;
//    }
}
