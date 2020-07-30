package com.example.ktapp.ui.roomdata;

import android.app.Application;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.AndroidViewModel;
        import androidx.lifecycle.LiveData;
        import androidx.lifecycle.MutableLiveData;

        import com.example.ktapp.data.db.User;
        import com.example.ktapp.data.db.UserDatabase;

        import java.util.List;

public class RoomDataViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<User> user;
    private MutableLiveData<List<User>> users;
    private UserDatabase userDatabase;
    //paging

    public RoomDataViewModel(@NonNull Application application) {
        super(application);
        users = new MutableLiveData<List<User>>();
        user = new MutableLiveData<User>();
        userDatabase = UserDatabase.getInstance(application.getApplicationContext());

    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<List<User>> getUsers() {
        queryAllUsers();
        return users;
    }

    public void queryAllUsers() {
        users.postValue(userDatabase.getUserDao().queryAllUsers());
    }


    /************************************************* * Paging * *************************************************/

}
