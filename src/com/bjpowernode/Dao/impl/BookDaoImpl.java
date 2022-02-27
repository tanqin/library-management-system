package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.BookDao;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.PathConstant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {
    /**
     * 查询
     *
     * @param book
     * @return
     */
    @Override
    public List<Book> select(Book book) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> bookList = (List<Book>) ois.readObject();

            if (bookList != null) {
                if (book == null || ("".equals(book.getBookName()) && "".equals(book.getIsbn()))) {
                    // 查询全部数据
                    return bookList;
                } else {
                    // 条件查询
                    List<Book> conditionList = new ArrayList<Book>();

                    if (!"".equals(book.getBookName()) && !"".equals(book.getIsbn())) {
                        // 查询两个条件
                        conditionList = bookList.stream().filter(b -> b.getBookName().equals(book.getBookName())).collect(Collectors.toList());
                        conditionList = conditionList.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).collect(Collectors.toList());

                    } else {
                        // 查询单个条件
                        if (!"".equals(book.getBookName())) {
                            conditionList = bookList.stream().filter(b -> b.getBookName().equals(book.getBookName())).collect(Collectors.toList());
                        }
                        if (!"".equals(book.getIsbn())) {
                            conditionList = bookList.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).collect(Collectors.toList());
                        }
                    }
                    return conditionList;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
