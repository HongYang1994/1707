package com.bawei.month_ds_demo.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Android Studio.
 * User: HONGYANG
 * Date: 2019/12/30
 * Time: 8:45
 */

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
