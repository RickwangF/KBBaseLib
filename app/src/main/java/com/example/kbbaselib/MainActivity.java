package com.example.kbbaselib;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kbit.kbbaselib.lifecircle.BaseActivity;
import com.kbit.kbbaselib.permission.PermissionTool;
import com.kbit.kbbaselib.preference.BasePreference;
import com.kbit.kbbaselib.util.DateUtil;
import com.kbit.kbbaselib.util.DeviceUtil;
import com.kbit.kbbaselib.util.JsonUtil;
import com.kbit.kbbaselib.util.PackageUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> permissions = new ArrayList<>();
                permissions.add(Manifest.permission.CALL_PHONE);
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                permissions.add(Manifest.permission.SEND_SMS);
                PermissionTool.requestMultiplePermissions(permissions, MainActivity.this);
            }
        });
        int year = DateUtil.getNowYear();
        Log.e("date", "year is " + year);

        String androidId = DeviceUtil.getAndroidId();

        String IMEIID = DeviceUtil.getIMEI();

        Log.e("deviceID", "androidId is " + androidId + " IMEIID is " + IMEIID);

        long timestamp = 1588855251 + 4 * 24 * 3600 + 18 * 3600 + 48 * 60;
        Date date = DateUtil.getDateFromTimeStamp(timestamp, true);
        String dateString = DateUtil.getDateStringFromDate(date, "yyyy-MM-dd HH:mm:ss");
        Log.e("DateString", "date string is " + dateString);

        Log.e("DateString", "relative date string is " + DateUtil.getRelativeTimeStringFromNow(date));

        String packageName = PackageUtil.getPackageName(this);
        Log.e("Package", "packageName is " + packageName);

        List<String> classNames = PackageUtil.getClasses(this, packageName);
        for (String className: classNames) {
            Log.e("Package", "className is " + className);
        }
//        SharedPreferences sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
//        BasePreference basePreference = new BasePreference(sharedPreferences);
//        boolean strResult = basePreference.putString("key", "KEY");
//        if (strResult) {
//            String value = basePreference.getString("key");
//            Log.e("Test", "value is " + value);
//        }
//
//        List<SimpleModel> list = new ArrayList<>();
//        for (int i=0; i<10; i++) {
//            SimpleModel model = new SimpleModel();
//            model.setName("名字" + i);
//            model.setAvatar("头像" + i);
//            list.add(model);
//        }
//        boolean listResult = basePreference.putList("list", list);
//        if (listResult) {
//            List simpleList = basePreference.getList("list", SimpleModel.class);
//            Log.e("Test", "list is " + JsonUtil.list2Json(simpleList));
//        }
//
//        Map<String, String> map = new HashMap<>();
//        for (int i=0; i<10; i++) {
//            map.put("key" + i, String.valueOf(i));
//        }
//        boolean mapResult = basePreference.putMap("map", map);
//        if (mapResult) {
//            HashMap<String, String> hashMap = (HashMap<String, String>) basePreference.getMap("map", String.class, String.class);
//            Log.e("Test", "hashMap is " + JsonUtil.map2Json(hashMap));
//        }
//
//        String manufacturer = DeviceUtil.getDeviceManufacturer();
//        Log.e("Test", "manufacturer is " + manufacturer);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
