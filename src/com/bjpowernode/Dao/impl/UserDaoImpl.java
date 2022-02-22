package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.UserDao;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
}
