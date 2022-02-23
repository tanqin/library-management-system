package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;

import java.io.*;
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

    /**
     * 添加
     *
     * @param user
     */
    @Override
    public void add(User user) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // 读取文件
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            // 添加数据
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                // 设置用户编号
                User lastUser = list.get(list.size() - 1);
                user.setId(lastUser.getId() + 1);
                list.add(user);
            } else {
                list = new ArrayList<User>();
                user.setId(1001);
                list.add(user);
            }
            // 写入文件
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
