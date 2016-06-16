package model;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;

/**
 * @author Roman Usik
 */
public class DB {
    private static final SessionFactory ourSessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        User u;
        Message message;
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            List users = session.createQuery("FROM User").list();
            if (users.isEmpty()) {
                System.out.println("Adding user");
                u = new User("roma", "123");
                u.setId((Integer)session.save(u));
            }
            else {
                u = (User) users.get(0);
            }
            message = new Message(u, "Test message");
            message.setId((Integer) session.save(message));
            tr.commit();
        } catch (Exception e) {
            if (tr != null)
                tr.rollback();
        } finally {
            session.close();
        }
        printAll();
    }

    public static void printAll() {
        final Session session = getSession();
        try {
            System.out.println("Selecting all from users...");
            List users = session.createQuery("From User").list();
            for (Object o : users) {
                System.out.println(o);
            }
            System.out.println("Selecting all from messages...");
            List messages = session.createQuery("From Message").list();
            for (Object o : messages) {
                System.out.println(o);
            }
        } finally {
            session.close();
        }
    }
}
