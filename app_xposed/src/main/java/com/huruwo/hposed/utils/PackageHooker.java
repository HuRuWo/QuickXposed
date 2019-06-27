package com.huruwo.hposed.utils;

import com.huruwo.hposed.utils.LogXUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import dalvik.system.DexFile;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author liuwan
 * @date 2019/4/30 0030
 * @action hook 全部方法
 **/
public class PackageHooker {


    private final XC_LoadPackage.LoadPackageParam loadPackageParam;

    public PackageHooker(XC_LoadPackage.LoadPackageParam param) {
        loadPackageParam = param;
        try {
            hook();
        } catch (IOException | ClassNotFoundException e) {
            LogXUtils.e(e.getMessage());
        }
    }

    public void hook() throws IOException, ClassNotFoundException {
        DexFile dexFile = new DexFile(loadPackageParam.appInfo.sourceDir);
        Enumeration<String> classNames = dexFile.entries();
        while (classNames.hasMoreElements()) {
            String className = classNames.nextElement();

            if (isClassNameValid(className)) {
                Class clazz = Class.forName(className, false, loadPackageParam.classLoader);

                for (Method method : clazz.getDeclaredMethods()) {
                    if (!Modifier.isAbstract(method.getModifiers())) {
                        XposedBridge.hookMethod(method, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                LogXUtils.e("类:[" + param.thisObject.getClass() + "] 方法:[" + param.method.getName() + "]" + " 返回值:[" + param.getResult() + "]");
                            }
                        });
                    }
                }
            }
        }
    }

    public boolean isClassNameValid(String className) {
        return className.startsWith(loadPackageParam.packageName)
                && !className.contains("$")
                && !className.contains("BuildConfig")
                && !className.equals(loadPackageParam.packageName + ".R");
    }

}
