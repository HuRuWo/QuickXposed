package com.huruwo.xposed.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author liuwan
 * @date 2019/4/11 0011
 * @action
 **/
public class GsonUtils {

    private static Gson gson = new Gson();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            LogXUtils.e("JSON异常" + e.getMessage(), true);
            return null;
        }
    }

    public static <T> List<T> listFromJson(String json, Type type) {

        try {
            List<T> lists = gson.fromJson(json, new TypeToken<List<Type>>() {
            }.getType());

            return lists;
        } catch (Exception e) {
            LogXUtils.e("JSON异常" + e.getMessage(), true);
            return null;
        }
    }
}
