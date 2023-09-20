package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyWebAppApplicationTests {

    /*@Test
    void contextLoads() {
    }*/

    public static void main(String[] args) {
        Configuration configuration = new Configuration().configure().addAnnotatedClass(Admin.class);


        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Admin admin = session.get(Admin.class, 1);
            System.out.println(admin.getUserLoginName());
            System.out.println(admin.getUserEmail());

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }

}
