package met.medconnect.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PatientController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/patients")
    public String getPatients(@RequestHeader("Authorization") String idToken, Model model) throws FirebaseAuthException, SQLException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken.replace("Bearer ", ""));
        String uid = decodedToken.getUid();

        List<Map<String, Object>> patients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM Patients";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> patient = new HashMap<>();
                    patient.put("patientId", resultSet.getInt("patient_id"));
                    patient.put("firstName", resultSet.getString("patient_first_name"));
                    patient.put("lastName", resultSet.getString("patient_last_name"));
                    patient.put("dateOfBirth", resultSet.getDate("patient_date_of_birth"));
                    patient.put("gender", resultSet.getString("patient_gender"));
                    patient.put("phoneNumber", resultSet.getString("patient_phone_number"));
                    patient.put("email", resultSet.getString("patient_email"));
                    patient.put("address", resultSet.getString("patient_address"));
                    patient.put("emergencyContact", resultSet.getString("patient_emergency_contact"));
                    patients.add(patient);
                }
            }
        }

        model.addAttribute("patients", patients);
        return "patients";
    }
}
