package met.medconnect.controller;

import jakarta.servlet.http.HttpServletResponse;
import met.medconnect.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ModelAndView getDashboard(Authentication authentication) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        System.out.println("We in getDashboard");

        // Extract the staffId and role from the Authentication object
        String staffId = (String) authentication.getPrincipal();
        String role = getRoleFromAuthentication(authentication);

        System.out.println("Dashboard user: " + staffId);

        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("patients", getPatients(role, staffId));
        dashboardData.put("appointments", getAppointments(role, staffId));
        dashboardData.put("medicalRecords", getMedicalRecords(role, staffId));
        dashboardData.put("billing", getBilling(role, staffId));
        dashboardData.put("userInfo", getUserInfo(staffId));
        dashboardData.put("appointmentStatusCounts", getAppointmentStatusCounts(role, staffId));

        System.out.println("We seem to be good in the controller: Dashboard Data: " + dashboardData);

        modelAndView.addObject("dashboardData", dashboardData);
        return modelAndView;
    }

    private String getRoleFromAuthentication(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            return authorities.iterator().next().getAuthority().replace("ROLE_", "");
        }
        return null;
    }

    private Map<String, Integer> getAppointmentStatusCounts(String role, String staffId) throws SQLException {
        String query = "SELECT appointment_status, COUNT(*) as count FROM Appointments WHERE appointment_date >= CURDATE() AND appointment_date < CURDATE() + INTERVAL 1 DAY";
        if ("Doctor".equals(role)) {
            query += " AND appointment_doctor_id = ? GROUP BY appointment_status";
        } else {
            query += " GROUP BY appointment_status";
        }

        Map<String, Integer> counts = new HashMap<>();
        counts.put("scheduled", 0);
        counts.put("completed", 0);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if ("Doctor".equals(role)) {
                statement.setString(1, staffId);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String status = resultSet.getString("appointment_status").toLowerCase();
                    int count = resultSet.getInt("count");
                    counts.put(status, count);
                }
            }
        }
        return counts;
    }

    private List<Map<String, Object>> getPatients(String role, String staffId) throws SQLException {
        String query = "SELECT * FROM Patients";
        if ("Doctor".equals(role)) {
            query = "SELECT DISTINCT p.* FROM Patients p JOIN Appointments a ON p.patient_id = a.patient_id WHERE a.doctor_id = ?";
        }
        return executeQuery(query, "Doctor".equals(role) ? Collections.singletonList(staffId) : Collections.emptyList());
    }

    private List<Map<String, Object>> getAppointments(String role, String staffId) throws SQLException {
        String query = "SELECT a.appointment_id, a.appointment_date, a.appointment_status, " +
                "p.patient_first_name, p.patient_last_name, p.patient_id, " +
                "s.staff_first_name, s.staff_last_name " +
                "FROM Appointments a " +
                "JOIN Patients p ON a.appointment_patient_id = p.patient_id " +
                "JOIN Staff s ON a.appointment_doctor_id = s.staff_id " +
                "ORDER BY appointment_date DESC";
        return executeQuery(query, Collections.emptyList());
    }

    private List<Map<String, Object>> getMedicalRecords(String role, String staffId) throws SQLException {
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

    private List<Map<String, Object>> getBilling(String role, String staffId) throws SQLException {
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
                    results.add(row);
                }
            }
        }
        return results;
    }

    private Map<String, Object> getUserInfo(String staffId) throws SQLException {
        String query = "SELECT staff_first_name, staff_last_name, staff_role, staff_email FROM Staff WHERE staff_id = ?";
        Map<String, Object> userInfo = new HashMap<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staffId);
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
