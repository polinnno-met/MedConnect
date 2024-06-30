package met.medconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicalrecords")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "record_appointment_id")
    private Appointment appointment;

    @Column(name = "record_diagnosis")
    private String diagnosis;

    @Column(name = "record_treatment")
    private String treatment;

    @Column(name = "record_prescription")
    private String prescription;

    @Column(name = "record_notes")
    private String notes;


    public Long getRecordId() {
        return recordId;
    }
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
