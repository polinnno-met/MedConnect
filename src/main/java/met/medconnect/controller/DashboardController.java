package met.medconnect.controller;

import met.medconnect.model.User;

import met.medconnect.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getDashboard(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);

        model.addAttribute("latestPatients", patientService.getLatestAccessiblePatients(user));
        model.addAttribute("latestAppointments", appointmentService.getLatestAccessibleAppointments(user));
        model.addAttribute("latestMedicalRecords", medicalRecordService.getLatestAccessibleMedicalRecords(user));
        model.addAttribute("latestBills", billService.getLatestAccessibleBills(user));
        model.addAttribute("appointmentStatusCounts", appointmentService.getAppointmentStatusCounts(user));

        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Dashboard");

        return "dashboard";
    }
}

