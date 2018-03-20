package com.ef;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author dmitry.mikhaylovich@bostongene.com
 */
public class LogSaver implements Closeable {

    private final SessionFactory sessionFactory;

    public LogSaver() {
        this.sessionFactory = configureSessionFactory();
    }

    private static SessionFactory configureSessionFactory() {
        Configuration conf = new Configuration();
        conf.configure("hibernate.cfg.xml");
        // FIXME should be only in xml file
        conf.addAnnotatedClass(Log.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        try {
            return conf.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public void save(Log log) {
        try (Session session = this.sessionFactory.openSession()) {
            System.out.println(log);
            Transaction transaction = session.beginTransaction();
            session.save(log);
            transaction.commit();
        }
    }

    @Override
    public void close() throws IOException {
        this.sessionFactory.close();
    }
}
