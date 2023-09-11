package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Objects;

@SpringBootTest//in dem test entity scan scan machen , der findet entity nicht
public class HibernateTests {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure()
                .build();

        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        Session session =sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Product product = new Product();
        product.setProductName("SomeProduct1");
        product.setProductSalePrice(1290.78);
        Calendar today = Calendar.getInstance();
        product.setProductAvailableFrom(today.getTime());

        if (Objects.isNull(session.find(Product.class, product.getId()))) {
            session.persist(product);
        } else {
            session.merge(product);
        }
        transaction.commit();
        session.close();
        sessionFactory.close();

    }
}
