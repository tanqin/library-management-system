package com.bjpowernode.Dao;

import com.bjpowernode.bean.User;

import java.util.List;

public interface UserDao {
    List<User> select();

    void add(User user);
}
