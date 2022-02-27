package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.BookDao;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.PathConstant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {
    /**
     * ��ѯͼ��
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
                    // ��ѯȫ������
                    return bookList;
                } else {
                    // ������ѯ
                    List<Book> conditionList = new ArrayList<Book>();

                    if (!"".equals(book.getBookName()) && !"".equals(book.getIsbn())) {
                        // ��ѯ��������
                        conditionList = bookList.stream().filter(b -> b.getBookName().equals(book.getBookName())).collect(Collectors.toList());
                        conditionList = conditionList.stream().filter(b -> b.getIsbn().equals(book.getIsbn())).collect(Collectors.toList());

                    } else {
                        // ��ѯ��������
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
     * ���ͼ��
     *
     * @param book
     */
    @Override
    public void add(Book book) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            // ��ȡ�ļ�����
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> bookList = (List<Book>) ois.readObject();


            // ���ͼ��
            if (bookList != null) {
                // ���� id ֵ
                Book lastBook = bookList.get(bookList.size() - 1);
                book.setId(lastBook.getId() + 1);
                bookList.add(book);
            } else {
                bookList = new ArrayList<Book>();
                book.setId(1);
                bookList.add(book);
            }

            // �洢�����ݵ��ļ���
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
     * ɾ��ͼ��
     *
     * @param id
     */
    @Override
    public void delete(int id) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // ��ȡ����
            ois = new ObjectInputStream(new FileInputStream(PathConstant.BOOK_PATH));
            List<Book> bookList = (List<Book>) ois.readObject();

            if (bookList != null) {
                // ��ѯ����
                Book book = bookList.stream().filter(b -> b.getId() == id).findFirst().get();

                // ɾ������
                // ɾ��ǰ��Ҫ��֤ Book ����д�� equals() �� hashCode() ����
                bookList.remove(book);

                // �洢����
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

