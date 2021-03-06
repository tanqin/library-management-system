package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.LendDao;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.PathConstant;
import com.bjpowernode.util.BeanUtil;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LendDaoImpl implements LendDao {
    @Override
    public List<Lend> select(Lend lend) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH))) {
            List<Lend> lendList = (List<Lend>) ois.readObject();

            if (lend == null || ("".equals(lend.getBook().getBookName()) && "".equals(lend.getBook().getIsbn()))) {
                // 查询全部数据
                return lendList;
            } else {
                List<Lend> conditionList = new ArrayList<>();
                // 条件查询 - 方法一
            /*    conditionList = lendList.stream().filter(l -> {
                    if (!"".equals(lend.getBook().getBookName()) && !"".equals(lend.getBook().getIsbn())) {
                        return l.getBook().getBookName().equals(lend.getBook().getBookName()) && l.getBook().getIsbn().equals(lend.getBook().getIsbn());
                    } else {
                        if (!"".equals(lend.getBook().getBookName())) {
                            return l.getBook().getBookName().equals(lend.getBook().getBookName());
                        } else {
                            return l.getBook().getIsbn().equals(lend.getBook().getIsbn());
                        }
                    }
                }).collect(Collectors.toList());*/
                // 条件查询 - 方法二
                if (!"".equals(lend.getBook().getBookName()) && !"".equals(lend.getBook().getIsbn())) {
                    conditionList = lendList.stream().filter(l -> l.getBook().getBookName().equals(lend.getBook().getBookName())).collect(Collectors.toList());
                    conditionList = conditionList.stream().filter(l -> l.getBook().getIsbn().equals(lend.getBook().getIsbn())).collect(Collectors.toList());
                } else {
                    if (!"".equals(lend.getBook().getBookName())) {
                        conditionList = lendList.stream().filter(l -> l.getBook().getBookName().equals(lend.getBook().getBookName())).collect(Collectors.toList());
                    }
                    if (!"".equals(lend.getBook().getIsbn())) {
                        conditionList = lendList.stream().filter(l -> l.getBook().getIsbn().equals(lend.getBook().getIsbn())).collect(Collectors.toList());
                    }
                }
                return conditionList;
            }
        } catch (Exception e) {
            // 追踪输出至标准错误流
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    /**
     * 添加借阅
     *
     * @param lend
     */
    @Override
    public void add(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> lendList = (List<Lend>) ois.readObject();
            if (lendList != null) {
                lendList.add(lend);
            } else {
                lendList = new ArrayList<>();
            }
            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
            oos.writeObject(lendList);
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
     * 还书
     *
     * @param lendId
     */
    @Override
    public void returnBook(String lendId) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> lendList = (List<Lend>) ois.readObject();

            List<Lend> newList = lendList.stream().filter(l -> !(l.getId().equals(lendId))).collect(Collectors.toList());

            oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
            oos.writeObject(newList);
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

    @Override
    public void update(Lend lend) {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            // 读取原始数据
            ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH));
            List<Lend> lendList = (List<Lend>) ois.readObject();

            if (lendList != null) {
                // 查找数据，赋新值
                Lend originLend = lendList.stream().filter(l -> l.getId().equals(lend.getId())).findFirst().get();
                BeanUtil.populate(originLend, lend);

                // 输出新数据
                oos = new ObjectOutputStream(new FileOutputStream(PathConstant.LEND_PATH));
                oos.writeObject(lendList);
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
