package com.huruwo.hposed;

import android.app.Application;
import android.content.Context;
import android.support.v4.util.ArraySet;
import android.text.TextUtils;

import com.huruwo.hposed.utils.LogXUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.huruwo.hposed.HookAppAllMethod.hookClassInfo;

/**
 * @author DX
 * 注意：该类不要自己写构造方法，否者可能会hook不成功
 * 开发Xposed模块完成以后，建议修改xposed_init文件，并将起指向这个类,以提升性能
 * 所以这个类需要implements IXposedHookLoadPackage,以防修改xposed_init文件后忘记
 * Created by DX on 2017/10/4.
 */

public class MainHookLoader implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        new PackageHooker(lpparam);
    }
}
