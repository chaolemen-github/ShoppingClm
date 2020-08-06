package com.chaolemen.shoppingclm.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();//收集线上Bug
        initLeakCanary();//初始化内存泄露检测工具
        initUm();//初始化友盟
    }

    //初始化友盟
    private void initUm() {
        UMConfigure.init(this
                , "5f2c02d8d30932215475ad2a"
                , "UMCon"
                , UMConfigure.DEVICE_TYPE_PHONE
                , "");

        UMConfigure.setLogEnabled(true);
    }

    //初始化内存泄露检测工具
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for
            // heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    //收集线上Bug
    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), "14fdd7625c", true);
        // CrashReport.testJavaCrash(); //测试
    }
}
