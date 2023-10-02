package com.bitconex.mywebapp;

import com.bitconex.mywebapp.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest(classes = MyWebAppApplication.class)
@Transactional
public class MyWebAppApplicationTests  {

    @Test
    public void testUserEntity() {
        Configuration configuration = new Configuration().configure().addAnnotatedClass(User.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            User user = session.get(User.class, 1);
            assertNotNull(user);

            session.getTransaction().commit();

        }
    }
}
