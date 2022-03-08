package com.bjpowernode.util;

import com.bjpowernode.bean.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


public class InitDataUtil {
    public static void main(String[] args) {
        // ��ʼ���û�����
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001, "��һ", Constant.USER_OK, BigDecimal.TEN,true));
        userList.add(new User(1002, "�¶�", Constant.USER_OK, BigDecimal.TEN,false));
        userList.add(new User(1003, "����", Constant.USER_OK, BigDecimal.TEN,false));
        initData(PathConstant.USER_PATH, userList);

        // ��ʼ��ͼ������
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "java����", "��һ", Constant.TYPE_COMPUTER, "123-1", "���ڳ�����", Constant.STATUS_STORAGE));
        bookList.add(new Book(2, "java����", "�¶�", Constant.TYPE_COMPUTER, "123-2", "��е��ҵ������", Constant.STATUS_STORAGE));
        bookList.add(new Book(3, "�Ļ�����", "����", Constant.TYPE_LITERATURE, "456-1", "ɢ�ĳ�����", Constant.STATUS_STORAGE));
        bookList.add(new Book(4, "Ħ������", "����", Constant.TYPE_ECONOMY, "789-1", "Ħ��������", Constant.STATUS_STORAGE));
        bookList.add(new Book(5, "׿�г�Ч�Ĺ�����", "����", Constant.TYPE_MANAGEMENT, "910-1", "���������", Constant.STATUS_STORAGE));
        initData(PathConstant.BOOK_PATH, bookList);

        // ��ʼ����������
        List<Lend> lendList = new ArrayList<>();
        User user = new User(1001, "��һ", Constant.USER_OK, BigDecimal.TEN,false);
        Book book = new Book(1, "java����", "��һ", Constant.TYPE_COMPUTER, "123-1", "���ڳ�����", Constant.LEND_RETURN);
        Lend lend = new Lend();
        // UUID ����Ψһֵ��UUID ����������� MAC ��ַ��ʼ�����С�ʱ��������й�
        String uuid = UUID.randomUUID().toString();
        lend.setId(uuid);
        lend.setUser(user);
        lend.setBook(book);
        lend.setStatus(Constant.LEND_LEND);
        LocalDate begin = LocalDate.now();
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));
        lendList.add(lend);
        initData(PathConstant.LEND_PATH, lendList);
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
