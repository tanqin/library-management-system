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
     *
     * @return
     */
    @Override
    public List<User> select() {
        // ���� Dao �㷽��
        return userDao.select();
    }

    /**
     * ���
     *
     * @param user
     */
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    /**
     * �޸�
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * ɾ��
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    /**
     * ����
     *
     * @param id
     */
    @Override
    public void frozen(int id) {
        userDao.frozen(id);
    }

    /**
     * �����û���ѯ
     * @return
     */
    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }
}
