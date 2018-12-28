package com.common.cook.core.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户验证工具类
 *
 * @author vendor
 */
public class GsonUtil {

    private static final String TAG = "GsonUtil";

    /**
     * 解析数据-对象
     *
     * @param <T>
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T parse(String jsonString, Class<T> cls) {
        T t = null;

        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        }

        return t;
    }

    /**
     * 解析数据-对象
     *
     * @param <T>
     * @param jsonString
     * @param typeOfT
     * @return
     */
    public static <T> T parse(String jsonString, Type typeOfT) {
        T t = null;

        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, typeOfT);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        }

        return t;
    }

    /**
     * 解析数据-数组
     *
     * @param <T>
     * @param jsonString
     * @param type       new TypeToken<List<T>>() {}.getType()
     * @return
     */
    public static <T> List<T> parseList(String jsonString, Type type) {
        List<T> list = new ArrayList<>();

        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, type);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        }

        return list;
    }

    /**
     * 将对象解析成json
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }


    /**
     * Desc: String -> JSONObject
     * <p>
     * Author: SonnyJack
     * Date: 2018-09-06
     *
     * @param json
     * @return json object
     */
    public static JSONObject parseStrJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
