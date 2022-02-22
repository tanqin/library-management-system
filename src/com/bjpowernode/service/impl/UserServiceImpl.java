package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.Dao.impl.UserDaoImpl;
import com.bjpowernode.bean.User;
import com.bjpowernode.service.UserService;

import java.util.List;

/**
 * 用户 Service 层
 */
public class UserServiceImpl implements UserService {
    // 多态写法
    UserDao userDao = new UserDaoImpl();

    /**
     * 查询
     * @return
     */
    @Override
    public List<User> select() {
        // 调用 Dao 层方法
        return userDao.select();
    }
}
