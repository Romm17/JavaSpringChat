package model.hibernateDAO;

import model.DB;
import model.Message;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * @author Roman Usik
 */
public class MessageDao implements Dao<Message> {

    @Override
    public Integer create(Message object) {
        final Session session = DB.getSession();
        Transaction tr = null;
        try {
            tr = session.beginTransaction();
            String userName = object.getUser().getLogin();
            List users = session.createQuery("FROM User WHERE login = '"
                    + userName + "'").list();
            if (!users.isEmpty()) {
                object.setUser((User) users.get(0));
                session.save(object);
            }
            tr.commit();
        } catch (Exception e) {
            if (tr != null)
                tr.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Message read(String name) {
        return null;
    }

    @Override
    public List getAll() {
        try {
            final Session session = DB.getSession();
            List messages = session.createQuery("from Message").list();
            session.close();
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
