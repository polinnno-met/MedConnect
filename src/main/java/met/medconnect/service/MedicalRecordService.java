package met.medconnect.service;

import met.medconnect.model.Appointment;
import met.medconnect.model.MedicalRecord;
import met.medconnect.model.User;
import met.medconnect.repo.AppointmentRepository;
import met.medconnect.repo.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<MedicalRecord> getAccessibleMedicalRecords(User user) {
        if (user.getStaffRole().equals("DOCTOR")) {
            return getRecordsForDoctor(user);
        } else {
            return medicalRecordRepository.findAll();
        }
    }

    private List<MedicalRecord> getRecordsForDoctor(User doctor) {
        List<Long> appointmentIds = appointmentRepository.findByDoctorId(doctor.getStaffId()).stream()
                .map(Appointment::getAppointmentId)
                .distinct()
                .collect(Collectors.toList());

        return medicalRecordRepository.findByAppointmentAppointmentIdIn(appointmentIds);
    }


    public boolean canAccessMedicalRecord(User user, MedicalRecord medicalRecord) {
        if (user.getStaffRole().equals("DOCTOR")) {
            return appointmentRepository.findByDoctorIdAndPatientId(user.getStaffId(), medicalRecord.getAppointment().getPatient().getPatientId()).size() > 0;
        }
        return true;
    }

    public List<MedicalRecord> getLatestAccessibleMedicalRecords(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return medicalRecordRepository.findTop3ByAppointmentDoctorOrderByAppointmentAppointmentDateDesc(user);
        } else {
            return medicalRecordRepository.findTop5ByOrderByAppointmentAppointmentDateDesc();
        }
    }


    public void deleteMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.delete(medicalRecord);
    }
    public MedicalRecord findById(Long id) {
        return medicalRecordRepository.findById(id).orElse(null);
    }
}
