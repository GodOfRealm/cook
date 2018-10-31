package com.miguan.youmi.app.constant;

/**
 * Copyright (c) 2018 Miguan Inc All rights reserved.
 * Created by Liaopeikun on 2018/6/19
 */
public interface ExtraConstant {

    String SERVER_CONFIG = "server_config"; // 需要服务器配置的数据
    String ACTIVITY_FROM = "activity_from";

    /********************* MainActivity ********************/
    String MAIN_PARAM = "main_param";
    String FINISH_ALL_EXCEPT_MAIN = "finish_all_except_main";//关闭所有，剩下MainActivity
    String MAIN_TAB = "main_tab";//Tab位置
    String MAIN_SECOND_TAB = "main_second_tab";//二级Tab的位置

    /********************* 图片预览 ********************/
    String PREVIEW_IMAGE_DATA = "preview_image_data";//图片预览
    String PREVIEW_IMAGE_POSITION = "preview_image_position";//图片预览默认的显示位置

    /********************* 视频播放 ********************/
    String PLAY_VIDEO_DATA = "play_video_data";//视频播放列表
    String PLAY_VIDEO_CONTENT = "play_video_content";//底部显示的文字
    String PLAY_VIDEO_POSITION = "play_video_position";//列表的某个视频开始播放
    String PLAY_VIDEO_AUTO_PLAY = "play_video_auto_play";//是否自动播放

    String IS_ACTIVITY_INIT = "is_activity_init"; // 是否初始化

    String ACTIVITY_CONFIG = "activity_config";

    String TOKEN_ENTITY = "token_entity"; // 登录标识

    String USER_INFO = "user_bean"; // 用户信息

    String CUSTOMER_SERVICE_USER_INFO = "customer_service_user_info"; // 客服信息

    String USER_ID = "user_id"; // 用户ID
    String USER_PHOTO = "user_photo"; // 用户头像
    String USER_NICKNAME = "user_nickname"; // 用户昵称
    String USER_GENDER = "user_gender"; // 用户性别
    String USER_LIST = "user_list"; // 用户列表
    String USER_FROM = "user_from";

    String AUDITED = "audited"; // 是否已认证

    String ACCOUNT_TYPE = "account_type"; // 注册账号或者找回密码

    String WEB_URL = "web_url"; // 网页地址
    String WEB_IS_ANALYSIS = "web_is_analysis"; // 是否统计

    String RIGHT_CUSTOM_TYPE = "right_custom_type"; // 右上角显示在线客服类型

    String BIND_STATUS = "bind_status"; // 绑定、未绑定状态

    String BIND_TYPE = "bind_type"; // 绑定类型

    String RECHARGE_TYPE = "recharge_type"; // 充值类型
    String PAYSOURCE_TYPE = "paysource_type"; // 充值来源

    String PLATFORM_TYPE = "platform_type"; // 第三方平台类型

    String THIRD_ID = "third_id"; // 第三方平台唯一ID
    String LOGIN_TYPE = "login_type"; // 登录方式@see LoginTransform.LOGIN_TYPE

    String ALIBIND_OR_UNBIND_TYPE = "alibind_or_unbind_type"; // 打开支付宝绑定、解绑界面
    String INCOME_TYPE = "income_type"; // 收入类型
    String DOKI_OR_DIAMOND_DETAIL_TYPE = "doki_or_diamond_detail_type"; // 余额、钻石明细类型
    String INCOME_OR_DOKI_DETAIL_TYPE = "income_or_doki_detail_type"; // 现金收入、doki 明细类型

    String UDOMAIN = "UDOMAIN";// 上传青牛后返回的地址的域名
    String DETAIL_TYPE = "detail_type"; // 明细类型
    String CUSTOMDIALOG_TYPE = "customdialog_type"; // 自定义弹窗类型
    String ACCEPT_MAIN_ORDER_TYPE_LIST = "accept_main_order_type_list"; // 接单主品类集合
    String SELECT_LABEL = "select_label"; // 选择标签
    String WEEK_SETTING = "week_setting"; // 接单设置，重复日期
    String SELECT_HOBBY = "select_hobby"; // 选择兴趣
    String SELECT_JOB = "select_job"; // 选择工作
    String EXTRA_VERSION = "extra_version";//版本
    String REPORT_ID = "report_id";//被举报的id
    String REPORT_TYPE = "report_type";//举报类型
    String NEED_UPDATE = "need_update"; // 是否需要更新
    String LEVEL_UPGRADE_BEAN = "level_upgrade_bean";//用户等级提升
    String USER_SIGN_IN = "user_sign_in";//用户签到
    String ACCOUNT_DETAIL_DEFAULT_INDEX= "account_detail_default_index";//账户详情默认选中的fragement

    String SLIDE_LIKE_TYPE = "slide_like_type";
    String SLIDE_IS_VOICE = "slide_is_voice";

    /**
     * h5支付地址
     */
    String KEY_PAY_H5URL = "key_pay_h5url";

