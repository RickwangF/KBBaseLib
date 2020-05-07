package com.example.kbbaselib;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.example.kbbaselib.databinding.ActivitySecondBinding;
import com.kbit.kbbaselib.lifecircle.BaseActivity;
import com.kbit.kbbaselib.util.DisplayUtil;

public class SecondActivity extends BaseActivity {

    ActivitySecondBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_second);
        setSupportActionBar(mBind.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);

        int widthDp = DisplayUtil.getScreenWidthDp();
        int heightDp = DisplayUtil.getScreenHeightDp();
        int widthPx = DisplayUtil.getScreenWidthPx();
        int heightPx = DisplayUtil.getScreenHeightPx();
        float densityNum = DisplayUtil.getDensity();
        float densityDPI = DisplayUtil.getDensityDpi();

        Log.d("h_bl", "屏幕宽度（像素）：" + widthPx);
        Log.d("h_bl", "屏幕高度（像素）：" + heightPx);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + densityNum);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDPI);
        Log.d("h_bl", "屏幕宽度（dp）：" + widthDp);
        Log.d("h_bl", "屏幕高度（dp）：" + heightDp);
    }
}
