package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * �û� Dao ��
 */
public class UserDaoImpl implements UserDao {
    /**
     * ��Ӳ�̶�ȡ����
     *
     * @return
     */
    @Override
    public List<User> select() {
        // ������
        List<User> list = null;
        // �Զ��ر���
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))) {
            list = (List<User>) ois.readObject();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ������ϴ�������쳣���򷵻� List ����
        return new ArrayList<>();
    }

    /**
     * ���
     *
     * @param user
     */
    @Override
    public void add(User user) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // ��ȡ�ļ�
            ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH));
            // �������
            List<User> list = (List<User>) ois.readObject();
            if (list != null) {
                // �����û����
                User lastUser = list.get(list.size() - 1);
                user.setId(lastUser.getId() + 1);
                list.add(user);
            } else {
                list = new ArrayList<User>();
                user.setId(1001);
                list.add(user);
            }
            // д���ļ�
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
