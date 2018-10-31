package com.miguan.youmi.app.constant;

public interface ARouterPaths {

    /***************  common模块 *****************/
    String COMMON_WEB = "/common/web";
    String COMMON_PREVIEW_IMAGE = "/common/preview_image";//图片预览
    String COMMON_PLAY_VIDEO = "/common/play_video";//视频播放

    /***************  main模块 *****************/
    String MAIN_HOME = "/main/home";
    String CHAT_RANKSING = "/main/rankings";//排行版
    String MAIN_GUIDE = "/main/guide";//引导页
    String MAIN_QUESTIONS = "/main/questions";
    String MAIN_RECOMMEND = "/main/recommend";

    /***************  home模块 *****************/
    String HOME_SEARCH = "/home/search";//搜索页
    String HOME_SKILL_USER_LIST = "/search/search_skill_user"; // 技能对应的用户列表
    String HOME_SLIDE = "/search/search_paddle"; //划一划
    String HOME_SLIDE_ME_LIKE = "/subscribe/like_list";//我喜欢的(划一划)
    String HOME_SLIDE_RECORD = "/home/slide/record";

    /***************  聊天室模块 *****************/
    String CHAT_ROOM_LIST = "/chat_room/list";//聊天室列表
    String CHAT_ROOM_MAIN = "/chat_room/mian";//聊天室主页
    String CHAT_ROOM_REPORT = "/chat_room/report";//聊天室举报

    /***************  技能模块 *****************/
    String SKILL_CATEGORY = "/skill/category";//分类

    /***************  account 模块 *****************/
    String ACCOUNT_LOGIN_HOME = "/account/login/home";
    String USER_LOGIN = "/user/login";
    String USER_REGISTER = "/user/register";
    String USER_PWD_LOGIN = "/user/pwdLogin";
    String USER_PROFILE = "/user/profile";
    String ACCOUNT_BIND_MOBILE = "/account/bindMobile";
    String ACCOUNT_FORGOT_PWD = "/account/account_forgot_pwd";

    /***************  个人主页模块 *****************/
    String PROFILE_HOME = "/user/home";
    String PROFILE_SKILL_DETAIL = "/profile/skill/detail";

    String USER_SETTING = "/user/setting";
    String USER_ACCOUNT_BIND = "/user/account_bind";
    String USER_HELP_FEEDBACK = "/user/help_feedback";
    String USER_ABOUT = "/user/about";
    String USER_FOLLOWS = "/user/follows";
    String USER_VISITORS = "/user/visitors";
    String USER_FEEDBACK = "/user/feedback";
    String USER_BLACK_LIST = "/user/blacklist";
    String USER_CHANGE_PWD = "/user/changepwd";
    String USER_OTHER_BIND = "/user/other_bind";
    String USER_VIDEO_AUTOPLAY = "/user/video_autoplay";
    String USER_REPORT = "/user/report";
    String USER_NOTIFICATIONS = "/user/notifications";
    String USER_PAYMENT = "/user/payment";
    String USER_MINEWALLET = "/user/minewallet";
    String USER_SELECTHOBBY = "/user/selecthobby";
    String USER_SELECTJOB = "/user/selectjob";
    String USER_BINDALI = "/user/bindali";
    String USER_INCOME = "/user/income";
    String USER_CHANGE_ALI = "/user/change_ali";
    String USER_BALANCEORDIAMOND_DETAIL = "/user/balance_or_diamond_detail";
    String USER_CASHORDOKI_DETAIL = "/user/cash_or_doki_detail";
    String USER_VIPCENTER = "/user/vipcenter";
    String USER_EDITPROFILE = "/user/editprofile";
    String USER_ACCEPT_ORDER_SETTING = "/user/accept_order_setting";
    String USER_SELECT_DATE = "/user/select_date";
    String USER_PAY = "/user/pay";//h5唤起微信支付
    String USER_ACCOUNT_DETAIL= "/user/account_detail";//账户明细
    String USER_TASK_CENTER= "/user/user_task_center";//任务大厅
    String USER_DRESS_HALL = "/user/user_dress_hall";//装扮大厅
    String USER_DRESS_MINE = "/user/user_dress_mine";//我的装扮


    // 粉丝搜索
    String USER_FANS_SEARCH = "/user/fansSearch";
    // 身份认证
    String USER_IDENTITY_AUTH = "/user/identity_auth";
    // 达人认证
    String USER_TALENT_CERTIFICATION = "/user/talent_certification";
    // 能力认证
    String USER_APPLY_AUDIT = "/user/ablility_certifacation";
    // 提交审核
    String USER_SUBMIT_AUDIT = "/user/submit_audit";

    /***************  order模块 *****************/
    String ORDER_PUBLISH_ORDER = "/order/publish";//发布订单
    String ORDER_WAIT_RECEIPT = "/order/wait_receipt";//等待接单
    String ORDER_PUBLISH_DETAIL = "/order/detail";//订单详情页
    String ORDER_TALENT_ORDER_DETAIL = "/order/talent_detail";//达人订单详情页
    String ORDER_CANCEL_ORDER = "/order/cancel_order";//取消订单
    String ORDER_HEART_BEAT = "/order/heartBeat";//打开pick心动达人
    String ORDER_ADD_EVALUATION = "/skillOrderEvaluate/evaluate"; //订单评价（评价达人、用户）
    String ORDER_EVALUATION_DETAIL = "/order/evaluation_detail";//接单详情
    String ORDER_PLACE_ORDER = "/order/place"; // 直接下单
    String ORDER_PAY = "/order/pay"; // 支付订单
    String ORDER_LIST = "/skillOrder/my_order_list"; // 接单记录和我的订单
    String ORDER_SEND_LIST = "/order/send_list";//派单列表

    /***************  动态模块 *****************/
    String FEED_DETAIL = "/feed/detail";//打开动态详情页
    String FEED_PUBLISH_FEED = "/feed/publish_feed";//打开发布动态界面
    String FEED_SEARCH_LABEL = "/feed/search_label";//搜索标签界面
    String FEED_COMMENT_DETAIL = "/feed/comment_detail";//动态详情页
    String FEED_LIST_TAG = "/feed/list_tag";//动态tag列表

    // 消息模块
    String MSG_CONVERSATION = "/msg/conversation";
    String MSG_GRAB_ORDER_SETTING = "/msg/grab_order_setting";
    String MSG_GRAB_ORDER_DETAIL = "/msg/grab_order_detail";
    String MSG_USER_EVALUATION_LIST = "/msg/user_evaluation_list";
    String MSG_GRAB_ORDER_CENTER = "/msg/grab_order_center";
    String MSG_INTERACTION = "/message/interaction"; // 互动消息
    String MSG_SYSTEM = "/message/notification"; // 系统通知
    String MSG_ORDER = "/orderMsg/list"; // 订单消息

    // 语音通话页面
    String MSG_VOICE_CHAT = "/msg/audio_chat";
}
