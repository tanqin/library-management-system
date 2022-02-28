package com.bjpowernode.module.charts;

import com.bjpowernode.service.ChartService;
import com.bjpowernode.service.impl.ChartServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author admin
 */
public class PieChart implements Initializable {

    @FXML
    private javafx.scene.chart.PieChart pieChart;

    private ChartService chartService = new ChartServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ��ȡͼ��ͳ������
        Map<String, Integer> mapList = chartService.bookTypeCount();
        // ת�� - ��ʽһ�����ϴ洢
    /*    Iterator<Map.Entry<String, Integer>> iterator = mapList.entrySet().iterator();
        ArrayList<Data> dataList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            dataList.add(new Data(next.getKey(), next.getValue()));
        }*/

        // ת�� - ��ʽ��������洢
        Data[] dataList = new Data[mapList.size()];
        // �����±�
        int i = 0;
        Iterator<Map.Entry<String, Integer>> iterator = mapList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            // i++�������㣬�� ++
            dataList[i++] = new Data(next.getKey(), next.getValue());
        }
        // ��ͳ��������ӵ� observableArrayList
        ObservableList<Data> pieChartData = FXCollections.observableArrayList(
                dataList
        );
        pieChart.setData(pieChartData);
        pieChart.setClockwise(false);
    }
}
