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
     * �û�������ѯ
     *
     * @param user
     * @return
     */
    @Override
    public List<User> select(User user) {
        // ������
        List<User> list = null;
        // �Զ��ر���
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.USER_PATH))) {
            list = (List<User>) ois.readObject();
            return list.stream().filter(u -> u.getId() == user.getId()).collect(Collectors.toList());
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
     * �޸�
     *
     * @param user
     */
    @Override
    public void update(User user) {
        // ��ȡ�ļ��е� List ���ݣ��ҵ�ԭʼ���ݣ������޸Ĳ�������д���ļ���
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
     * ɾ��
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
            // ���쳣�׸�����������
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
     * ����
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
     * ��ѯ�ɽ����û�
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
