package com.huruwo.hposed.utils;

import android.util.Log;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;



/**
 * @author
 * @date 2019/4/9 0009
 * @action
 **/
public class XposedPrintUtils {

    public static String printFieldName(Object object) {

        StringBuffer stringBuffer = new StringBuffer();

        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                stringBuffer.append(f.getName() + ":" + f.get(object) + "\n");
            } catch (IllegalAccessException e) {
                stringBuffer.append(f.getName() + ":" + e.getMessage() + "\n");
            }

        }

        return stringBuffer.toString();
    }

    public static String printArgs(XC_MethodHook.MethodHookParam param) {

        StringBuffer stringBuffer = new StringBuffer();
        for (Object f : param.args) {
            if (f != null) {
                if (f instanceof Object[]) {
                    Object[] ff = (Object[]) f;
                    for (Object o : ff) {
                        stringBuffer.append("参数值Object[]:[" + o.getClass().getName() + "][" + o.toString() + "]");
                    }
                } else if (f instanceof byte[]) {
                    byte[] ff = (byte[]) f;
                    stringBuffer.append("参数值byte[]:[" + ByteToStr(ff) + "]");
                } else {
                    stringBuffer.append("参数值:[" + f.getClass().getName() + "][" + f.toString() + "]");
                }
            } else {
                stringBuffer.append("参数值:[null][null]");
            }
        }
        return stringBuffer.toString();
    }

    public static void hookClassMethod( String class_name,ClassLoader classLoader, String method_name,boolean ex) {
        XposedBridge.hookAllMethods(XposedHelpers.findClass(class_name, classLoader), method_name, new Log_XC_Hook(class_name + "  " + method_name,ex));
    }

    public static class Log_XC_Hook extends XC_MethodHook {

        private String class_method;
        private boolean ex;

        public Log_XC_Hook( String class_method, boolean ex) {
            this.class_method = class_method;
            this.ex = ex;
        }

        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            super.beforeHookedMethod(param);
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\n方法["+class_method+"]\n");
            if(param.args!=null&&param.args.length>0) {
                stringBuffer.append("参数["+XposedPrintUtils.printArgs(param)+"]\n");
            }
            if(ex) {
                String bt = Log.getStackTraceString(new Exception());
                stringBuffer.append("调用栈["+ bt+"]\n");
            }
            stringBuffer.append("返回[" + GsonUtils.toJson(param.getResult())+"]");
            LogXUtils.e(stringBuffer.toString());
        }
    }

    public static String ByteToStr(byte[] bArr) {
        int i = 0;
        char[] toCharArray = "0123456789abcdef".toCharArray();

        char[] cArr;
        for(cArr = new char[bArr.length * 2]; i < bArr.length; ++i) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            cArr[i3] = toCharArray[i2 >>> 4];
            cArr[i3 + 1] = toCharArray[i2 & 15];
        }

        return new String(cArr);
    }

}
