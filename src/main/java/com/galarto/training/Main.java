package com.galarto.training;

import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.entity.Customer;
import com.galarto.training.entity.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
//            Author a1 = new Author("Alexander", "Pushkin", "Sergeevich");
//            Book book = new Book("Captain's daughter", 1836, 10, Genre.HISTORIC_NOVEL,
//                    BigMoney.of(CurrencyUnit.USD, 20));
//            Customer customer = new Customer("Pyotr", "Svidler", BigMoney.of(CurrencyUnit.USD, 250));
//            a1.addBookToAuthor(book);
//            customer.addBookToCustomer(book);

            session.beginTransaction();

            Author author = session.get(Author.class, 1);
            System.out.println(author);
            System.out.println(author.getBooks() + " ");


            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

}