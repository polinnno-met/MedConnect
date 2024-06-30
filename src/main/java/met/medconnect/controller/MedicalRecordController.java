package met.medconnect.controller;

import met.medconnect.model.Appointment;
import met.medconnect.model.MedicalRecord;
import met.medconnect.model.User;
import met.medconnect.repo.AppointmentRepository;
import met.medconnect.repo.MedicalRecordRepository;
import met.medconnect.repo.UserRepository;
import met.medconnect.service.AppointmentService;
import met.medconnect.service.MedicalRecordService;
import met.medconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getMedicalRecords(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        List<MedicalRecord> medicalRecords = medicalRecordService.getAccessibleMedicalRecords(user);

        model.addAttribute("medicalRecords", medicalRecords);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Medical Records");

        return "medical-records";
    }

    @GetMapping("/edit")
    public String editMedicalRecord(@RequestParam(value = "recordId", required = false) Long recordId, Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        MedicalRecord medicalRecord = (recordId != null) ? medicalRecordRepository.findById(recordId).orElse(null) : null;

        if (medicalRecord != null && !medicalRecordService.canAccessMedicalRecord(user, medicalRecord)) {
            return "redirect:/access-denied";
        }

        List<MedicalRecord> allMedicalRecords = medicalRecordService.getAccessibleMedicalRecords(user);
        model.addAttribute("allMedicalRecords", allMedicalRecords);
        model.addAttribute("medicalRecord", medicalRecord);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Edit Medical Record");

        return "manage/manage-medical-record";
    }

    @PostMapping("/save")
    public String saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
        return "redirect:/medical-records";
    }

    @GetMapping("/add")
    public String addMedicalRecord(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        MedicalRecord medicalRecord = new MedicalRecord();
        List<Appointment> accessibleAppointments = appointmentService.getAccessibleAppointmentsWithoutMedicalRecords(user);

        if (!"DOCTOR".equals(user.getStaffRole())) {
            List<User> allDoctors = userRepository.findByStaffRole("DOCTOR");
            model.addAttribute("allDoctors", allDoctors);
        }

        model.addAttribute("medicalRecord", medicalRecord);
        model.addAttribute("accessibleAppointments", accessibleAppointments);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Add Medical Record");

        return "manage/manage-medical-record";
    }
    @GetMapping("/delete")
    public String deleteMedicalRecord(@RequestParam(value = "recordId", required = false) Long recordId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser(authentication);
        MedicalRecord medicalRecord = medicalRecordService.findById(recordId);

        if (medicalRecord != null && medicalRecordService.canAccessMedicalRecord(user, medicalRecord)) {
            medicalRecordService.deleteMedicalRecord(medicalRecord);
            redirectAttributes.addFlashAttribute("message", "Medical record deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to delete this medical record.");
        }

        return "redirect:/medical-records";
    }

}
