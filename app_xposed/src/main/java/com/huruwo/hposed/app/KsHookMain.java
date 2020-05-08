package com.huruwo.hposed.app;

import android.app.Activity;
import android.os.Bundle;


import com.huruwo.hposed.utils.LogXUtils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class KsHookMain {

    private ClassLoader classLoader;

    public KsHookMain(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public  void hookAppUi() {

        LogXUtils.e("xposed进入");

        Class aClass = XposedHelpers.findClass("android.app.Activity", classLoader);

        if(aClass!=null){
            XposedHelpers.findAndHookMethod(aClass, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Activity activity = (Activity) param.thisObject;
                    String class_name = activity.getClass().getName();
                    LogXUtils.e("界面包名"+class_name);
                    if ("com.yxcorp.gifshow.HomeActivity".equals(class_name)) {
                        new KSSekiroMainUi().UI(activity,classLoader);
                    }
                }
            });
        }else {
            LogXUtils.e("Class为null",true);
        }
    }

}
