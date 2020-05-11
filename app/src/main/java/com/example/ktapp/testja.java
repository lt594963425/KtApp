package com.example.ktapp;

import com.example.ktapp.data.Person;
import com.example.ktapp.data.Person2;

/**
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: testja
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/4/24 15:17
 */
public class testja {


    public static void main(String[] args) {
        //抽象匿名内部类
        new Person() {
            @Override
            public void eat() {
                System.out.print("----//抽象匿名内部类-------\n");
            }
        }.eat();

        //接口匿名内部类
        Person2 person2 = new Person2() {
            @Override
            public void eat() {
                System.out.print("----//接口匿名内部类-------");
            }
        };
        person2.eat();
    }
}
