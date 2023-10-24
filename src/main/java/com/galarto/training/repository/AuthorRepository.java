package com.galarto.training.repository;

import com.galarto.training.entity.Author;
import com.galarto.training.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class AuthorRepository {
    public void saveAuthor(Author author){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(author);
        transaction.commit();
        session.close();
    }

    public void updateAuthor(Author author){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(author);
        transaction.commit();
        session.close();
    }

    public Author getAuthor(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class, id);
        transaction.commit();
        session.close();
        return author;
    }

    public void deleteAuthor(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Author author = session.get(Author.class, id);
        session.delete(author);
        transaction.commit();
        session.close();
    }


}
