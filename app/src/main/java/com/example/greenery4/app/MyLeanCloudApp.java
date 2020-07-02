package com.example.greenery4.app;

import android.app.Application;
import cn.leancloud.core.AVOSCloud;


public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize( "8gVtHkgnzUdQl74WdytlyhbR-gzGzoHsz", "d9h01Ut05M9CqATQCdxxXJh0");
    }
}
