package met.medconnect.controller;

import jakarta.servlet.http.HttpServletResponse;
import met.medconnect.model.User;
import met.medconnect.repo.DashboardRepository;
import met.medconnect.repo.UserRepository;
import met.medconnect.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public String getDashboard(Authentication authentication, Model model) throws SQLException {
        System.out.println("We in getDashboard");

        String staffId = (String) authentication.getPrincipal();
        String role = userService.getRoleFromAuthentication(authentication);

        System.out.println("Dashboard user: " + staffId);

        // Fetch user info and add to the model
        User user = userRepository.findById(staffId);

        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("patients", dashboardRepository.getPatients(role, staffId));
        dashboardData.put("appointments", dashboardRepository.getAppointments(role, staffId));
        dashboardData.put("medicalRecords", dashboardRepository.getMedicalRecords(role, staffId));
        dashboardData.put("billing", dashboardRepository.getBilling(role, staffId));
        dashboardData.put("appointmentStatusCounts", dashboardRepository.getAppointmentStatusCounts(role, staffId));

        System.out.println("We seem to be good in the controller with user " + user.getFirstName());

        model.addAttribute("dashboardData", dashboardData);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Dashboard");
        return "dashboard";
    }




}
