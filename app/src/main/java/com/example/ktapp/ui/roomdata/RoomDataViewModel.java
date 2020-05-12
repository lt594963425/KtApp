package com.example.ktapp.ui.roomdata;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.ktapp.data.User;
import com.example.ktapp.data.db.UserDao;
import com.example.ktapp.data.db.UserDatabase;
import com.example.ktapp.ui.roomdata.paging.ConcertDataSource;
import com.example.ktapp.ui.roomdata.paging.ConcertFactory;

import java.util.List;

import javax.inject.Inject;

public class RoomDataViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<User> user;
    private MutableLiveData<List<User>> users;
    private UserDatabase userDatabase;
    //paging
    private LiveData<PagedList<User>> convertList;
    private DataSource<Integer, User> concertDataSource;

    public RoomDataViewModel(@NonNull Application application) {
        super(application);
        users = new MutableLiveData<List<User>>();
        user = new MutableLiveData<User>();
        userDatabase = UserDatabase.getInstance(application.getApplicationContext());

        ConcertFactory concertFactory = new ConcertFactory();
        concertDataSource = concertFactory.create();
        convertList = new LivePagedListBuilder<>(concertFactory, 20).build();
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
    public void invalidateDataSource() {
        concertDataSource.invalidate();
    }

    public LiveData<PagedList<User>> getConvertList() {
        return convertList;
    }
}
