package com.example.ktapp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.math.BigInteger;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: BaseActivity
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 13:42
 * @UpdateUser: LiuTao
 */
public abstract  class BaseActivity extends AppCompatActivity {
    private ViewModelProvider viewModelProvider;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        viewModelProvider = getViewModelProvider();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModelProvider = null;
    }
    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    public <T extends ViewModel> T getViewModel(Class<T> clazz){
        return viewModelProvider.get(clazz);
    }
    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private ViewModelProvider getViewModelProvider(){
        return new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()));
    }
    /**
     * 获取布局ID
     * @return
     */
    protected abstract int getLayoutId();


}
