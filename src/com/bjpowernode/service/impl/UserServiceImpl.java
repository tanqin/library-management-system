package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.Dao.impl.UserDaoImpl;
import com.bjpowernode.bean.User;
import com.bjpowernode.service.UserService;

import java.util.List;

/**
 * �û� Service ��
 */
public class UserServiceImpl implements UserService {
    // ��̬д��
    UserDao userDao = new UserDaoImpl();

    /**
     * ��ѯ
     * @return
     */
    @Override
    public List<User> select() {
        // ���� Dao �㷽��
        return userDao.select();
    }
}
