package com.bjpowernode.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    //状态
    private String status;
    //余额
    private BigDecimal money;
    // 标识当前用户是否有借书
    private Boolean isLend;

    public User() {
    }

    public User(int id, String name, String status, BigDecimal money, Boolean isLend) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.money = money;
        this.isLend = isLend;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Boolean getLend() {
        return isLend;
    }

    public void setLend(Boolean lend) {
        isLend = lend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (status != null ? !status.equals(user.status) : user.status != null) return false;
        if (money != null ? !money.equals(user.money) : user.money != null) return false;
        return isLend != null ? isLend.equals(user.isLend) : user.isLend == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (isLend != null ? isLend.hashCode() : 0);
        return result;
    }
}
