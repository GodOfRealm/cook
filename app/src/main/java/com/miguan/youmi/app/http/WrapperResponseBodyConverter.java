package com.miguan.youmi.app.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.miguan.youmi.bean.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Copyright (c) 2015. LiaoPeiKun Inc. All rights reserved.
 */

public class WrapperResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final String TAG = WrapperResponseBodyConverter.class.getSimpleName();

    private final Type mType;

    WrapperResponseBodyConverter(Type type) {
        this.mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String result = value.string();

        try {
            if (TextUtils.isEmpty(result)) {
                return new Gson().fromJson(result, mType);
            }

            Object object = new JSONTokener(result).nextValue();
            if (object instanceof JSONObject) {
                JSONObject data = (JSONObject) object;

                if (data.has("code")) {
                    int code = data.getInt("code");
                    if (code != HttpResponseCode.SUCCESS) {
                        if (data.has("msg")) {
                            throw new ServicesException(code, data.getString("msg"));
                        } else {
                            throw new ServicesException(code, "请求服务器失败");
                        }
                    }

                    if (mType.equals(Response.class)) { // 如果是基础类型，就直接解析
                        return new Gson().fromJson(result, mType);
                    } else if (data.has("data") && !data.isNull("data")) {
                        result = data.opt("data").toString();
                    }
                } else {
                    // 防止没有code字段的情况
                    throw new ServicesException(400, "请求服务器失败");
                }
            }
            if (mType.equals(String.class)) {
                //noinspection unchecked
                return (T) result;
            }
            return new Gson().fromJson(result, mType);
        } catch (JSONException | JsonSyntaxException e) {
            throw new ServicesException(HttpResponseCode.ERROR_PARSE_FAIL, "数据解析错误" + e.getLocalizedMessage());
        }
    }

}
