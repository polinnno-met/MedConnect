package met.medconnect.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Controller
public class RedirectController implements ErrorController {

    private static final String ERROR_PATH = "/error";


    @RequestMapping("/")
    public void initiateDashboard(HttpServletResponse response) throws IOException {
        response.sendRedirect("/dashboard");
    }

    @RequestMapping("/error")
    public void handleError(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }
}
