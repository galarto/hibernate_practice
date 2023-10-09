package com.galarto.training.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.BigMoney;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private String surname;
    @Type(type = "com.galarto.training.util.MoneyUserType")
    @Columns(columns = {@Column(name = "balance"), @Column(name = "currency")})
    private BigMoney balance;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_customers"
            , joinColumns = @JoinColumn(name = "customer_id")
            , inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> bookList;

    public Customer() {
    }

    public Customer(String name, String surname, BigMoney balance) {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public void addBookToCustomer(Book book) {
        if(bookList == null) {
            bookList = new ArrayList<>();
        }
        bookList.add(book);
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public BigMoney getBalance() {
        return balance;
    }

    public void setBalance(BigMoney balance) {
        this.balance = balance;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", balance=" + balance +
                '}';
    }
}
