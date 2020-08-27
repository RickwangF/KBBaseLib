package com.kbit.kbbaselib.lifecircle;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.kbit.kbbaselib.util.PackageUtil;
import com.kbit.kbbaselib.util.StringUtil;

public class BaseApplication extends Application {

    private static BaseApplication mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        ActivityManager.registerLifeCicleCallback();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            webViewSetPath(getApplicationContext());
        }
    }

    public static BaseApplication getContext() {
        return mContext;
    }

    @RequiresApi(api = 28)
    public void webViewSetPath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = PackageUtil.getProcessName(getApplicationContext());
            String packageName = PackageUtil.getPackageName(getApplicationContext());
            if (!StringUtil.isEmpty(processName) && !StringUtil.isEmpty(packageName) && !packageName.equals(processName)){//判断不等于默认进程名称
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    public void openLoginActivity() {

    }
}
