package com.example.ktapp.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ktapp.data.People


class WelcomeViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val users: MutableLiveData<People> by lazy {
        MutableLiveData<People>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<People> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
        Thread(Runnable {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            users.postValue(People("哈哈哈", "40"))
            //                  users.setValue(new User("哈哈哈哈哈哈哈哈哈"));
        }).start()
    }
}
