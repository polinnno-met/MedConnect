package met.medconnect.service;

import met.medconnect.model.Appointment;
import met.medconnect.model.AppointmentStatus;
import met.medconnect.model.User;
import met.medconnect.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAccessibleAppointments(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return appointmentRepository.findByDoctorId(user.getStaffId());
        } else {
            return appointmentRepository.findAll();
        }
    }

    public boolean canAccessAppointment(User user, Appointment appointment) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return appointment.getDoctor().equals(user);
        }
        return true;
    }

    public List<Appointment> getAccessibleAppointmentsWithoutMedicalRecords(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return appointmentRepository.findAppointmentsWithoutMedicalRecordsByDoctor(user.getStaffId());
        } else {
            return appointmentRepository.findAppointmentsWithoutMedicalRecords();
        }
    }

    public List<Appointment> getLatestAccessibleAppointments(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return appointmentRepository.findTop5ByDoctorStaffIdOrderByAppointmentDateDesc(user.getStaffId());
        } else {
            return appointmentRepository.findTop5ByOrderByAppointmentDateDesc();
        }
    }


    public Map<String, Integer> getAppointmentStatusCounts(User user) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime startOfDay = today.with(LocalTime.MIN);
        LocalDateTime endOfDay = today.with(LocalTime.MAX);

        Map<String, Integer> counts = new HashMap<>();
        counts.put(AppointmentStatus.SCHEDULED.name().toLowerCase(), 0);
        counts.put(AppointmentStatus.COMPLETED.name().toLowerCase(), 0);

        if ("DOCTOR".equals(user.getStaffRole())) {
            counts.put(AppointmentStatus.SCHEDULED.name().toLowerCase(),
                    appointmentRepository.countByDoctorStaffIdAndAppointmentDateAndStatus(user.getStaffId(), startOfDay, endOfDay, AppointmentStatus.SCHEDULED));
            counts.put(AppointmentStatus.COMPLETED.name().toLowerCase(),
                    appointmentRepository.countByDoctorStaffIdAndAppointmentDateAndStatus(user.getStaffId(), startOfDay, endOfDay, AppointmentStatus.COMPLETED));
        } else {
            counts.put(AppointmentStatus.SCHEDULED.name().toLowerCase(),
                    appointmentRepository.countByAppointmentDateAndStatus(startOfDay, endOfDay, AppointmentStatus.SCHEDULED));
            counts.put(AppointmentStatus.COMPLETED.name().toLowerCase(),
                    appointmentRepository.countByAppointmentDateAndStatus(startOfDay, endOfDay, AppointmentStatus.COMPLETED));
        }

        return counts;
    }


    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
}
