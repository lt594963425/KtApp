package com.example.ktapp.ui.login;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ktapp.R;
import com.example.ktapp.base.BaseDataBindFragment;
import com.example.ktapp.base.LazyLoadFragment;
import com.example.ktapp.databinding.LoginFragmentBinding;

public class LoginFragment extends LazyLoadFragment<LoginFragmentBinding> {


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.login_fragment;
    }


    public LoginViewModel get() {
        return getViewModel(LoginViewModel.class);
    }


    @Override
    protected void initData() {
        super.initData();
        dataBind.startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("START", "你好开始页面");
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_login_to_start, bundle);
            }
        });

        dataBind.welcomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_login_to_welcome);
            }
        });
    }
}
