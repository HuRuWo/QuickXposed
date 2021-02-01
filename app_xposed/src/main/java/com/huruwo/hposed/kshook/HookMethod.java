package com.huruwo.hposed.kshook;

import android.util.Base64;

import com.google.gson.reflect.TypeToken;
import com.huruwo.hposed.utils.GsonUtils;
import com.huruwo.hposed.utils.LogXUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XposedHelpers;

/**
 * @author : huruwo
 * @date : 2020/7/22
 * @descrice : 类功能描述
 */
public class HookMethod {

    public static String getSign(Map<String, Class<?>> signMap, String messData) {
        Object res = XposedHelpers.callStaticMethod(signMap.get("testObj"), "getSign", messData);
        return res.toString();
    }


}
