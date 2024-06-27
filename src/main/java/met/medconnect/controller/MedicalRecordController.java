package met.medconnect.controller;

import met.medconnect.model.MedicalRecord;
import met.medconnect.model.User;
import met.medconnect.repo.MedicalRecordRepository;
import met.medconnect.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getMedicalRecords(Authentication authentication, Model model) throws SQLException {
        String staffId = (String) authentication.getPrincipal();
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllOrderByAppointmentDateAsc();
        User user = userRepository.findById(staffId);

        model.addAttribute("medicalRecords", medicalRecords);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Medical Records");

        return "medical-records";
    }

    @GetMapping("/edit/{recordId}")
    public String editMedicalRecord(@PathVariable Long recordId, Model model) throws SQLException {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId).orElse(null);
        model.addAttribute("medicalRecord", medicalRecord);
        return "edit-record";
    }

    @GetMapping("/add/{recordId}")
    public String addMedicalRecord(@PathVariable Long recordId, Model model) {
        // Implement the logic for adding a medical record
        return "add-record";
    }

    @GetMapping("/change-status/{recordId}")
    public String changeStatus(@PathVariable Long recordId, Model model) {
        // Implement the logic for changing the status
        return "change-status";
    }

    @GetMapping("/reschedule/{recordId}")
    public String reschedule(@PathVariable Long recordId, Model model) {
        // Implement the logic for rescheduling
        return "reschedule";
    }
}
