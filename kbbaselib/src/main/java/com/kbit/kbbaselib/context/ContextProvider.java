package com.kbit.kbbaselib.context;

import android.annotation.SuppressLint;
import android.content.Context;

public class ContextProvider {
    private Context mContext;

    @SuppressLint("StaticFieldLeak")
    private static volatile ContextProvider instance;

    private ContextProvider(Context context) {
        this.mContext = context;
    }

    public static void setContext(Context context) {
        instance.mContext = context;
    }

    public static Context getContext() {
        if (instance == null) {
            Context context = AppContextProvider.mContext;
            if (context == null) {
                throw new IllegalStateException("ApplicationContextProvider context is null");
            }

            instance = new ContextProvider(context);
        }

        return instance.mContext;
    }
}
