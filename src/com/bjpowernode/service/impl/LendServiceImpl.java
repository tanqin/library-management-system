package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.LendDao;
import com.bjpowernode.Dao.impl.LendDaoImpl;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.service.LendService;

import java.util.List;

public class LendServiceImpl implements LendService {
    private LendDao lendDao = new LendDaoImpl();

    @Override
    public List<Lend> select(Lend lend) {
       return lendDao.select(lend);
    }
}
