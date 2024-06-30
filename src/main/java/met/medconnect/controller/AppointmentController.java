package met.medconnect.controller;

import met.medconnect.model.Appointment;
import met.medconnect.model.AppointmentStatus;
import met.medconnect.model.User;
import met.medconnect.model.Patient;

import met.medconnect.repo.AppointmentRepository;
import met.medconnect.repo.PatientRepository;
import met.medconnect.repo.UserRepository;
import met.medconnect.service.AppointmentService;
import met.medconnect.service.PatientService;
import met.medconnect.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @GetMapping
    public String getAppointments(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        List<Appointment> appointments = appointmentService.getAccessibleAppointments(user);
        List<String> appointmentStatuses = Arrays.stream(AppointmentStatus.values())
                .map(AppointmentStatus::name)
                .toList();

        model.addAttribute("appointments", appointments);
        model.addAttribute("appointmentStatuses", appointmentStatuses);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Appointments");

        return "appointments";
    }

    @GetMapping("/edit")
    public String editAppointment(@RequestParam(value = "appointmentId", required = false) Long appointmentId, Authentication authentication, Model model) {

        User user = userService.getCurrentUser(authentication);
        Appointment appointment = (appointmentId != null) ? appointmentRepository.findById(appointmentId).orElse(null) : null;

        if (appointment != null && !appointmentService.canAccessAppointment(user, appointment)) {
            return "redirect:/login";
        }

        List<Appointment> allAppointments = appointmentService.getAccessibleAppointments(user);
        List<String> appointmentStatuses = Arrays.stream(AppointmentStatus.values())
                .map(AppointmentStatus::name)
                .toList();

        List<Patient> accessiblePatients = (user.getStaffRole().equals("DOCTOR")) ? patientService.getAllAccessiblePatients(user) : patientRepository.findAll();

        if (!"DOCTOR".equals(user.getStaffRole())) {
            List<User> allDoctors = userRepository.findByStaffRole("DOCTOR");
            model.addAttribute("allDoctors", allDoctors);
        }

        model.addAttribute("allAppointments", allAppointments);
        model.addAttribute("appointmentStatuses", appointmentStatuses);
        model.addAttribute("accessiblePatients", accessiblePatients);
        model.addAttribute("appointment", appointment);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Edit Appointment");

        return "manage/manage-appointment";
    }

    @PostMapping("/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentRepository.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/change-status/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable String status, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        if (appointment != null && appointmentService.canAccessAppointment(user, appointment)) {
            try {
                AppointmentStatus appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
                appointment.setStatus(appointmentStatus);
                appointmentRepository.save(appointment);
            } catch (IllegalArgumentException e) {
                return "redirect:/appointments";
            }
        } else {
            return "redirect:/access-denied";
        }

        return "redirect:/appointments";
    }


    @GetMapping("/add")
    public String addAppointment(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication);
        Appointment appointment = new Appointment();
        List<Patient> accessiblePatients = (user.getStaffRole().equals("DOCTOR")) ? patientService.getAllAccessiblePatients(user) : patientRepository.findAll();


        if (!"DOCTOR".equals(user.getStaffRole())) {
            List<User> allDoctors = userRepository.findByStaffRole("DOCTOR");
            model.addAttribute("allDoctors", allDoctors);

        }

        List<String> appointmentStatuses = Arrays.stream(AppointmentStatus.values())
                .map(AppointmentStatus::name)
                .toList();

        model.addAttribute("appointment", appointment);
        model.addAttribute("appointmentStatuses", appointmentStatuses);

        model.addAttribute("accessiblePatients", accessiblePatients);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Add Appointment");

        return "manage/manage-appointment";
    }

    @GetMapping("/delete")
    public String deleteAppointment(@RequestParam(value = "appointmentId", required = false) Long appointmentId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser(authentication);
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);

        if (appointment != null && appointmentService.canAccessAppointment(user, appointment)) {
            appointmentService.deleteAppointment(appointment);
            redirectAttributes.addFlashAttribute("message", "Appointment deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to delete this appointment.");
        }

        return "redirect:/appointments";
    }

}
