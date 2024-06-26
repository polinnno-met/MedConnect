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

import java.sql.SQLException;
import java.util.List;

@Controller
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/medical-records")
    public String getMedicalRecords(Authentication authentication, Model model) throws SQLException {
        String staffId = (String) authentication.getPrincipal();
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllOrderByAppointmentDateAsc();
        User user = userRepository.findById(staffId);

        model.addAttribute("medicalRecords", medicalRecords);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Medical Records");

        return "medical-records";
    }
}
