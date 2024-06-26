package met.medconnect.repo;

import met.medconnect.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientRepository {

    @Autowired
    private DataSource dataSource;

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patients";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setPatientId(resultSet.getInt("patient_id"));
                patient.setFirstName(resultSet.getString("patient_first_name"));
                patient.setLastName(resultSet.getString("patient_last_name"));
                patient.setDateOfBirth(resultSet.getDate("patient_date_of_birth"));
                patient.setGender(resultSet.getString("patient_gender"));
                patient.setPhoneNumber(resultSet.getString("patient_phone_number"));
                patient.setEmail(resultSet.getString("patient_email"));
                patient.setAddress(resultSet.getString("patient_address"));
                patient.setEmergencyContact(resultSet.getString("patient_emergency_contact"));
                patients.add(patient);
            }
        }
        return patients;
    }
}
