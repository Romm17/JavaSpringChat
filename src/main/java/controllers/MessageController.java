package controllers;

import model.Message;
import model.User;
import model.hibernateDAO.MessageDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author Roman Usik
 */
public class MessageController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MessageDao messageDao = new MessageDao();
        List messages = messageDao.getAll();
        String result = "[";
        for (Iterator i = messages.iterator(); i.hasNext();) {
            Message m = (Message) i.next();
            result += "[\"" + m.getUser().getLogin() + "\", ";
            result += "\"" + m.getText() + "\"]";
            if (i.hasNext()) {
                result += ", ";
            }
        }
        result += "]";
        resp.getWriter().println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newMessage = req.getParameter("message");
        if (newMessage != null) {
            User u = new User(
                    req.getSession().getAttribute("login").toString(),
                    "???"
            );
            Message m = new Message(u, newMessage);
            new MessageDao().create(m);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
