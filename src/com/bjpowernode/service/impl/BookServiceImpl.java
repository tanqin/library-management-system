package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.BookDao;
import com.bjpowernode.Dao.impl.BookDaoImpl;
import com.bjpowernode.bean.Book;
import com.bjpowernode.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();

    /**
     * ²éÑ¯
     *
     * @param book
     * @return
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }

    /**
     * Ìí¼Ó
     *
     * @param book
     */
    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     * É¾³ý
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }
}
