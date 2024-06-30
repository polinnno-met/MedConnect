package met.medconnect.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import met.medconnect.model.Bill;
import met.medconnect.model.BillStatus;
import met.medconnect.model.User;
import met.medconnect.repo.BillRepository;
import met.medconnect.service.BillService;
import met.medconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getBillingRecords(Authentication authentication, Model model) throws JsonProcessingException {
        User user = userService.getCurrentUser(authentication);
        List<Bill> bills = billService.getAccessibleBills(user);
        List<String> billStatuses = Arrays.stream(BillStatus.values())
                .map(BillStatus::name)
                .toList();

        Map<String, Long> billStatusCounts = billService.getBillStatusCounts();
        ObjectMapper objectMapper = new ObjectMapper();
        String billStatusCountsJson = objectMapper.writeValueAsString(billStatusCounts);

        model.addAttribute("billStatusCountsJson", billStatusCountsJson);
        model.addAttribute("billStatusCounts", billStatusCounts);
        model.addAttribute("bills", bills);
        model.addAttribute("billStatuses", billStatuses);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Billing Records");

        return "billing";
    }

    @GetMapping("/edit")
    public String editBill(@RequestParam(value = "billId", required = false) Long billId, Authentication authentication, Model model) {

        User user = userService.getCurrentUser(authentication);
        Bill bill = (billId != null) ? billRepository.findById(billId).orElse(null) : null;

        if (bill != null && !billService.canAccessBill(user, bill)) {
            return "redirect:/login";
        }

        List<Bill> allBills = billService.getAccessibleBills(user);

        model.addAttribute("allBills", allBills);
        model.addAttribute("bill", bill);
        model.addAttribute("userInfo", user);
        model.addAttribute("pageName", "Edit Bill");

        return "manage/manage-bill";
    }

    @PostMapping("/save")
    public String saveBill(@ModelAttribute Bill bill) {
        billRepository.save(bill);
        return "redirect:/billing";
    }

    @GetMapping("/add/{id}")
    public String addBill(@PathVariable Long id, Authentication authentication, Model model) {
        Bill bill = new Bill();
        bill.setBillId(id);
        User user = userService.getCurrentUser(authentication);

        model.addAttribute("bill", bill);
        model.addAttribute("userInfo", user);
        return "add-bill";
    }

    @GetMapping("/change-status/{id}/{status}")
    public String changeStatus(@PathVariable Long id, @PathVariable String status, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        Bill bill = billRepository.findById(id).orElse(null);

        if (bill != null && billService.canAccessBill(user, bill)) {
            try {
                BillStatus billStatus = BillStatus.valueOf(status.toUpperCase());
                bill.setStatus(billStatus);
                billRepository.save(bill);
            } catch (IllegalArgumentException e) {
                return "redirect:/billing";
            }
        } else {
            return "redirect:/access-denied";
        }

        return "redirect:/billing";
    }


    @GetMapping("/delete")
    public String deleteBill(@RequestParam(value = "billId", required = false) Long billId, Authentication authentication, RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser(authentication);
        Bill bill = billRepository.findById(billId).orElse(null);

        if (bill != null && billService.canAccessBill(user, bill)) {
            billService.deleteBill(bill);
            redirectAttributes.addFlashAttribute("message", "Bill deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to delete this bill.");
        }

        return "redirect:/billing";
    }
}
