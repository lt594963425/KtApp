package com.example.ktapp.base;

import android.app.Application;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.base
 * @ClassName: App
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/7/29 13:58
 * @UpdateUser: LiuTao
 */
public class App extends Application {

    private  static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
