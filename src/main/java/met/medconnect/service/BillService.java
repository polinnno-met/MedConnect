package met.medconnect.service;

import met.medconnect.model.Appointment;
import met.medconnect.model.Bill;
import met.medconnect.model.User;
import met.medconnect.repo.AppointmentRepository;
import met.medconnect.repo.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Bill> getAccessibleBills(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            List<Long> accessibleAppointmentIds = appointmentRepository.findByDoctorId(user.getStaffId())
                    .stream()
                    .map(Appointment::getAppointmentId)
                    .toList();

            return billRepository.findAll().stream()
                    .filter(bill -> accessibleAppointmentIds.contains(bill.getAppointment().getAppointmentId()))
                    .collect(Collectors.toList());
        } else {
            return billRepository.findAll();
        }
    }


    public boolean canAccessBill(User user, Bill bill) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return appointmentRepository.findByDoctorId(user.getStaffId())
                    .stream()
                    .anyMatch(appointment -> appointment.getAppointmentId().equals(bill.getAppointment().getAppointmentId()));
        } else {
            return true;
        }
    }

    public List<Bill> getLatestAccessibleBills(User user) {
        if ("DOCTOR".equals(user.getStaffRole())) {
            return billRepository.findTop5ByAppointmentDoctorStaffIdOrderByDateDesc(user.getStaffId());
        } else {
            return billRepository.findTop5ByOrderByDateDesc();
        }
    }

    public Map<String, Long> getBillStatusCounts() {
        List<Object[]> results = billRepository.countBillsByStatus();
        Map<String, Long> counts = new HashMap<>();

        for (Object[] result : results) {
            String status = result[0].toString();
            Long count = (Long) result[1];
            counts.put(status, count);
        }

        return counts;
    }

    public void deleteBill(Bill bill) {
        billRepository.delete(bill);
    }
}
