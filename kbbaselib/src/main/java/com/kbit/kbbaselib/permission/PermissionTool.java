package com.kbit.kbbaselib.permission;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kbit.kbbaselib.BuildConfig;
import com.kbit.kbbaselib.lifecircle.BaseApplication;
import com.kbit.kbbaselib.util.DeviceUtil;
import com.kbit.kbbaselib.util.PackageUtil;
import com.kbit.kbbaselib.util.ToastUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PermissionTool {

    private static final int SINGLE_PERMISSION = 1;

    private static final int MULTIPLE_PERMISSION = 2;

    private static String packageName = PackageUtil.getPackageName(BaseApplication.getContext());

    private static String getPermissionChineseDesc(String permission) {
        String desc = "";
        switch (permission) {
            case Manifest.permission.READ_CALENDAR:
                desc = "读取日历";
                break;
            case Manifest.permission.WRITE_CALENDAR:
                desc = "写入日历";
                break;
            case Manifest.permission.CAMERA:
                desc = "拍照";
                break;
            case Manifest.permission.READ_CONTACTS:
                desc = "读取通讯录";
                break;
            case Manifest.permission.WRITE_CONTACTS:
                desc = "写入通讯录";
                break;
            case Manifest.permission.GET_ACCOUNTS:
                desc = "获取手机账户";
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
                desc = "获取精确定位";
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                desc = "获取模糊定位";
                break;
            case Manifest.permission.RECORD_AUDIO:
                desc = "录制语音";
                break;
            case Manifest.permission.READ_PHONE_STATE:
                desc = "读取电话状态";
                break;
            case Manifest.permission.CALL_PHONE:
                desc = "打电话";
                break;
            case Manifest.permission.READ_CALL_LOG:
                desc = "读取通话记录";
                break;
            case Manifest.permission.WRITE_CALL_LOG:
                desc = "写入通话记录";
                break;
            case Manifest.permission.ADD_VOICEMAIL:
                desc = "添加语音留言";
                break;
            case Manifest.permission.USE_SIP:
                desc = "使用SIP通话";
                break;
            case Manifest.permission.BODY_SENSORS:
                desc = "运动传感器";
                break;
            case Manifest.permission.SEND_SMS:
                desc = "发送短信";
                break;
            case Manifest.permission.RECEIVE_SMS:
                desc = "接受短信";
                break;
            case Manifest.permission.RECEIVE_WAP_PUSH:
                desc = "接受Wap推送";
                break;
            case Manifest.permission.RECEIVE_MMS:
                desc = "接受彩信";
                break;
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                desc = "读取SD卡";
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                desc = "写入SD卡";
                break;
            case Manifest.permission.FOREGROUND_SERVICE:
                desc = "前台服务";
                break;
        }
        return desc;
    }

    private static void gotoPermissionActivty(Activity activity) {
        String manufacturer = DeviceUtil.getDeviceManufacturer();
        switch (manufacturer) {
            case "huawei":
                goHuaWeiMainager(activity);
                break;
            case "xiaomi":
                goXiaoMiMainager(activity);
                break;
            case "meizu":
                goMeizuMainager(activity);
                break;
            case "oppo":
                goOppoManager(activity);
                break;
            case "vivo":
                goVivoManager(activity);
                break;
            case "samsung":
                goSamsungMainager(activity);
                break;
            case "sony":
                goSonyMainager(activity);
                break;
        }
    }

    private static void openPermissionDialog(final Activity activity, List<String> permissions) {
        StringBuilder desc = new StringBuilder();
        for (String permission: permissions) {
            desc.append(getPermissionChineseDesc(permission)).append(",");
        }
        desc.delete(desc.length()-1, desc.length());
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle(desc.toString() + "权限申请")
                .setMessage(desc.toString() + "的权限需要您的同意，否则应用的功能无法正常使用")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoPermissionActivty(activity);
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static void requestSinglePermission(String permission, Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    ActivityCompat.requestPermissions(activity, new String[] {permission}, SINGLE_PERMISSION);
                } else {
                    List<String> permissions = new ArrayList<>();
                    permissions.add(permission);
                    openPermissionDialog(activity, permissions);
                }
            } else {
                ActivityCompat.requestPermissions(activity, new String[] {permission}, SINGLE_PERMISSION);
            }
        }
    }

    public static void requestMultiplePermissions(List<String> permissions, Activity activity) {
        List<String> disabledPermissions = new ArrayList<>();
        List<String> rationales = new ArrayList<>();
        for (String permission: permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (activity.shouldShowRequestPermissionRationale(permission)) {
                    rationales.add(permission);
                    continue;
                }
            }
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                disabledPermissions.add(permission);
            }
        }

        if (rationales.size() > 0) {
            openPermissionDialog(activity, rationales);
        }

        if (disabledPermissions.size() > 0) {
            ActivityCompat.requestPermissions(activity, disabledPermissions.toArray(new String[disabledPermissions.size()]), MULTIPLE_PERMISSION);
        }
    }

    private static void goLGMainager(Activity activity){
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showLongToast(activity, "跳转失败");
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }
    private static void goSonyMainager(Activity activity){
        try {
            Intent intent = new Intent(packageName);
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showLongToast(activity, "跳转失败");
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    private static void goHuaWeiMainager(Activity activity) {
        try {
            Intent intent = new Intent(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            activity.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showLongToast(activity, "跳转失败");
            e.printStackTrace();
            goIntentSetting(activity);
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private static void goXiaoMiMainager(Activity activity) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", packageName);
        activity.startActivity(intent);
    }

    private static void goMeizuMainager(Activity activity) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            activity.startActivity(intent);
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
            goIntentSetting(activity);
        }
    }

    private static void goSamsungMainager(Activity activity) {
        //三星4.3可以直接跳转
        goIntentSetting(activity);
    }

    private static void goOppoManager(Activity activity) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.LIBRARY_PACKAGE_NAME);
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivity(intent);
    }

    private static void goVivoManager(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        intent.setAction("secure.intent.action.softPermissionDetail");
        intent.putExtra("packagename", activity.getPackageName());
        activity.startActivity(intent);
    }

    private static void goIntentSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
