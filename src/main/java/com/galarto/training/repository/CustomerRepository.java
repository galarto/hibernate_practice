package com.galarto.training.repository;

import com.galarto.training.entity.Author;
import com.galarto.training.entity.Book;
import com.galarto.training.entity.Customer;
import com.galarto.training.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;

public class CustomerRepository {

    public void saveCustomer(Customer customer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
    }

    public Customer getCustomer(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Customer.class, id);
    }

    public Customer getCustomer(String email) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "From Customer where email = :email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        Customer customer = (Customer) query.getSingleResult();
        transaction.commit();
        session.close();
        return customer;
    }

    public void updateCustomer(Customer customer) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(customer);
        transaction.commit();
        session.close();
    }

    public void deleteCustomer(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.delete(customer);
        transaction.commit();
        session.close();
    }
}
