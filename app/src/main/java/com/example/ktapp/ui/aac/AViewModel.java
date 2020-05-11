package com.example.ktapp.ui.aac;

import androidx.lifecycle.MutableLiveData;

import com.example.ktapp.base.BaseViewModel;
import com.example.ktapp.data.People;
import com.example.ktapp.data.Person;
import com.example.ktapp.data.User;

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
    public MutableLiveData<People> getUserMutableLiveData() {return get(People.class);}
    public MutableLiveData<Person> getPersonMutableLiveData(){return get(Person.class);}
}