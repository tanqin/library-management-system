package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * 用户条件查询
     *
     * @param user
     * @return
     */
    @Override
    public List<User> select(User user) {
        // 查数据
        List<User> list = null;
        // 自动关闭流
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))) {
            list = (List<User>) ois.readObject();
            return list.stream().filter(u -> u.getId() == user.getId()).collect(Collectors.toList());
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
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

    /**
     * 修改
     *
     * @param user
     */
    @Override
    public void update(User user) {
        // 读取文件中的 List 数据，找到原始数据，进行修改操作，并写入文件中
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();

            User originUser = list.stream().filter(p -> p.getId() == user.getId()).findFirst().get();

            BeanUtil.populate(originUser, user);

            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
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

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();

            User user = list.stream().filter(p -> p.getId() == id).findFirst().get();
            list.remove(user);

            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            // 将异常抛给控制器处理
            throw new RuntimeException();
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

    /**
     * 冻结
     *
     * @param id
     */
    @Override
    public void frozen(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            List<User> list = (List<User>) ois.readObject();

            User user = list.stream().filter(p -> p.getId() == id).findFirst().get();

            user.setStatus(Constant.USER_FROZEN);

            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
            oos.writeObject(list);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
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

    /**
     * 查询可借阅用户
     *
     * @return
     */
    @Override
    public List<User> selectUserToLend() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))) {
            List<User> userList = (List<User>) ois.readObject();
            if (userList != null) {
                List<User> collect = userList.stream().filter(u -> false == u.getLend() && Constant.USER_OK.equals(u.getStatus())).collect(Collectors.toList());
                return collect;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
