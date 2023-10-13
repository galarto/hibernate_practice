package com.galarto.training.repository;

import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AuthorRepository {
    public void saveAuthor(Author author){
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = null;

        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(author);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

    public void updateAuthor(Author author){
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = null;
        try{
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.saveOrUpdate(author);
            session.getTransaction().commit();
        } finally {
            session.close();
            factory.close();
        }
    }

    public Author getAuthor(int id) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = null;
        try {
            session = factory.getCurrentSession();;
            session.beginTransaction();
            Author author = session.get(Author.class, id);
            session.getTransaction().commit();
            return author;
        } finally {
            session.close();
            factory.close();
        }
    }

    public void deleteAuthor(int id) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = null;
        try {
            session = factory.getCurrentSession();;
            session.beginTransaction();
            Author author = session.get(Author.class, id);
            session.delete(author);
        } finally {
            session.close();
            factory.close();
        }
    }


}
