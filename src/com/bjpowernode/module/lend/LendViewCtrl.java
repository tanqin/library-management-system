package com.bjpowernode.module.lend;

import com.bjpowernode.module.book.BookViewCtrl;
import com.bjpowernode.service.BookService;
import com.bjpowernode.service.LendService;
import com.bjpowernode.service.UserService;
import com.bjpowernode.service.impl.BookServiceImpl;
import com.bjpowernode.service.impl.LendServiceImpl;
import com.bjpowernode.service.impl.UserServiceImpl;
import com.gn.App;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.global.util.Alerts;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 图书管理
 *
 * @author admin
 */
public class LendViewCtrl implements Initializable {

    @FXML
    private TableView<Lend> lendTableView;
    @FXML
    private TableColumn<Lend, String> c1;
    @FXML
    private TableColumn<Lend, String> c2;
    @FXML
    private TableColumn<Lend, String> c3;
    @FXML
    private TableColumn<Lend, String> c4;
    @FXML
    private TableColumn<Lend, String> c5;
    @FXML
    private TableColumn<Lend, String> c6;
    @FXML
    private TableColumn<Lend, String> c7;

    @FXML
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    ObservableList<Lend> lends = FXCollections.observableArrayList();

    private LendService lendService = new LendServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Lend> lendList = lendService.select(null);
        // 计算用户逾期后的余额
        lendList.forEach(l -> {
            LocalDate returnDate = l.getReturnDate();
//            LocalDate now = LocalDate.now();
            LocalDate now = LocalDate.parse("2022-04-20");

            // 计算时间差
//            int days = now.compareTo(returnDate);
            int days = Period.between(returnDate, now).getDays();
            if (days > 0) {
                /*
                    逾期：
                        逾期小于等于 30 天，用户余额扣除用户预期天数金额，归还日期自动改为当天
                        逾期大于 30 天，用户余额扣除 30 元，归还日期自动改为当天
                        如果用户余额小于 0，则状态变为冻结
                        冻结的用户不能还书，需要先充值
                 */
                // 方法一
              /*  User user = l.getUser();
                if (days <= 30) {
                    // 计算滞纳金
                    user.setMoney(user.getMoney().subtract(BigDecimal.valueOf(days)));
                } else {
                    user.setMoney(user.getMoney().subtract(BigDecimal.valueOf(30)));
                }
                l.setReturnDate(now);
                if (user.getMoney().compareTo(BigDecimal.ZERO) < 0) {
                    user.setStatus(Constant.USER_FROZEN);
                }
                l.setUser(user);
                lendService.update(l);
                userService.update(user);*/

                // 方法二
                User user = l.getUser();
                BigDecimal originMoney = user.getMoney();
                BigDecimal delayMoney;

                // 计算滞纳金
                if (days <= 30) {
                    delayMoney = new BigDecimal(days);
                } else {
                    delayMoney = new BigDecimal(30);
                }
                // 扣款
                user.setMoney(originMoney.subtract(delayMoney));

                // 归还日期改为今日
                l.setReturnDate(now);
                if (user.getMoney().compareTo(BigDecimal.ZERO) < 0) {
                    user.setStatus(Constant.USER_FROZEN);
                }
                l.setUser(user);
                lendService.update(l);
                userService.update(user);
            }
        });
        lends.addAll(lendList);
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        //获取图书名称
        c2.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getBookName())
        );
        c3.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getIsbn())
        );
        c4.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getUser().getName())
        );
        c5.setCellValueFactory(new PropertyValueFactory<>("lendDate"));
        c6.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
        lendTableView.setItems(lends);

    }

    /*
        查询
     */
    @FXML
    private void lendSelect() {
        String lendName = lendNameField.getText();
        String isbn = isbnField.getText();
        Book book = new Book();
        book.setBookName(lendName);
        book.setIsbn(isbn);
        Lend lend = new Lend();
        lend.setBook(book);
        List<Lend> selectList = lendService.select(lend);

        lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(selectList));
        lendTableView.setItems(lends);
    }

    /*
        还书
     */
    @FXML
    private void returnBook() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("未选择", "请先选择要归还的书籍");
            return;
        }
        List<Lend> lendList = lendService.returnBook(lend);
        ObservableListWrapper<Lend> lends = new ObservableListWrapper<>(new ArrayList<>(lendList));
        lendTableView.setItems(lends);
    }

    /*
        续借
     */
    @FXML
    private void renew() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("未选择", "请先选择要续借的书籍");
            return;
        }
        lend.setReturnDate(LocalDate.now().plusDays(30));
    }


    /*
        初始化stage
     */
    private void initStage(Lend lend) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/bjpowernode/module/lend/LendHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        LendHandleViewCtrl controller = (LendHandleViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setLends(lends);
        controller.setLend(lend);
        controller.setLendTableView(lendTableView);
//        stage.setResizable(false);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }
}
