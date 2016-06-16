package model.hibernateDAO;

import model.DB;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author Roman Usik
 */
public class UserDao implements Dao<User> {

    @Override
    public Integer create(User object) {
        Session session = DB.getSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            session.save(object);
            tr.commit();
            return 0;
        } catch (Exception e) {
            if (tr != null)
                tr.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User read(String name) {
        Session session = DB.getSession();
        try {
            return (User) session.createQuery("from User WHERE login = '" + name + "'").list().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List getAll() {
        return null;
    }
}
