package com.huruwo.hposed;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.huruwo.hposed.utils.GsonUtils;
import com.huruwo.hposed.kshook.KSSekiroMainUi;
import com.huruwo.hposed.utils.LogXUtils;

import java.io.File;
import java.util.Map;

import dalvik.system.DexClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.huruwo.hposed.utils.Constants.DOU_YIN_PACKAGE_NAME;
import static com.huruwo.hposed.utils.Constants.KUAISHOU_PACKAGE_NAME;

/**
 * @author DX
 * 注意：该类不要自己写构造方法，否者可能会hook不成功
 * 开发Xposed模块完成以后，建议修改xposed_init文件，并将起指向这个类,以提升性能
 * 所以这个类需要implements IXposedHookLoadPackage,以防修改xposed_init文件后忘记
 * Created by DX on 2017/10/4.
 */

public class MainHookLoader implements IXposedHookLoadPackage {

    public static String MAIN_APP = "com.huruwo.hposed";

    private int flag = 0;
    private static String INTERCEPTORPATH = "/storage/emulated/0/main_xposed.dex";



    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals(DOU_YIN_PACKAGE_NAME)) {
            LogXUtils.e(lpparam.packageName + "我被hook了");
        } else if (lpparam.packageName.equals(MAIN_APP)) {
            LogXUtils.e(lpparam.packageName + "我被hook了");
            hookMainApplication();
        }else if(lpparam.packageName.equals(KUAISHOU_PACKAGE_NAME)){
            LogXUtils.e(lpparam.packageName + "我被hook了");
            hookKsAppUi(lpparam);
            Class abfCls = XposedHelpers.findClass("com.kuaishou.dfp.a.b.f", lpparam.classLoader);
            Class bfCls = XposedHelpers.findClass("com.kuaishou.dfp.b.f", lpparam.classLoader);
            Class formCls = XposedHelpers.findClass("okhttp3.FormBody$a", lpparam.classLoader);


            XposedHelpers.findAndHookMethod(abfCls, "a", Map.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                    LogXUtils.e("1========" + GsonUtils.toJson(param.args[0]));
                    LogXUtils.e("1========" + param.getResult().toString());//
                }
            });

            XposedHelpers.findAndHookMethod(bfCls, "a", String.class,byte[].class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if(param.args[0].toString().equals("deviceInfo")) {
                        LogXUtils.e("2========" + ((byte[]) param.args[1]).length);//2========2372/2373
                    }
                }
            });

            XposedHelpers.findAndHookMethod(formCls, "a", String.class,String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if(param.args[0].toString().equals("deviceInfo")) {
                        LogXUtils.e("3========" + ((String) param.args[1]).length());//3========3553
                        //param.setResult(null);
                    }
                }
            });
        }

    }


    private void hookKsAppUi(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        Class aClass = XposedHelpers.findClass("android.app.Activity", loadPackageParam.classLoader);
        if (aClass != null) {
            XposedHelpers.findAndHookMethod(aClass, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Activity activity = (Activity) param.thisObject;
                    String class_name = activity.getClass().getName();
                    LogXUtils.e("界面包名" + class_name+"---");
                    if ("com.yxcorp.gifshow.HomeActivity".equals(class_name)) {
                        new KSSekiroMainUi(loadPackageParam.classLoader).UI(activity);
                    }
                }
            });
        } else {
            LogXUtils.e("Class为null", true);
        }
    }

    private void hookMainApplication(){
        XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Context context = (Context) param.thisObject;
                if (flag == 0) {
                    ClassLoader mLoader = context.getClassLoader();
                    File dexOutputDir = context.getDir("dex", 0);
                    DexClassLoader mDexClassLoader = new DexClassLoader(INTERCEPTORPATH, dexOutputDir.getAbsolutePath(), null, mLoader);
                }
            }

        });
    }
}
