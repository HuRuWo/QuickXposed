package com.huruwo.hposed;

import android.os.Environment;


/**
 * @author liuwan
 * @date 2019/2/28 0028
 * @action
 **/
public class Constants {


    public static final String LOG_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/Xposed/XLog.txt";

    public static final String FILTER_PKGNAME = "com.xingin.xhs";

}
