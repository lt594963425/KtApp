package com.example.ktapp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.base
 * @ClassName: BaseDataBindActivity
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 13:46
 * @UpdateUser: LiuTao
 */
public abstract class BaseDataBindActivity<V extends ViewDataBinding> extends BaseActivity {
    protected V dataBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBind = DataBindingUtil.setContentView(this, getLayoutId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBind != null) {
            dataBind.unbind();
            dataBind = null;
        }
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

}
