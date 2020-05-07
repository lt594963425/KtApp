package com.example.ktapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.ktapp.databinding.ActivityMainDataBinding;
import com.example.ktapp.databinding.ActivityMainDataBindingImpl;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: MainActivity2
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2020/4/26 15:07
 * @UpdateUser:
 */
public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainDataBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_data);
        User user = new User();
        user.setName("Hello");
        dataBinding.setUser(user);
        dataBinding.text2.setText("你好是");
        dataBinding.setListenerv1(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
