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
        initUser(null);
        initBook(null);
    }

    /**
     * ��ʼ���û����ݷ���
     *
     * @param userList
     */
    public static void initUser(List<User> userList) {
        ObjectOutputStream oos = null;
        try {
            // �����ļ��к��ļ�
            File folder = new File(PathConstant.FOLDER_PATH);
            File file = new File(PathConstant.USER_PATH);

            if (!folder.exists() && !file.exists()) {
                folder.mkdir();
                file.createNewFile();
                List<User> list = new ArrayList<User>();
                if (userList == null) {
                    // ������ʼ����
                    list.add(new User(1001, "��һ", Constant.USER_OK, BigDecimal.TEN));
                } else {
                    list = userList;
                }
                // ���������д���������ļ�
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
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

    /**
     * ��ʼ��ͼ�����ݷ���
     *
     * @param bookList
     */
    public static void initBook(List<Book> bookList) {
        ObjectOutputStream oos = null;
        try {
            File folder = new File("book/");
            File file = new File(PathConstant.BOOK_PATH);

            if (!folder.exists() && !file.exists()) {
                folder.mkdir();
                file.createNewFile();

                List<Book> list = new ArrayList<Book>();

                if (bookList == null) {
                    list.add(new Book(1, "java����", "����", Constant.TYPE_COMPUTER, "12-987", "XX������", Constant.STATUS_STORAGE));
                } else {
                    list = bookList;
                }

                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
