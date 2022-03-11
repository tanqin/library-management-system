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
 * 用户 Service 层
 */
public class UserServiceImpl implements UserService {
    // 多态写法
    UserDao userDao = new UserDaoImpl();
    LendDao lendDao = new LendDaoImpl();

    /**
     * 查询
     *
     * @return
     */
    @Override
    public List<User> select() {
        // 调用 Dao 层方法
        return userDao.select();
    }

    /**
     * 条件查询
     *
     * @param user
     * @return
     */
    @Override
    public List<User> select(User user) {
        return userDao.select(user);
    }

    /**
     * 添加
     *
     * @param user
     */
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    @Override
    public void update(User user) {
        userDao.update(user);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    /**
     * 冻结
     *
     * @param id
     */
    @Override
    public void frozen(int id) {
        userDao.frozen(id);
    }

    /**
     * 借阅用户查询
     *
     * @return
     */
    @Override
    public List<User> selectUserToLend() {
        return userDao.selectUserToLend();
    }

    /**
     * 用户充值
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

        // 更新用户数据和借阅数据
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
