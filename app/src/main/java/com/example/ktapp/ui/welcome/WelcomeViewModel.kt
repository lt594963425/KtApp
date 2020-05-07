package com.example.ktapp.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ktapp.User


class WelcomeViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    private val users: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<User> {
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

            users.postValue(User("哈哈哈哈哈哈哈哈哈"))
            //                  users.setValue(new User("哈哈哈哈哈哈哈哈哈"));
        }).start()
    }
}
