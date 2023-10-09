package com.galarto.training.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.BigMoney;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String title;

    @Column
    private int year;

    @Column
    private int count;
    //Связь двусторонняя и каскадом выполняются все операции кроме удаления
    //Если удаляется книга - автор остается. Автора из базы удалить нельзя из-за за fk,
    //Ввиду этого, нужно будет удалить все книги автора, прежде, чем удалить его самого
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id")
    private Author author;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(255)")
    private Genre genre;

    @Type(type = "com.galarto.training.util.MoneyUserType")
    @Columns(columns = {@Column(name = "price"), @Column(name = "currency")})
    private BigMoney price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "books_customers"
            , joinColumns = @JoinColumn (name = "book_id")
            , inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customerList;

    //К сожалению, currencyUnit в joda.money не содержит рубли
    public Book() {
    }

    public Book(String title, int year, int count, Genre genre, BigMoney price) {
        this.title = title;
        this.year = year;
        this.count = count;
        this.genre = genre;
        this.price = price;
    }

    public void addCustomerToBook(Customer customer) {
        if (customerList == null) {
            customerList = new ArrayList<>();
        }
        customerList.add(customer);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BigMoney getPrice() {
        return price;
    }

    public void setPrice(BigMoney price) {
        this.price = price;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", count=" + count +
                ", genre=" + genre +
                ", price=" + price +
                '}';
    }
}
