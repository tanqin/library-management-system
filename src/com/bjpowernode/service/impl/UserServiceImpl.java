package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.LendDao;
import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.Dao.impl.LendDaoImpl;
import com.bjpowernode.Dao.impl.UserDaoImpl;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.service.UserService;

import java.math.BigDecimal;
import java.util.List;

/**
 * �û� Service ��
 */
public class UserServiceImpl implements UserService {
    // ��̬д��
    UserDao userDao = new UserDaoImpl();
    LendDao lendDao = new LendDaoImpl();

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
     * ������ѯ
     *
     * @param user
     * @return
     */
    @Override
    public List<User> select(User user) {
        return userDao.select(user);
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
     *
     * @return
     */
    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }

    /**
     * �û���ֵ
     *
     * @param user
     * @param money
     * @return
     */
    @Override
    public User charge(User user, BigDecimal money) {
        BigDecimal currentMoney = money.add(user.getMoney());
        user.setMoney(currentMoney);

        if (currentMoney.compareTo(BigDecimal.ZERO) > 0) {
            user.setStatus(Constant.USER_OK);
        }

        // �����û����ݺͽ�������
        userDao.update(user);
        List<Lend> lendList = lendDao.select(null);
        for (int i = 0; i < lendList.size(); i++) {
            Lend lendItem = lendList.get(i);
            if (lendItem.getUser().getId() == user.getId()) {
                lendItem.setUser(user);
                lendDao.update(lendItem);
            }
        }

        return user;
    }
}
