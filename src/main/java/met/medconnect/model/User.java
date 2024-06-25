package met.medconnect.model;
public class User {
    private final String email;
    private final String staffId;
    private final String staffRole;

    public User(String email, String staffId, String staffRole) {
        this.email = email;
        this.staffId = staffId;
        this.staffRole = staffRole;
    }

    public String getEmail() {
        return email;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStaffRole() {
        return staffRole;
    }
}
