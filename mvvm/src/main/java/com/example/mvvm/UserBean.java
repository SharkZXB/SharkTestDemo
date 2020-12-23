package com.example.mvvm;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * author : 三丰
 * date   : 2020/11/28
 * desc   :
 */
public class UserBean extends BaseObservable {

    private String name; //姓名
    private int age; //年龄

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
