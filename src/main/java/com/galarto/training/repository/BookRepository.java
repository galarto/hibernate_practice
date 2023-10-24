package com.galarto.training.repository;

import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.entity.Customer;
import com.galarto.training.entity.Genre;
import com.galarto.training.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;



import java.util.List;

public class BookRepository {

    public void saveBook(Book book) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        session.save(book);
        t.commit();
        session.close();
    }

    public Book getBook(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Book.class, id);
    }

    public void updateBook(Book book) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(book);
        transaction.commit();
        session.close();
    }

    public void deleteBook(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Book book = session.get(Book.class, id);
        session.delete(book);
        transaction.commit();
        session.close();
    }

    public List<Book> getAvailableBooks() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Book> bookList = session.createQuery("from Book b where b.count > 0", Book.class).getResultList();
        transaction.commit();
        session.close();
        return bookList;
    }

    public List<Book> getBooks(Genre genre) {
        List<Book> bookList = HibernateSessionFactoryUtil
                .getSessionFactory().openSession()
                .createQuery(String.format("from Book where genre = '%s'", genre.name()), Book.class).list();
        return bookList;
    }

    public void addBookToAuthor(Author author, Book book) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        author.addBookToAuthor(book);
        session.update(author);
        session.save(book);
        transaction.commit();
        session.close();
    }
}
