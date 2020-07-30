package com.example.ktapp.data;

import android.os.Parcel;
import android.os.Parcelable;

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

public class People implements Parcelable {
    private int id;
    private String name;
    private String age;

    public People() {
    }

    public People(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.age);
    }

    protected People(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.age = in.readString();
    }

    public static final Parcelable.Creator<People> CREATOR = new Parcelable.Creator<People>() {
        @Override
        public People createFromParcel(Parcel source) {
            return new People(source);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };
}
