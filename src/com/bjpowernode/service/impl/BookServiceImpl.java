package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.BookDao;
import com.bjpowernode.Dao.impl.BookDaoImpl;
import com.bjpowernode.bean.Book;
import com.bjpowernode.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();

    /**
     * ��ѯ
     *
     * @param book
     * @return
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }

    /**
     * ���
     *
     * @param book
     */
    @Override
    public void add(Book book) {
        bookDao.add(book);
    }

    /**
     * ɾ��
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }
}
