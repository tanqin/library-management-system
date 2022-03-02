package com.bjpowernode.Dao.impl;

import com.bjpowernode.Dao.LendDao;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.PathConstant;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LendDaoImpl implements LendDao {
    @Override
    public List<Lend> select(Lend lend) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PathConstant.LEND_PATH))) {
            List<Lend> lendList = (List<Lend>) ois.readObject();

            if (lend == null || ("".equals(lend.getBook().getBookName()) && "".equals(lend.getBook().getIsbn()))) {
                // ��ѯȫ������
                return lendList;
            } else {
                List<Lend> conditionList = new ArrayList<>();
                // ������ѯ - ����һ
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
                // ������ѯ - ������
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
            // ׷���������׼������
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
