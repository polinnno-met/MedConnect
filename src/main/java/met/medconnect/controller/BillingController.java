package met.medconnect.controller;

import met.medconnect.model.Bill;
import met.medconnect.repo.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BillingController {

    @Autowired
    private BillRepository billRepository;

    @GetMapping("/billing")
    public String getBills(Model model) {
        List<Bill> bills = billRepository.findAllOrderByAppointmentDateAsc();
        model.addAttribute("bills", bills);
        return "billing";
    }
}
