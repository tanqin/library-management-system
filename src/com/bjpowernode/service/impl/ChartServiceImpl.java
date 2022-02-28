package com.bjpowernode.service.impl;

import com.bjpowernode.Dao.ChartDao;
import com.bjpowernode.Dao.impl.ChartDaoImpl;
import com.bjpowernode.service.ChartService;

import java.util.Map;

public class ChartServiceImpl implements ChartService {
    ChartDao chartDao = new ChartDaoImpl();
    @Override
    public Map<String, Integer> bookTypeCount() {
        return chartDao.bookTypeCount();
    }
}
