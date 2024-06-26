package met.medconnect.controller;

import met.medconnect.model.Patient;
import met.medconnect.model.User;
import met.medconnect.repo.PatientRepository;
import met.medconnect.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/patients")
    public String getPatients(Authentication authentication, Model model) throws SQLException {
        String staffId = (String) authentication.getPrincipal();
        List<Patient> patients = patientRepository.getAllPatients();
        User user = userRepository.findById(staffId);

        model.addAttribute("patients", patients);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Patients");

        return "patients";

    }
}
