package com.example.ktapp.ui.base;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.base
 * @ClassName: ILifecycle
 * @Description: 感知生命周期
 * @Author: LiuTao
 * @CreateDate: 2020/4/29 11:36
 * @UpdateUser: LiuTao
 */
public interface ILifecycle extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(@Nullable LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(@Nullable LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(@Nullable LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(@Nullable LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause(@Nullable LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop(@Nullable LifecycleOwner owner);

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onLifecycleChanged(@Nullable LifecycleOwner owner, @Nullable Lifecycle.Event event);

}
