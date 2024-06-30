package met.medconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class User {

    @Id
    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "staff_first_name")
    private String firstName;

    @Column(name = "staff_last_name")
    private String lastName;

    @Column(name = "staff_role")
    private String staffRole;

    @Column(name = "staff_phone_number")
    private String phoneNumber;

    @Column(name = "staff_email")
    private String email;

    @Column(name = "staff_address")
    private String address;


    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStaffRole() {
        return staffRole.toUpperCase();
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
