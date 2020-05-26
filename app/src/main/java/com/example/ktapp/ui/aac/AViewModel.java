package com.example.ktapp.ui.aac;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.ktapp.base.BaseViewModel;
import com.example.ktapp.data.People;
import com.example.ktapp.data.Person;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.ui.aac
 * @ClassName: AViewModel
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 13:52
 * @UpdateUser: LiuTao
 */

public class AViewModel extends BaseViewModel {
    /**
     * 构造函数（在ViewModelProvider里通过class.newInstance创建实例）
     *
     * @param application
     */
    public AViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<People> getUserMutableLiveData() {
        return get(People.class);
    }

    public MutableLiveData<Person> getPersonMutableLiveData() {
        return get(Person.class);
    }

}