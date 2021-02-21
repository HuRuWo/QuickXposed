package com.huruwo.hposed.utils;

import android.app.Application;
import android.content.Context;

import java.io.File;

import dalvik.system.DexClassLoader;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class DexUtils {

    private static String INTERCEPTORPATH = "/storage/emulated/0/main_xposed.dex";
    private int flag = 0;

    private void hookMainApplication(){
        XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Context context = (Context) param.thisObject;
                //if (flag == 0) {
                    ClassLoader mLoader = context.getClassLoader();
                    File dexOutputDir = context.getDir("dex", 0);
                    DexClassLoader mDexClassLoader = new DexClassLoader(INTERCEPTORPATH, dexOutputDir.getAbsolutePath(), null, mLoader);
                //}
            }

        });
    }

}
