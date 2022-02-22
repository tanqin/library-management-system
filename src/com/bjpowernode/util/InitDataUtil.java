package com.bjpowernode.util;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.bean.User;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 初始化用户数据
 */
public class InitDataUtil {
    public static void main(String[] args) {
//        initUser(null);
    }

    public static void initUser(List<User> userList) {
        ObjectOutputStream oos = null;
        try {
            // 创建文件夹和文件
            File folder = new File(PathConstant.FOLDER_PATH);
            File file = new File(PathConstant.USER_PATH);

            if (!folder.exists() && !file.exists()) {
                folder.mkdir();
                file.createNewFile();
                List<User> list = new ArrayList<User>();
                if (userList == null) {
                    // 创建初始数据
                    list.add(new User(1001, "刘一", Constant.USER_OK, BigDecimal.TEN));
                } else {
                    list = userList;
                }
                // 对象输出流写入数据至文件
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.USER_PATH));
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
