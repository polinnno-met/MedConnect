package met.medconnect.repo;

import met.medconnect.model.MedicalRecord;
import met.medconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    @Query("SELECT mr FROM MedicalRecord mr " +
            "JOIN Appointment a " +
            "ON mr.appointment.appointmentId = a.appointmentId " +
            "ORDER BY a.appointmentDate DESC")
    List<MedicalRecord> findAllOrderByAppointmentDateAsc();

    List<MedicalRecord> findByAppointmentAppointmentIdIn(List<Long> appointmentIds);
    List<MedicalRecord> findTop5ByOrderByAppointmentAppointmentDateDesc();

    List<MedicalRecord> findTop3ByAppointmentDoctorOrderByAppointmentAppointmentDateDesc(@Param("doctor") User doctor);


}
