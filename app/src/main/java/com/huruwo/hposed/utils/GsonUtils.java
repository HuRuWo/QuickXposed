package com.huruwo.hposed.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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
        return gson.fromJson(json, type);
    }

    public static <T> List<T> listFromJson(String json) {

        List<T> lists = gson.fromJson(json, new TypeToken<List<Type>>() {
        }.getType());

        return lists;

    }

    public static <K, V> Map<K, V> mapFromJson(String json) {

        Map<K, V> map = gson.fromJson(json, new TypeToken<Map<K, V>>() {
        }.getType());
        return map;

    }
}
