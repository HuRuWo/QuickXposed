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

    public static String getSign3(Map<String,Class<?>> signMap,String messData){
       Object res = XposedHelpers.callStaticMethod(signMap.get("kwaiAppObject"), "getSigWrapper", messData);
       return res.toString();
    }

    public static String getDeviceInfo(Map<String,Class<?>> signMap,String messData) throws UnsupportedEncodingException {
        String mess = URLDecoder.decode(messData, "UTF-8");
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> concurrentHashMap = GsonUtils.fromJson(mess, type);
        Object aMessageNano = XposedHelpers.callStaticMethod(signMap.get("abfObject"), "a", concurrentHashMap);
        byte[] bytes = (byte[]) XposedHelpers.callStaticMethod(signMap.get("messageNanoObject"), "toByteArray", aMessageNano);
        String encode = URLEncoder.encode(Base64.encodeToString((byte[]) XposedHelpers.callStaticMethod(signMap.get("ksdkInnerObject"), "atlasEncrypt", bytes), 0), "utf-8");
        String valueOf = String.valueOf(Long.valueOf(System.currentTimeMillis()));
        Map<String, String> dataMap = new HashMap<>();
        String b = (String) XposedHelpers.callMethod(XposedHelpers.callStaticMethod(signMap.get("dfpaaaObject"), "a"), "b");
        dataMap.put("deviceInfo", encode);
        dataMap.put("sign", (String) XposedHelpers.callStaticMethod(signMap.get("ksdkInnerObject"), "atlasSign", (b + valueOf + "2" + encode)));
        dataMap.put("ts", valueOf);
        dataMap.put("productName", b);
        String res = GsonUtils.toJson(dataMap);
        LogXUtils.e(res);
        return res;
    }

}
