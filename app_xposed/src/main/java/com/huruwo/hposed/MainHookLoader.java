package com.huruwo.hposed;

import android.app.Activity;
import android.os.Bundle;

import com.huruwo.hposed.kshook.AppMainUi;
import com.huruwo.hposed.utils.LogXUtils;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author DX
 * 注意：该类不要自己写构造方法，否者可能会hook不成功
 * 开发Xposed模块完成以后，建议修改xposed_init文件，并将起指向这个类,以提升性能
 * 所以这个类需要implements IXposedHookLoadPackage,以防修改xposed_init文件后忘记
 * Created by DX on 2017/10/4.
 */

public class MainHookLoader implements IXposedHookLoadPackage {

    public static String MAIN_APP = "com.huruwo.hposed";
    private static String APP_NAME = "";
    private static final String MAIN_CLASS_NAME = "";


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals(APP_NAME)) {
            LogXUtils.e(lpparam.packageName + "我被hook了");
            hookAppMainUi(lpparam);
        }

    }


    private void hookAppMainUi(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Class aClass = XposedHelpers.findClass("android.app.Activity", loadPackageParam.classLoader);
        if (aClass != null) {
            XposedHelpers.findAndHookMethod(aClass, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Activity activity = (Activity) param.thisObject;
                    String class_name = activity.getClass().getName();
                    LogXUtils.e("界面包名" + class_name + "---");
                    if (MAIN_CLASS_NAME.equals(class_name)) {
                        new AppMainUi(loadPackageParam.classLoader).UI(activity);
                    }
                }
            });
        } else {
            LogXUtils.e("Class为null", true);
        }
    }


}
