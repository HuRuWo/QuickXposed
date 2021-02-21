package com.huruwo.hposed.utils;

import android.util.Log;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;


/**
 * @author liuwan
 * @date 2018/9/5 0005
 * @action
 **/
public class LogXUtils {

    public static String TAG = "LogXUtils";
    public static String DerviceId = "";

    //v d i w e a

    public static void init(String tag, String derviceId) {
        TAG = tag;
        DerviceId = derviceId;
    }

    public static void v(String log) {
        if (!StringUtils.isEmpty(log)) {
            Log.v(TAG, log);
        }
    }

    public static void d(String log) {
        if (!StringUtils.isEmpty(log)) {
            Log.d(TAG, log);
        }
    }

    public static void i(String log) {

        if (!StringUtils.isEmpty(log)) {
            Log.i(TAG, log);
        }

    }

    public static void w(String log) {
        if (!StringUtils.isEmpty(log)) {
            Log.w(TAG, log);
        }
    }

    public static void e(String log) {
        if (!StringUtils.isEmpty(log)) {
            Log.e(TAG, log);
        }
    }


    public static void e(String log, boolean toast) {
        if (!StringUtils.isEmpty(log)) {
            LogXUtils.e(log);
            if (toast) {
                ToastUtils.showLong(log);
            }
        }
    }

    public static void e(String log, boolean toast,boolean save) {
        if (!StringUtils.isEmpty(log)) {
            LogXUtils.e(log);
            if (toast) {
                ToastUtils.showLong(log);
            }
            if(save){

            }
        }
    }

}
