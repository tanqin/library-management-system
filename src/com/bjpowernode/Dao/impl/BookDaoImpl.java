package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.BookDao;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {
    /**
     * 查询图书
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

    /**
     * 添加图书
     *
     * @param book
     */
    @Override
    public void add(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            // 读取文件数据
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> bookList = (List<Book>) ois.readObject();


            // 添加图书
            if (bookList != null) {
                // 设置 id 值
                Book lastBook = bookList.get(bookList.size() - 1);
                book.setId(lastBook.getId() + 1);
                bookList.add(book);
            } else {
                bookList = new ArrayList<Book>();
                book.setId(1);
                bookList.add(book);
            }

            // 存储新数据到文件中
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
            oos.writeObject(bookList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除图书
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // 读取数据
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> bookList = (List<Book>) ois.readObject();

            if (bookList != null) {
                // 查询数据
                Book book = bookList.stream().filter(b -> b.getId() == id).findFirst().get();

                // 删除数据
                // 删除前需要保证 Book 类重写了 equals() 和 hashCode() 方法
                bookList.remove(book);

                // 存储数据
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(bookList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 图书修改
     *
     * @param book
     */
    @Override
    public void update(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // 读取数据
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> bookList = (List<Book>) ois.readObject();

            if (bookList != null) {
                // 查询原始数据
                Book originBook = bookList.stream().filter(b -> b.getId() == book.getId()).findFirst().get();

//                originBook.setBookName(book.getBookName());
//                originBook.setIsbn(book.getIsbn());
//                originBook.setAuthor(book.getAuthor());
//                originBook.setPublisher(book.getPublisher());
//                originBook.setType(book.getType());

                // 当要修改的属性很多时，上面的写法就显得笨拙
                // 所以我们需要利用反射封装一个通用的工具类来进行两个对象间的赋值
                BeanUtil.populate(originBook, book);

                // 存储数据
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.BOOK_PATH));
                oos.writeObject(bookList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

