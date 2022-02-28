package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.ChartDao;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.PathConstant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartDaoImpl implements ChartDao {
    @Override
    public Map<String, Integer> bookTypeCount() {
        // 获取数据，使用自动关闭
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH))) {
            List<Book> bookList = (List<Book>) ois.readObject();

            // 数据分组
            Map<String, List<Book>> collect = bookList.stream().collect(Collectors.groupingBy(Book::getType));
            System.out.println(collect);
            // 获取迭代器
            Iterator<Map.Entry<String, List<Book>>> iterator = collect.entrySet().iterator();
            // 创建 Map 集合
            Map<String, Integer> mapList = new HashMap<>();
            // 遍历
            while (iterator.hasNext()) {
                Map.Entry<String, List<Book>> next = iterator.next();
                mapList.put(next.getKey(), next.getValue() == null ? 0 : next.getValue().size());
            }
            return mapList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
