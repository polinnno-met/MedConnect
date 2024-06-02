package met.medconnect.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DataSource dataSource;

    @GetMapping
    public Map<String, Object> getDashboardData(@RequestHeader("Authorization") String idToken) throws FirebaseAuthException, SQLException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken.replace("Bearer ", ""));
        String uid = decodedToken.getUid();

        String role = getUserRoleByUid(uid); // Implement this method to get user role from your database

        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("patients", getPatients(role, uid));
        dashboardData.put("appointments", getAppointments(role, uid));
        dashboardData.put("medicalRecords", getMedicalRecords(role, uid));
        dashboardData.put("billing", getBilling(role, uid));

        System.out.println("Dashboard Data: " + dashboardData);

        return dashboardData;
    }

    private String getUserRoleByUid(String uid) throws SQLException {
        // Implement logic to get the user role by UID from the Staff table
        String role = "Administrator"; // Placeholder for the actual implementation
        return role;
    }

    private List<Map<String, Object>> getPatients(String role, String uid) throws SQLException {
        String query = "SELECT * FROM Patients";
        if (role.equals("Doctor")) {
            query = "SELECT DISTINCT p.* FROM Patients p JOIN Appointments a ON p.patient_id = a.patient_id WHERE a.doctor_id = ?";
        }
        return executeQuery(query, role.equals("Doctor") ? Collections.singletonList(uid) : Collections.emptyList());
    }

    private List<Map<String, Object>> getAppointments(String role, String uid) throws SQLException {
        String query = "SELECT * FROM Appointments ORDER BY appointment_date DESC";
        if (role.equals("Doctor")) {
            query = "SELECT * FROM Appointments WHERE doctor_id = ? ORDER BY appointment_date DESC";
        }
        return executeQuery(query, role.equals("Doctor") ? Collections.singletonList(uid) : Collections.emptyList());
    }

    private List<Map<String, Object>> getMedicalRecords(String role, String uid) throws SQLException {
        String query = "SELECT * FROM MedicalRecords ORDER BY appointment_id DESC";
        if (role.equals("Doctor")) {
            query = "SELECT * FROM MedicalRecords WHERE doctor_id = ? ORDER BY appointment_id DESC";
        }
        return executeQuery(query, role.equals("Doctor") ? Collections.singletonList(uid) : Collections.emptyList());
    }

    private List<Map<String, Object>> getBilling(String role, String uid) throws SQLException {
        String query = "SELECT * FROM Billing ORDER BY billing_date DESC";
        if (role.equals("Doctor")) {
            query = "SELECT b.* FROM Billing b JOIN Appointments a ON b.appointment_id = a.appointment_id WHERE a.doctor_id = ? ORDER BY b.billing_date DESC";
        }
        return executeQuery(query, role.equals("Doctor") ? Collections.singletonList(uid) : Collections.emptyList());
    }

    private List<Map<String, Object>> executeQuery(String query, List<Object> params) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        row.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                    }
                    results.add(row);
                }
            }
        }
        return results;
    }
}
