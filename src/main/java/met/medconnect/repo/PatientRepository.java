package met.medconnect.repo;

import met.medconnect.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT DISTINCT p FROM Patient p JOIN Appointment a ON p.patientId = a.patient.patientId WHERE a.doctor.staffId = :doctorId")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT p FROM Patient p WHERE p.patientId IN (SELECT a.patient.patientId FROM Appointment a GROUP BY a.patient.patientId ORDER BY MAX(a.appointmentDate) DESC)")
    List<Patient> findTop5ByLatestAppointment();

    @Query("SELECT p FROM Patient p WHERE p.patientId IN (SELECT a.patient.patientId FROM Appointment a WHERE a.doctor.staffId = :doctorId GROUP BY a.patient.patientId ORDER BY MAX(a.appointmentDate) DESC)")
    List<Patient> findTop5ByDoctorIdAndLatestAppointment(@Param("doctorId") Long doctorId);
}
