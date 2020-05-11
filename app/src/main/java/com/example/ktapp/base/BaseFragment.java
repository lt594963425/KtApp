package com.example.ktapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.base
 * @ClassName: BaseFragment
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 13:47
 * @UpdateUser: LiuTao
 */
public  class BaseFragment extends Fragment {
    private ViewModelProvider viewModelProvider;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelProvider = getViewModelProvider();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModelProvider = null;
    }
    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    public   <T extends ViewModel> T getViewModel(Class<T> clazz){
        return viewModelProvider.get(clazz);
    }

    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private ViewModelProvider getViewModelProvider(){
        return new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()));
    }

}
