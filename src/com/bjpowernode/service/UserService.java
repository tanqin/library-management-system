package com.bjpowernode.service;
import com.bjpowernode.bean.User;

import java.util.List;

public interface UserService {
    List<User> select();
    void add(User user);
}
