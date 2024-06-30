package met.medconnect.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import met.medconnect.model.User;
import met.medconnect.security.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import met.medconnect.repo.UserRepository;


import javax.sql.DataSource;
import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    @GetMapping({ "/login"})
    public String showLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        String idToken = body.get("idToken");
        if (idToken == null) {
            return ResponseEntity.badRequest().body("Missing ID token.");
        }
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String email = decodedToken.getEmail();
            User user = userRepository.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
            }

            String jwt = TokenUtility.generateToken(idToken, user.getStaffId().toString(), user.getStaffRole());

            Cookie authCookie = new Cookie("AuthToken", jwt);
            authCookie.setHttpOnly(true);
            authCookie.setPath("/");
            authCookie.setSecure(true);
            authCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(authCookie);

            return ResponseEntity.ok().body("{\"redirectUrl\": \"/dashboard\"}");
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token or database error.");
        }
    }

}