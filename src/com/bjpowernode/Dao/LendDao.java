package com.bjpowernode.Dao;

import com.bjpowernode.bean.Lend;

import java.util.List;

public interface LendDao {
    List<Lend> select(Lend lend);

    void add(Lend lend);

    void returnBook(String lendId);

}
