package com.example.ktapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.base
 * @ClassName: BaseDataBindFragment
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 14:17
 * @UpdateUser: LiuTao
 */
public abstract class BaseDataBindFragment<V extends ViewDataBinding> extends BaseFragment {
    protected V dataBind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dataBind = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return dataBind.getRoot();
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();


}
