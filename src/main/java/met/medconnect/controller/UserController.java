package met.medconnect.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    private DataSource dataSource;

    @GetMapping({"/", "/login"})
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        if (idToken == null) {
            return ResponseEntity.badRequest().body("Missing ID token");
        }
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            System.out.println("Decoded Token UID: " + uid);
            System.out.println("Decoded Token Email: " + email);

            // Check user role from the Staff table
            try (Connection connection = dataSource.getConnection()) {
                String query = "SELECT staff_role FROM Staff WHERE staff_email = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, email);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            String role = resultSet.getString("staff_role");
                            System.out.println("User Role: " + role);
                            Map<String, String> response = new HashMap<>();
                            response.put("uid", uid);
                            response.put("role", role);
                            return ResponseEntity.ok(response);
                        } else {
                            return ResponseEntity.status(403).body("User not found in staff database");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Database error");
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid ID token");
        }
    }
//
//    @GetMapping("/patients")
//    public ResponseEntity<List<Map<String, Object>>> getPatients(@RequestHeader("Authorization") String idToken) throws FirebaseAuthException, SQLException {
//        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken.replace("Bearer ", ""));
//        String uid = decodedToken.getUid();
//        System.out.println("Get Patients Token UID: " + uid);
//
//        List<Map<String, Object>> patients = new ArrayList<>();
//        try (Connection connection = dataSource.getConnection()) {
//            String query = "SELECT * FROM Patients";
//            try (PreparedStatement statement = connection.prepareStatement(query);
//                 ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    Map<String, Object> patient = new HashMap<>();
//                    patient.put("patientId", resultSet.getInt("patient_id"));
//                    patient.put("firstName", resultSet.getString("first_name"));
//                    patient.put("lastName", resultSet.getString("last_name"));
//                    patient.put("dateOfBirth", resultSet.getDate("date_of_birth"));
//                    patient.put("gender", resultSet.getString("gender"));
//                    patient.put("phoneNumber", resultSet.getString("phone_number"));
//                    patient.put("email", resultSet.getString("email"));
//                    patient.put("address", resultSet.getString("address"));
//                    patient.put("emergencyContact", resultSet.getString("emergency_contact"));
//                    patients.add(patient);
//                }
//            }
//        }
//        return ResponseEntity.ok(patients);
//    }
//
//    @GetMapping("/dashboard")
//    public ResponseEntity<String> getDashboard(@RequestHeader("Authorization") String idToken) throws FirebaseAuthException, SQLException {
//        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken.replace("Bearer ", ""));
//        String uid = decodedToken.getUid();
//        String email = decodedToken.getEmail();
//        System.out.println("Dashboard Token UID: " + uid);
//        System.out.println("Dashboard Token Email: " + email);
//
//        // Check if the user is in the Staff table
//        try (Connection connection = dataSource.getConnection()) {
//            String query = "SELECT role FROM Staff WHERE email = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, email);
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String role = resultSet.getString("role");
//                        System.out.println("User Role for Dashboard: " + role);
//                        return ResponseEntity.ok("<h1>Dashboard</h1><p>Welcome, " + role + "</p>");
//                    } else {
//                        return ResponseEntity.status(403).body("User not found in staff database");
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Database error");
//        }
//    }

    @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.setHeader("Set-Cookie", "JSESSIONID=; HttpOnly; Path=/; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT");

        return new RedirectView("/index?logout");
    }

}
