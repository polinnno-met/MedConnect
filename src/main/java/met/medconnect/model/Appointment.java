package met.medconnect.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "appointment_patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_doctor_id")
    private User doctor;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @Column(name = "appointment_reason")
    private String reason;

    @Column(name = "appointment_status")
    private String status;

    // Getters and Setters


    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String appointmentReason) {
        this.reason = appointmentReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String appointmentStatus) {
        this.status = appointmentStatus;
    }
}
