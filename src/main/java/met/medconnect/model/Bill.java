package met.medconnect.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "billing")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "bill_appointment_id")
    private Appointment appointment;

    @Column(name = "bill_amount")
    private Double amount;

    @Column(name = "bill_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;


    @Enumerated(EnumType.STRING)
    @Column(name = "bill_status")
    private BillStatus status;


    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public String getStatusDisplay() {
        if (this.status == BillStatus.NA) {
            return "N/A";
        }
        return this.status.toString();
    }
}


