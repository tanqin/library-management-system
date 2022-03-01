package com.bjpowernode.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/*
    借书
 */
public class Lend implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // 由于借书记录会比较多，为防止数值溢出，id 使用 String 类型
    private String id;

    //借出的书籍
    private Book book;

    //借阅者
    private User user;

    //状态
    private String status;

    //出借日期
    private LocalDate lendDate;

    //归还日期
    private LocalDate returnDate;

    public Lend() {
    }

    public Lend(String id, Book book, User user, String status, LocalDate lendDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.status = status;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lend lend = (Lend) o;

        if (id != null ? !id.equals(lend.id) : lend.id != null) return false;
        if (book != null ? !book.equals(lend.book) : lend.book != null) return false;
        if (user != null ? !user.equals(lend.user) : lend.user != null) return false;
        if (status != null ? !status.equals(lend.status) : lend.status != null) return false;
        if (lendDate != null ? !lendDate.equals(lend.lendDate) : lend.lendDate != null) return false;
        return returnDate != null ? returnDate.equals(lend.returnDate) : lend.returnDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lendDate != null ? lendDate.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        return result;
    }
}
