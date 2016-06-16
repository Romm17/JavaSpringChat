package view;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Roman Usik
 */
public class IndexPage {

    private static Map<String, String> userWindow;

    static {
        userWindow = new HashMap<>();
        userWindow.put("success", "<p>Hello, %login%</p>");
        userWindow.put("failed", "Incorrect login or pass...(");
        userWindow.put("noLogin", "<a href=\"access?login\">Log in</a><a href=\"access?signup\">Sign up</a>");
    }

    public static String getPage(ServletContext servletContext, String state, String login) {
        System.out.println("State : " + state + ", login : " + login);
        String file = null;
        try {
            file = new String(
                    Files.readAllBytes(
                            Paths.get(
                                    servletContext
                                            .getRealPath("html/index.html")
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            file = file.replaceAll("%userWindow%", userWindow.get(state)).replace("%login%", login);
        }
        return file;
    }
}
