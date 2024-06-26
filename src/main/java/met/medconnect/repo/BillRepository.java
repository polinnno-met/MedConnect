package met.medconnect.repo;

import met.medconnect.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT b FROM Bill b " +
            "JOIN b.appointment a " +
            "ORDER BY a.appointmentDate ASC")
    List<Bill> findAllOrderByAppointmentDateAsc();
}
