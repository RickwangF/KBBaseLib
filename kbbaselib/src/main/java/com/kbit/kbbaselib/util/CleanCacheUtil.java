package com.kbit.kbbaselib.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.Locale;

public class CleanCacheUtil {

    private static final String TAG = "CleanCacheUtil";

    public static void cleanAllCache(Context context) {
        deleteFilesByDirectory(context.getExternalCacheDir());
        deleteFilesByDirectory(context.getCacheDir());
        //deleteFilesByDirectory(AppDataFile.getAppFile());
    }

    public static void deleteFilesByDirectory(File directory) {
        if (directory == null || !directory.exists()) {
            return;
        }
        if (!directory.isDirectory()) {
            directory.delete();
        }

        File files[] = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (file.listFiles().length > 0) {
                    deleteFilesByDirectory(file);
                } else {
                    file.delete();
                }
            } else {
                file.delete();
            }
        }
    }

    public static String getCacheTotalSize(Context context) {
        long size = getFolderSize(context.getCacheDir());
        size += getFolderSize(context.getExternalCacheDir());
        Log.i(TAG, "size " + size);
        return getFormatSize(size);
    }

    public static long getFolderSize(File directory) {
        if (directory == null) {
            return 0;
        }

        File files[] = directory.listFiles();
        if (files == null) {
            return 0;
        }
        long size = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                size += getFolderSize(file);
            } else {
                size += file.length();
            }
        }
        return size;
    }

    public static String getFormatSize(long size) {
        if(size==0){
            return null;
        }
        float kb = size / 1024F;
        if (kb < 1) {
            return String.format(Locale.getDefault(), "%1$dByte", size);
        }
        float mb = kb / 1024F;
        if (mb < 1) {
            return String.format(Locale.getDefault(), "%1$.2fKB", kb);
        }
        float gb = mb / 1024F;
        if (gb < 1) {
            return String.format(Locale.getDefault(), "%1$.2fMB", mb);
        }
        float tb = gb / 1024F;
        if (tb < 1) {
            return String.format(Locale.getDefault(), "%1$.2fGB", gb);
        }
        return String.format(Locale.getDefault(), "%1$.2fTB", tb);
    }

}
