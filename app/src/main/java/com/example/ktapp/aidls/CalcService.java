package com.example.ktapp.aidls;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.ktapp.IMyAidlInterface;
import com.example.ktapp.data.db.User;
import com.example.ktapp.data.db.UserDatabase;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.aidl
 * @ClassName: CalcService
 * @Description: Service的作用其实就是为我们创建Binder驱动，即服务端与客户端连接的桥梁。  AIDL用到的不能进行混淆
 * @Author: LiuTao
 * @CreateDate: 2020/9/17 8:38
 * @UpdateUser: LiuTao
 *
 * AIDL其实通过我们写的aidl文件，帮助我们生成了一个接口，一个Stub类用于服务端，一个Proxy类用于客户端调用。
 * 那么我们是否可以不通过写AIDL来实现远程的通信呢？下面向大家展示如何完全不依赖AIDL来实现客户端与服务端的通信。
 */
public class CalcService extends Service {
    private static final String TAG = "server";
    private User user;

    public void onCreate() {
        Log.e(TAG, "onCreate");
    }

    public IBinder onBind(Intent t) {
        Log.e(TAG, "onBind");
        return mBinder;
    }

    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public void onRebind(Intent intent) {
        Log.e(TAG, "onRebind");
        super.onRebind(intent);
    }

    private IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {


        @Override
        public User getUser(String name) throws RemoteException {
            user = new User();

            //查数据库就报错呢
            user = UserDatabase.getInstance(getApplicationContext()).getUserDao().getUser(name);
            Log.e("服务", "" + Thread.currentThread() + "");
            Log.e("服务", "" + user.getAge() + "");
            if (user != null) {
                return user;
            } else {
                user.setAge("23");
                user.setName("张三");
                return user;
            }
        }
    };

}
