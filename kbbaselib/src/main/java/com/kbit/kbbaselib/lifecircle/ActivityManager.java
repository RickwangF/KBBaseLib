package com.kbit.kbbaselib.lifecircle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kbit.kbbaselib.context.ContextProvider;
import com.kbit.kbbaselib.util.ListUtil;

import java.util.LinkedList;

public class ActivityManager implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "ActivityManager";

    private boolean isForeground = false;

    private boolean isPaused = false;

    private Activity currentActivity;

    private LinkedList<Activity> activities;

    private Handler mHandler = new Handler();

    private Runnable mCallback;


    @SuppressLint("StaticFieldLeak")
    private static ActivityManager instance = new ActivityManager();

    private ActivityManager() {
        this.activities = new LinkedList<>();
    }

    public static void registerLifeCicleCallback() {
        ((Application)ContextProvider.getContext()).registerActivityLifecycleCallbacks(instance);
    }

    public static void resignLifeCircleCallback() {
        instance.currentActivity = null;
        ListUtil.removeAll(instance.activities, new LinkedList<>());
        instance.activities = null;
    }

    public Activity getCurrentActivity() {
        return instance.currentActivity;
    }

    public boolean isForeground() {
        return isForeground;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        instance.activities.add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Log.e(TAG, "currentActivity is " + activity.getLocalClassName());
        instance.currentActivity = activity;
        isForeground = true;
        isPaused = false;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        isPaused = true;
        if (mCallback != null) {
            mHandler.removeCallbacks(mCallback);
        }
        mHandler.postDelayed(mCallback = new Runnable() {
            @Override
            public void run() {
                if (isForeground && isPaused) {
                    isForeground = false;
                    Log.e(TAG, "did enter background");
                }
            }
        }, 500);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        instance.activities.remove(activity);
    }
}
