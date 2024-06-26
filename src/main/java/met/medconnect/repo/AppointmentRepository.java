package met.medconnect.repo;

import met.medconnect.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a JOIN a.patient p JOIN a.doctor d WHERE d.staffId = :doctorId")
    List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT a FROM Appointment a JOIN a.patient p JOIN a.doctor d ORDER BY a.appointmentDate DESC")
    List<Appointment> findAllByOrderByAppointmentDateDesc();
}
