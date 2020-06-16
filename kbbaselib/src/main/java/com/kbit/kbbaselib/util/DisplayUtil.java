package com.kbit.kbbaselib.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.kbit.kbbaselib.context.ContextProvider;

import java.io.File;
import java.io.FileOutputStream;

public class DisplayUtil {

    public static int getScreenWidthDp() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        int screenWidth = (int)(displayMetrics.widthPixels / getDensity());
        return screenWidth;
    }

    public static int getScreenHeightDp() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        int screenHeight = (int) (displayMetrics.heightPixels / getDensity());
        return screenHeight;
    }

    public static int getScreenWidthPx() {
        return getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeightPx() {
        return getDisplayMetrics().heightPixels;
    }

    public static float getDensity() {
        return getDisplayMetrics().density;
    }

    public static int getDensityDpi() {
        return getDisplayMetrics().densityDpi;
    }

    public static float dpToPx(int dp) {
        return getDisplayMetrics().density * dp;
    }

    public static float pxToDp(int px) {
        return px / getDisplayMetrics().density;
    }

    private static DisplayMetrics getDisplayMetrics() {
        WindowManager windowManager = (WindowManager) ContextProvider.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        assert windowManager != null;
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String screenshot(Activity context) {
        // 获取屏幕
        String path = "";
        View dView = context.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
                // 图片文件路径
                path = sdCardPath + File.separator + System.currentTimeMillis() + ".jpg";

                File file = new File(path);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

}
