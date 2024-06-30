package met.medconnect.repo;

import met.medconnect.model.Appointment;
import met.medconnect.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a JOIN a.patient p JOIN a.doctor d WHERE d.staffId = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a JOIN a.patient p JOIN a.doctor d ORDER BY a.appointmentDate DESC")
    List<Appointment> findAllByOrderByAppointmentDateDesc();

    @Query("SELECT a FROM Appointment a WHERE a.doctor.staffId = :doctorId AND a.patient.patientId = :patientId")
    List<Appointment> findByDoctorIdAndPatientId(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId);

    @Query("SELECT a FROM Appointment a WHERE a NOT IN (SELECT mr.appointment FROM MedicalRecord mr)")
    List<Appointment> findAppointmentsWithoutMedicalRecords();

    @Query("SELECT a FROM Appointment a WHERE a.doctor.staffId = :doctorId AND a NOT IN (SELECT mr.appointment FROM MedicalRecord mr)")
    List<Appointment> findAppointmentsWithoutMedicalRecordsByDoctor(@Param("doctorId") Long doctorId);

    List<Appointment> findTop5ByOrderByAppointmentDateDesc();

    List<Appointment> findTop5ByDoctorStaffIdOrderByAppointmentDateDesc(@Param("doctorId") Long doctorId);
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.staffId = :doctorId AND a.appointmentDate BETWEEN :startOfDay AND :endOfDay AND a.status = :status")
    int countByDoctorStaffIdAndAppointmentDateAndStatus(@Param("doctorId") Long doctorId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay, @Param("status") AppointmentStatus status);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.appointmentDate BETWEEN :startOfDay AND :endOfDay AND a.status = :status")
    int countByAppointmentDateAndStatus(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay, @Param("status") AppointmentStatus status);

}
