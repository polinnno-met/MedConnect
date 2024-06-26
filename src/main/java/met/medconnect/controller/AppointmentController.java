package met.medconnect.controller;

import met.medconnect.model.Appointment;
import met.medconnect.model.User;
import met.medconnect.repo.AppointmentRepository;
import met.medconnect.repo.UserRepository;
import met.medconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAppointments(Authentication authentication, Model model) {
        String staffId = (String) authentication.getPrincipal();
        User user = userRepository.findById(Long.valueOf(staffId)).orElse(null);

        if (user == null) {
            return "error";
        }


        List<Appointment> appointments;
        if ("Doctor".equals(userService.getRoleFromAuthentication(authentication))) {
            appointments = appointmentRepository.findByDoctorId(Long.valueOf(staffId));
        } else {
            appointments = appointmentRepository.findAllByOrderByAppointmentDateDesc();
        }

        model.addAttribute("appointments", appointments);
        model.addAttribute("userInfo", user);

        return "appointments";
    }
}
