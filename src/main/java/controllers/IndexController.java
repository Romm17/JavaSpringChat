package controllers;

import model.DB;
import model.User;
import model.hibernateDAO.UserDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import view.IndexPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Roman Usik
 */
public class IndexController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object attribute = req.getSession().getAttribute("login");
        if (attribute != null)
            resp.getWriter().print(IndexPage.getPage(getServletContext(), "success", attribute.toString()));
        else
            resp.getWriter().print(IndexPage.getPage(getServletContext(), "noLogin", ""));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String state = "noLogin";
        String login = "";
        Object attr = req.getSession().getAttribute("login");
        if (attr != null) {
            login = attr.toString();
            state = "success";
        }
        System.out.println(session.getAttribute("login"));
        System.out.println(req.getParameter("login"));
        if (attr == null) {
            if (req.getParameter("Log in") != null) {
                login = req.getParameter("login");
                String pass = req.getParameter("pass");
                User u = new UserDao().read(login);
                if (u != null && u.getPass().equals(pass)) {
                    state = "success";
                    session.setAttribute("login", login);
                } else {
                    state = "failed";
                }
            }
            else if (req.getParameter("Sign up") != null) {
                login = req.getParameter("login");
                String pass = req.getParameter("pass");
                User u = new User(login, pass);
                if (new UserDao().create(u) != null) {
                    state = "success";
                    session.setAttribute("login", login);
                }
            }
        }
        resp.getWriter().print(IndexPage.getPage(getServletContext(), state, login));
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
