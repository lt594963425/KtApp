package com.example.ktapp.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.example.ktapp.databinding.StartFragmentBinding;

/**
 * Created by ${LiuTao}.
 * functiona:懒加载
 * Date: 2019/8/27 0027
 * Time: 上午 11:27
 */
public abstract class LazyLoadFragment<V extends ViewDataBinding> extends BaseDataBindFragment<V> {
    private Context mContext;
    private boolean isFirstLoad = true; // 是否第一次加载

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中
            initData();
            initEvent();
            isFirstLoad = false;
        }
    }


    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

    }

    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

    }


}