    /**
     * 关注或者粉丝类型
     */
    String FOLLOWS_TYPE = "follows_type";
    /**
     * 粉丝或者关注搜索类型
     */
    String FANS_SEARCH_TYPE = "fans_search_type";
    // 关注人数
    String SUBSCRIBE_COUNT = "subscribe_count";
    // 粉丝数量
    String FANS_COUNT = "fans_count";

    /********************* 技能 ********************/
    String SKILL_CATEGORY_OPEN_TYPE = "skill_category_open_type";//打开技能分类列表类型(默认、选择)
    String SKILL_ITEM = "skill_item";//传递Skill的item的key
    String USER_SKILL_ID = "user_skill_order"; // 用户技能的ID
    String SKILL_ID = "skill_id"; // 技能的ID
    String SKILL_NAME = "skill_name"; // 技能的名字
    String SKILL_PRICE = "skill_price"; // 技能价格
    String SKILL_TAG_LIST = "skill_tag_list"; // 标签列表
    String SKILL_FROM = "skill_from"; // 从哪个页面跳转过来
    String IS_AUDITED = "is_audited"; // 是否认证通过

    /**
     * 技能列表
     */
    String SKILL_LIST = "skill_list";
    String SKILL_LEVEL_LIST = "skill_level_list";

    // im消息eventBus key
    String IM_MESSAGE_BUNDLE_KEY = "im_message_key";
    // 通知类型，不展示界面的消息
    String IM_NOTIFY_MESSAGE_BUNDLE_KEY = "im_notify_message_key";
    // 会话id
    String MSG_TARGET_ID = "targetId";
    String MSG_PHOTO = "conversation_target_photo";
    // 会话标题
    String MSG_TITLE = "title";
    // 从哪里打开聊天页面
    String CONVERSATION_FROM = "conversation_from";

    /********************* 拉黑的操作类型 ********************/
    String BLACK_TYPE = "black_type";//拉黑、移除黑名单

    /********************* 关注的操作类型 ********************/
    String FOLLOW_TYPE = "follow_type";//关注、取消关注


    /********************* 动态 ********************/
    String FEED_ID = "feed_id";//动态ID
    String FEED_LIST_TYPE = "feed_list_type";//动态类别：个人动态、关注、推荐、最新、技能、标签
    String FEED_LIST_PARAMS = "feed_list_params";//参数
    String FEED_LIST_TAG = "feed_list_tag";//动态列表tag
    String FEED_SELECT_TAG = "feed_select_tag";//选择tag返回
    String FEED_SHOW_KEYBOARD = "feed_show_keyboard";//是否弹出键盘

    /********************* 评论 ********************/
    String COMMENT_ID = "comment_id";//评论ID
    String COMMENT_DATA = "comment_data";//评论类型

    /********************* 订单 ********************/
    String ORDER_PUBLISH_ID = "order_publish_id";
    String ORDER_ID = "order_id";
    String ORDER_SOURCE = "order_source";
    String ORDER_COUNT = "order_count"; // 下单数量
    String ORDER_JUDGE_EXIST = "order_judge_exist"; //发布需求页面是否需要判断未完成订单
    String ORDER_GRAB_ID = "order_grab_id"; //抢单中心—>抢单详情页ID
    String ORDER_TYPE = "order_type"; // 订单类型
    String ORDER_LIST_TYPE = "order_list_type"; // 订单列表类型
    String ORDER_SETTING_TYPE = "order_setting_type"; // 抢单派单设置


    // 语音聊天的类型，接收方或者发送方
    String MSG_CHAT_VOICE_TYPE = "msg_chat_voice_type";
    // 用户信息
    String MSG_CHAT_USER_ENTITY = "msg_chat_user_entity";

    /********************* 分享相关 ********************/
    String SHARE_CODE_PROFILE = "daily_share_user";//分享的是个人主页
    String SHARE_CODE_ACTIVITY = "daily_share_activity";//分享的是邀请赚钱【目前直接从h5传递】

    /********************* 装扮相关 ********************/
    String DRESS_DATE = "dress_date";//装扮Item
    String DRESS_WEAR_TYPE = "dress_wear_type";//佩戴或者解除

    String USER_VIP_AMOUNT = "user_vip_amount";
    // 是否关闭过私聊顶部关注悬浮控件
    String HAS_CLOSE_SUBSCRIBE_SUSPEND_VIEW = "HAS_CLOSE_SUBSCRIBE_SUSPEND_VIEW";

    /********************* 聊天室相关 ********************/
    String CHAT_ROOM_ID = "chat_room_id";//聊天室ID
    String CHAT_ROOM_FROM = "chat_room_from";//从哪里进入聊天室
    String CHAT_ROOM_REPEAT_ENTER = "chat_room_repeat_enter";//是否重复进入聊天室
    String CHAT_RANKING_TYPE = "chat_ranking_type"; // 聊天室排行榜
}