package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户 Dao 层
 */
public class UserDaoImpl implements UserDao {
    /**
     * 从硬盘读取数据
     *
     * @return
     */
    @Override
    public List<User> select() {
        // 查数据
        List<User> list = null;
        // 自动关闭流
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))) {
            list = (List<User>) ois.readObject();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果以上代码出现异常，则返回 List 对象
        return new ArrayList<>();
    }
}
