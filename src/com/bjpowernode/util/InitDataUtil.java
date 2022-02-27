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
        userList.add(new User(1001, "刘一", Constant.USER_OK, BigDecimal.TEN));
        initData(PathConstant.USER_PATH, userList);

        List<Book> bookList = new ArrayList<Book>();
        bookList.add(new Book(1, "java入门", "张三", Constant.TYPE_COMPUTER, "123-1", "XX出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(2, "java进阶", "李四", Constant.TYPE_COMPUTER, "123-1", "机械工业出版社", Constant.STATUS_STORAGE));
        initData(PathConstant.BOOK_PATH, bookList);
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
