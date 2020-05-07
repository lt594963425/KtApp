package com.example.ktapp.ui.start;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ktapp.User;
import com.example.ktapp.ui.base.ILifecycle;

public class StartViewModel extends ViewModel implements ILifecycle {
    // TODO: Implement the ViewModel
    private static final String TAG = "StartViewModel";
    private MutableLiveData<User> users;
    private final StartRepertory startRepertory;

    public StartViewModel() {
        users = new MutableLiveData<User>();
        startRepertory = new StartRepertory();
    }

    public MutableLiveData<User> getUsers() {

        return users;
    }

    /**
     * 加载数据
     */
    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                users.postValue(new User("哈哈哈哈哈哈哈哈哈"));
//                  users.setValue(new User("哈哈哈哈哈哈哈哈哈"));
            }
        }).start();
    }


    @Override
    public void onCreate(@Nullable LifecycleOwner owner) {
        Log.e(TAG, "onCreate()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
        loadUsers();
    }

    @Override
    public void onDestroy(@Nullable LifecycleOwner owner) {
        Log.e(TAG, "onDestroy()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStart(@Nullable LifecycleOwner owner) {
        Log.e(TAG, "onStart()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onResume(@Nullable LifecycleOwner owner) {
        Log.e(TAG, "onResume()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onPause(@Nullable LifecycleOwner owner) {
        Log.e(TAG, "onPause()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onStop(@Nullable LifecycleOwner owner) {
        Log.e(TAG, "onStop()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }

    @Override
    public void onLifecycleChanged(@Nullable LifecycleOwner owner, @Nullable Lifecycle.Event event) {
        Log.e(TAG, "onLifecycleChanged()");
        Log.e(TAG, "当前生命周期状态=" + owner.getLifecycle().getCurrentState().name());
    }
}
