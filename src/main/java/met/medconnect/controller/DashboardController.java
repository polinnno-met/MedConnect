package met.medconnect.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping
    public Map<String, Object> getDashboardData(@RequestHeader("Authorization") String idToken) throws FirebaseAuthException, SQLException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken.replace("Bearer ", ""));
        String uid = decodedToken.getUid();

        String role = getUserRoleByUid(uid); // Implement this method to get user role from your database

        System.out.println("Dashboard user: " + uid);


        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("patients", getPatients(role, uid));
        dashboardData.put("appointments", getAppointments(role, uid));
        dashboardData.put("medicalRecords", getMedicalRecords(role, uid));
        dashboardData.put("billing", getBilling(role, uid));
        dashboardData.put("userInfo", getUserInfo(uid)); // Add user info to the response


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
        String query = "SELECT a.appointment_id, a.appointment_date, a.appointment_status, " +
                "p.patient_first_name, p.patient_last_name, p.patient_id, " +
                "s.staff_first_name, s.staff_last_name " +
                "FROM Appointments a " +
                "JOIN Patients p ON a.appointment_patient_id = p.patient_id " +
                "JOIN Staff s ON a.appointment_doctor_id = s.staff_id " +
                "ORDER BY appointment_date DESC";
        return executeQuery(query, Collections.emptyList());
    }

    private List<Map<String, Object>> getMedicalRecords(String role, String uid) throws SQLException {
        String query = "SELECT m.record_id, m.record_diagnosis, a.appointment_date, " +
                "p.patient_first_name, p.patient_last_name, p.patient_id, " +
                "s.staff_first_name, s.staff_last_name " +
                "FROM MedicalRecords m " +
                "JOIN Patients p ON m.record_patient_id = p.patient_id " +
                "JOIN Staff s ON m.record_doctor_id = s.staff_id " +
                "JOIN Appointments a ON m.record_appointment_id = a.appointment_id " +
                "ORDER BY m.record_appointment_id DESC";
        return executeQuery(query, Collections.emptyList());
    }

    private List<Map<String, Object>> getBilling(String role, String uid) throws SQLException {
        String query = "SELECT b.bill_id, b.bill_amount, b.bill_status, " +
                "a.appointment_date, " +
                "p.patient_first_name, p.patient_last_name, p.patient_id " +
                "FROM Billing b " +
                "JOIN Appointments a ON b.bill_appointment_id = a.appointment_id " +
                "JOIN Patients p ON a.appointment_patient_id = p.patient_id " +
                "ORDER BY bill_date DESC";
        return executeQuery(query, Collections.emptyList());
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
//                    System.out.println(row);
                    results.add(row);
                }
            }
        }
        return results;
    }

    private Map<String, Object> getUserInfo(String uid) throws SQLException {
        String query = "SELECT staff_first_name, staff_last_name, staff_role, staff_email FROM Staff WHERE staff_id = ?";
        Map<String, Object> userInfo = new HashMap<>();

        System.out.println(uid);
        System.out.println(uid);
        System.out.println(uid);
        System.out.println(uid);
        System.out.println(uid);
        System.out.println(uid);

        System.out.println(uid);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userInfo.put("firstName", resultSet.getString("staff_first_name"));
                    userInfo.put("lastName", resultSet.getString("staff_last_name"));
                    userInfo.put("role", resultSet.getString("staff_role"));
                    userInfo.put("email", resultSet.getString("staff_email"));
                }
            }
        }

        logger.info("User Info: {}", userInfo);
        return userInfo;
    }

}