package com.ef;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author dmitry.mikhaylovich@bostongene.com
 */
public class LogSaver implements Closeable {

    private static final int BUFFER_SIZE = 10000;

    private final SessionFactory sessionFactory;

    private int buffer = 0;

    private Session session;

    public LogSaver() {
        this.sessionFactory = configureSessionFactory();
        startTransaction();
    }

    private static SessionFactory configureSessionFactory() {
        // TODO turn off logs
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = configuration.getStandardServiceRegistryBuilder().build();
        try {
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public void save(Log log) {
        this.session.save(log);
        this.buffer++;
        checkBuffer();
    }

    @Override
    public void close() throws IOException {
        commitTransaction();
        this.sessionFactory.close();
    }

    private void startTransaction() {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
    }

    private void commitTransaction() {
        this.session.getTransaction().commit();
        this.session.close();
    }

    private void checkBuffer() {
        if (this.buffer > BUFFER_SIZE) {
            commitTransaction();
            this.buffer = 0;
            startTransaction();
        }
    }
}
