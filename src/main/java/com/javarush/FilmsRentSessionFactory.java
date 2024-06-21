package com.javarush;

import com.javarush.domain.*;
import com.javarush.tools.KeyValue;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class FilmsRentSessionFactory {
    private static SessionFactory sessionFactory;
    private static final Properties properties;
    private static final KeyValue keyData = new KeyValue();

    static {
        KeyValue keyData = new KeyValue();
        properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://" + keyData.DATA_HOST_NAME + ":3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, keyData.DATA_USER_PASS);
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        properties.put(Environment.STATEMENT_BATCH_SIZE, "100");
    }

    public SessionFactory getSessionFactory() {

        if (this.sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(Actor.class)
                    .addAnnotatedClass(Address.class)
                    .addAnnotatedClass(Category.class)
                    .addAnnotatedClass(City.class)
                    .addAnnotatedClass(Country.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Film.class)
                    .addAnnotatedClass(FilmText.class)
                    .addAnnotatedClass(Inventory.class)
                    .addAnnotatedClass(Language.class)
                    .addAnnotatedClass(Payment.class)
                    .addAnnotatedClass(Rental.class)
                    .addAnnotatedClass(Staff.class)
                    .addAnnotatedClass(Store.class)
                    .addProperties(properties)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}
