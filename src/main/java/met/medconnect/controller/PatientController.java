package met.medconnect.controller;

import met.medconnect.model.Patient;
import met.medconnect.model.User;
import met.medconnect.repo.PatientRepository;
import met.medconnect.repo.UserRepository;
import met.medconnect.service.PatientService;
import met.medconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getPatients(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        List<Patient> patients = patientService.getAllAccessiblePatients(user);

        model.addAttribute("patients", patients);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Patients");

        return "patients";
    }

    @GetMapping("/edit")
    public String editPatient(@RequestParam(value = "patientId", required = false) Long patientId, Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        Patient patient = (patientId != null) ?
                patientRepository.findById(patientId).orElse(null) :
                null;

        if (patient != null && !patientService.canAccessPatient(user, patient)) {
            return "redirect:/access-denied";
        }

        List<Patient> allPatients = patientService.getAllAccessiblePatients(user);
        model.addAttribute("allPatients", allPatients);
        model.addAttribute("patient", patient);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Edit Patient");

        return "manage/manage-patient";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        if (!patientService.canAccessPatient(user, patient)) {
            return "redirect:/access-denied";
        }
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    @GetMapping("/add")
    public String addPatient(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        Patient patient = new Patient();

        List<Patient> allPatients = patientService.getAllAccessiblePatients(user);
        model.addAttribute("allPatients", allPatients);
        model.addAttribute("patient", patient);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Add Patient");

        return "manage/manage-patient";
    }

    @GetMapping("/delete")
    public String deletePatient(@RequestParam("patientId") Long patientId, Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);

        Patient patient = patientRepository.findById(patientId).orElse(null);
        if (patient != null && patientService.canAccessPatient(user, patient)) {
            patientService.deletePatient(patientId);
        } else {
            model.addAttribute("errorMessage", "You do not have permission to delete this patient.");
            return "redirect:/patients";
        }

        return "redirect:/patients";
    }
}
