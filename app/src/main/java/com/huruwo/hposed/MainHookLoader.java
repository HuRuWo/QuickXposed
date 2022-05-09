package com.huruwo.hposed;

import static com.huruwo.hposed.utils.Constants.APP_NAME;
import static com.huruwo.hposed.utils.Constants.MAIN_CLASS_NAME;

import android.app.Activity;
import android.os.Bundle;

import com.huruwo.hposed.hook.HookMainUi;
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
 */

public class MainHookLoader implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(APP_NAME)) {
            LogXUtils.e(lpparam.packageName + "我被hook了");
        }
    }

}
