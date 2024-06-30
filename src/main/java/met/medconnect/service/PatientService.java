package met.medconnect.service;

import met.medconnect.model.Patient;
import met.medconnect.model.User;
import met.medconnect.repo.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllAccessiblePatients(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return patientRepository.findPatientsByDoctorId(user.getStaffId());
        } else {
            return patientRepository.findAll();
        }
    }

    public boolean canAccessPatient(User user, Patient patient) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return patientRepository.findPatientsByDoctorId(user.getStaffId())
                    .stream()
                    .anyMatch(p -> p.getPatientId().equals(patient.getPatientId()));
        } else {
            return true;
        }
    }


    public List<Patient> getLatestAccessiblePatients(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return patientRepository.findTop5ByDoctorIdAndLatestAppointment(user.getStaffId());
        } else {
            return patientRepository.findTop5ByLatestAppointment();
        }
    }
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void deletePatient(Long patientId) { patientRepository.deleteById(patientId); }
}
