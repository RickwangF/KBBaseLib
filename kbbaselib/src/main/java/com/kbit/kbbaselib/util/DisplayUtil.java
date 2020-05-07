package com.kbit.kbbaselib.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.kbit.kbbaselib.context.ContextProvider;

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

}
