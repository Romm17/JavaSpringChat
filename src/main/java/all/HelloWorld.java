package all; /**
 * @author Roman Usik
 */
// Import required java libraries
import java.io.*;
import java.sql.ResultSet;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class HelloWorld extends HttpServlet {

    String[] messages;
    private HttpSession session;

    public void init() throws ServletException
    {

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
//        session = request.getSession();
        PrintWriter out = response.getWriter();

        if (request.getParameter("up") != null) {
            FirstExample.run(out, request.getParameter("up"),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        }
        else {
            FirstExample.run(out, request.getParameter("up"),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        }

        // Set response content type
//        response.setContentType("text/html");
//
//        if (request.getParameter("login") != null) {
//            session.setAttribute("login", request.getParameter("login"));
//        }
//
//        Object login = session.getAttribute("login");
//
//        if (login != null) {
//            out.println("<h3> Hello, " + login + "</h3>");
//        }
//        else {
//            out.println("<form action=\"hello_example\" method=\"GET\">"
//                    + "<label>Name : <input type=\"text\" name=\"login\"/>"
//                    + "<label>Pass : <input type=\"text\" name=\"pass\"/>"
//                    + "<input type=\"submit\"/>"
//                    + "</form>");
//        }

        // Actual logic goes here.
//        out.println("<h1>" + message + "</h1>");
    }

    public void destroy()
    {
        // do nothing.
    }
}

