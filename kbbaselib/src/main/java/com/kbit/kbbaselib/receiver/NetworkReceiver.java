package com.kbit.kbbaselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectionManager != null;
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    int networkType = networkInfo.getSubtype();
                    judgeMobileNetworkType(networkType, context);
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    Toast.makeText(context, "正在使用wifi", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        } else {
            Toast.makeText(context, "当前无网络连接", Toast.LENGTH_SHORT).show();
        }
    }

    public void judgeMobileNetworkType(int networkType, Context context) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                Toast.makeText(context, "正在使用2G网络", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                Toast.makeText(context, "正在使用3G网络", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                Toast.makeText(context, "正在使用4G网络", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
