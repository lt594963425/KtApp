package com.example.ktapp.ui.start;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;

import com.example.ktapp.R;
import com.example.ktapp.base.LazyLoadFragment;
import com.example.ktapp.data.People;
import com.example.ktapp.databinding.StartFragmentBinding;

/**
 * Mvvm  ViewModel  +LiveData +dataBinding +Lifecycle
 */
public class StartFragment extends LazyLoadFragment<StartFragmentBinding> {


    private String start;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public StartViewModel getViewModel() {
        return getViewModel(StartViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.start_fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycle().addObserver(getViewModel());
        if (getArguments() != null) {
            start = getArguments().getString("START");
            dataBind.changeMsg.setText(start + "");
        }

        getViewModel().getUsers().observe(getViewLifecycleOwner(), new Observer<People>() {
            @Override
            public void onChanged(People user) {
                dataBind.message.setText(user.getName());
            }
        });
        dataBind.changeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mViewModel.updateData("改变了");
                getViewModel().getUsers().setValue(new People("hahahahha", "20"));
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        getViewModel().loadUsers();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("StartFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("StartFragment", "onPause");
    }

    //    @Override
//    private void onResume() {
//        super.onResume();
//        Log.d("StartFragment", "onResume");
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("StartFragment", "onPause");
//    }
}
