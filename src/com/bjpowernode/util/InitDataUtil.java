package com.bjpowernode.util;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class InitDataUtil {
    public static void main(String[] args) {
        List<User> userList = new ArrayList<User>();
        userList.add(new User(1001, "��һ", Constant.USER_OK, BigDecimal.TEN));
        initData(PathConstant.USER_PATH, userList);

        List<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book(1, "java����", "����", Constant.TYPE_COMPUTER, "123-1", "XX������", Constant.STATUS_STORAGE));
        bookList.add(new Book(2, "java����", "����", Constant.TYPE_COMPUTER, "123-1", "��е��ҵ������", Constant.STATUS_STORAGE));
        initData(PathConstant.BOOK_PATH, bookList);
    }

    /**
     * ��ʼ�����ݷ���
     *
     * @param list
     */
    public static void initData(String path, List<?> list) {
        ObjectOutputStream oos = null;
        try {
            // �����ļ��к��ļ�
            File folder = new File(path.split("/")[0] + "/");
            File file = new File(path);

            if (!folder.exists() && !file.exists()) {
                folder.mkdir();
                file.createNewFile();
                // ���������д���������ļ�
                oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // �ر���
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
