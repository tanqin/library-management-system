package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.LendDao;
import com.bjpowernode.Dao.impl.LendDaoImpl;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.service.BookService;
import com.bjpowernode.service.LendService;
import com.bjpowernode.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LendServiceImpl implements LendService {
    private LendDao lendDao = new LendDaoImpl();
    private BookService bookService = new BookServiceImpl();
    private UserService userService = new UserServiceImpl();

    /**
     * 借阅查询
     *
     * @param lend
     * @return
     */
    @Override
    public List<Lend> select(Lend lend) {
        return lendDao.select(lend);
    }

    @Override
    public void add(int bookId, int userId) {
        Book paramBook = new Book();
        paramBook.setId(bookId);
        List<Book> bookList = bookService.select(paramBook);

        User paramUser = new User();
        paramUser.setId(userId);
        List<User> userList = userService.select(paramUser);

        Lend lend = new Lend();
        lend.setId(UUID.randomUUID().toString());

        // 给图书赋值
        Book book = bookList.get(0);
        book.setStatus(Constant.STATUS_LEND); // 图书状态改为出借
        lend.setBook(book);

        // 用户赋值
        User user = userList.get(0);
        user.setLend(true);
        lend.setUser(user); // 修改用户借阅状态

        lend.setStatus(Constant.LEND_LEND);
        LocalDate begin = LocalDate.now();

        // 借阅日期和归还日期
        lend.setLendDate(begin);
        lend.setReturnDate(begin.plusDays(30));

        // 修改图书状态和用户状态
        bookService.update(book);
        userService.update(user);

        // 添加借阅数据
        lendDao.add(lend);
    }
}
