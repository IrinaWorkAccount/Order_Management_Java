package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Calendar;

public class HibernateTests {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure()
                .build();

        SessionFactory sessionFactory = new Configuration()
                .configure("/org/nitish/caller/hibernate.cfg.xml").buildSessionFactory();
        Session session =sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Product product = new Product();
        product.setProductName("SomeProduct1");
        product.setProductSalePrice(1290.78);
        Calendar today = Calendar.getInstance();
        product.setProductAvailableFrom(today.getTime());

        session.save(product);
        transaction.commit();
        session.close();
        sessionFactory.close();

    }
}
