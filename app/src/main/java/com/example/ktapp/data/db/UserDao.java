package com.example.ktapp.data.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ktapp.data.User;

import java.util.List;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp.data.db
 * @ClassName: UserDao
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/5/9 17:41
 * @UpdateUser: LiuTao
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> queryAllUsers();

    @Query("SELECT * FROM user WHERE id=:id")
    User getUser(int id);

    @Query("SELECT * FROM user WHERE name=:name")
    User getUser(String name);

    @Insert
    void insert(User... users);

    @Insert
    void insert(User user);

    @Insert
    void insert(List<User> userLists);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);

    //删全部
    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user WHERE age=:age")
    List<User> getUsersByAge(int age);

    @Query("SELECT * FROM user WHERE age=:age LIMIT :max")
    List<User> getUsersByAge(int max, int... age);
}
