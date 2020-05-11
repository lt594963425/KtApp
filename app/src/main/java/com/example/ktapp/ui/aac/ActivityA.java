package com.example.ktapp.ui.aac;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.ktapp.R;
import com.example.ktapp.base.BaseActivity;
import com.example.ktapp.base.BaseDataBindActivity;
import com.example.ktapp.data.People;
import com.example.ktapp.data.User;
import com.example.ktapp.databinding.ActivityMainDataBinding;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.aac
 * @ClassName: ActivityA
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 13:51
 * @UpdateUser: LiuTao
 */
public class ActivityA extends BaseDataBindActivity<ActivityMainDataBinding> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1.添加数据更改监听器
        getViewModel(AViewModel.class).getUserMutableLiveData().observe(this, new Observer<People>() {
            @Override
            public void onChanged(@Nullable People user) {
                Log.e("linhaojian", "ActivityA中接收user：" + user.toString());
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_data;
    }

    // 2.更改数据
    public void getUser() {
        People user = new People();
        user.setName("lin");
        user.setName("30");
        //同步更改setValue  ;  异步更改postValue
        getViewModel(AViewModel.class).getUserMutableLiveData().setValue(user);
    }
}
