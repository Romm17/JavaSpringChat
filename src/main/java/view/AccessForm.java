package view;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Roman Usik
 */
public class AccessForm {

    public static String getFrom(ServletContext servletContext, String title, String formName) {
        String file = null;
        try {
            file = new String(
                    Files.readAllBytes(
                            Paths.get(
                                    servletContext
                                            .getRealPath("html/access.html")
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            file = file.replaceAll("%title%", title).replaceAll("%formName%", formName);
        }
        return file;
    }
}
