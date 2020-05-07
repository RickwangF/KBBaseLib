package com.kbit.kbbaselib.util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.kbit.kbbaselib.R;

public class ToastUtil {
    private static Toast mToast;

    public static void showToast(@NonNull Context context, String text) {
        showShortToast(context, text);
    }

    public static void showShortToast(@NonNull Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(@NonNull Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    public static void showToast(@NonNull Context context, @StringRes int resId) {
        showShortToast(context, resId);
    }

    public static void showShortToast(@NonNull Context context, @StringRes int resId) {
        showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showLongToast(@NonNull Context context, @StringRes int resId) {
        showToast(context, context.getString(resId), Toast.LENGTH_LONG);
    }

    public static void showToast(@NonNull Context context, String text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (mToast != null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(context.getApplicationContext(), text, duration);
        View view = mToast.getView();
        view.setBackgroundResource(R.drawable.bg_toast);
        TextView v = view.findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
//        mToast.setGravity(Gravity.BOTTOM, 0, 0);

        mToast.show();
    }
}
