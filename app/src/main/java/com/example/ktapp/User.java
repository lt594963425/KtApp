package com.example.ktapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: User
 * @Description: java类作用描述
 * @Author: liys
 * @CreateDate: 2020/4/26 15:21
 * @UpdateUser: 更新者
 */
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)//autoGenerate 为true时，自增；
    public int uid;
    @ColumnInfo(name = "name")
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
