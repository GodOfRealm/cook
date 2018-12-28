package com.common.cook.app.http;

/**
 * 作者: liyu on  2018/1/30 0030 19:39
 * 功能描述:请求返回码
 * 备注:
 */
public interface HttpResponseCode {

    int SUCCESS = 200;//请求成功
    int ERROR_NO_PERMISSION = 401; // 未授权的访问
    int ERROR_NOT_EXIST = 404; // 未找到对应的服务
    int ERROR_INTERNAL_SERVER = 500; // 服务器发生错误
    int ERROR_REJECTED = 403; // 请求被服务器拒绝
    int ERROR_PARAM_ERROR = 4001;// 参数异常
    int ERROR_DEVICE_DISABLE = 4002;// 设备被禁用
    int ERROR_USER_DISABLE = 4003;// 用户被禁用
    int ERROR_UPLOAD_FILE = 4004; // 上传文件失败
    int ERROR_BUY_DIAMOND = 4013;//购买钻石(目前购买挂件余额不足，会返回该值)

    int ERROR_BALANCE_NOT_ENOUGH = 500; // 余额不足

    int ERROR_PARSE_FAIL = -1000;// 数据解析错误
    int ERROR_UNKNOWN = -1001; // 未知错误
    int ERROR_UNKNOWN_HOST = -1002; // 网络不可用
    int ERROR_SOCKET_TIMEOUT = -1003;// 请求网络超时

}
