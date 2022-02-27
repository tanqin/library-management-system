package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.BookDao;
import com.bjpowernode.Dao.impl.BookDaoImpl;
import com.bjpowernode.bean.Book;
import com.bjpowernode.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();

    /**
     * ≤È—Ø
     *
     * @param book
     * @return
     */
    @Override
    public List<Book> select(Book book) {
        return bookDao.select(book);
    }
}
