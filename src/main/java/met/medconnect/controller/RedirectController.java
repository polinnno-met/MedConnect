package met.medconnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/initiate-dashboard")
public class RedirectController {

    @GetMapping
    public void initiateDashboard(HttpServletResponse response) throws IOException {
        // Perform the server-side redirect
        response.sendRedirect("/dashboard");
    }
}
