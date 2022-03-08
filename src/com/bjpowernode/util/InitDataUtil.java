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
        // 初始化用户数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(1001, "刘一", Constant.USER_OK, BigDecimal.TEN,true));
        userList.add(new User(1002, "陈二", Constant.USER_OK, BigDecimal.TEN,false));
        userList.add(new User(1003, "张三", Constant.USER_OK, BigDecimal.TEN,false));
        initData(PathConstant.USER_PATH, userList);

        // 初始化图书数据
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "java入门", "刘一", Constant.TYPE_COMPUTER, "123-1", "季节出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(2, "java进阶", "陈二", Constant.TYPE_COMPUTER, "123-2", "机械工业出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(3, "文化苦旅", "张三", Constant.TYPE_LITERATURE, "456-1", "散文出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(4, "摩根财团", "李四", Constant.TYPE_ECONOMY, "789-1", "摩根出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(5, "卓有成效的管理者", "王五", Constant.TYPE_MANAGEMENT, "910-1", "管理出版社", Constant.STATUS_STORAGE));
        initData(PathConstant.BOOK_PATH, bookList);

        // 初始化借阅数据
        List<Lend> lendList = new ArrayList<>();
        User user = new User(1001, "刘一", Constant.USER_OK, BigDecimal.TEN,false);
        Book book = new Book(1, "java入门", "刘一", Constant.TYPE_COMPUTER, "123-1", "季节出版社", Constant.LEND_RETURN);
        Lend lend = new Lend();
        // UUID 生成唯一值，UUID 的生成与电脑 MAC 地址、始终序列、时间等因素有关
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
     * 初始化数据方法
     *
     * @param list
     */
    public static void initData(String path, List<?> list) {
        ObjectOutputStream oos = null;
        try {
            // 创建文件夹和文件
            File folder = new File(path.split("/")[0] + "/");
            File file = new File(path);

            if (!folder.exists() && !file.exists()) {
                folder.mkdir();
                file.createNewFile();
                // 对象输出流写入数据至文件
                oos = new ObjectOutputStream(new FileOutputStream(path));
                oos.writeObject(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
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
