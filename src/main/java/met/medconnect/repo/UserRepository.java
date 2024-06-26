package met.medconnect.repo;

import met.medconnect.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findByEmail(String email) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT staff_id, staff_role, staff_first_name, staff_last_name, staff_phone_number, staff_email, staff_address FROM Staff WHERE staff_email = ?")) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setStaffId(rs.getString("staff_id"));
                    user.setStaffRole(rs.getString("staff_role"));
                    user.setFirstName(rs.getString("staff_first_name"));
                    user.setLastName(rs.getString("staff_last_name"));
                    user.setPhoneNumber(rs.getString("staff_phone_number"));
                    user.setEmail(rs.getString("staff_email"));
                    user.setAddress(rs.getString("staff_address"));
                    return user;
                }
            }
        }
        return null;
    }

    public User findById(String staffId) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT staff_id, staff_role, staff_first_name, staff_last_name, staff_phone_number, staff_email, staff_address FROM Staff WHERE staff_id = ?")) {
            stmt.setString(1, staffId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setStaffId(rs.getString("staff_id"));
                    user.setStaffRole(rs.getString("staff_role"));
                    user.setFirstName(rs.getString("staff_first_name"));
                    user.setLastName(rs.getString("staff_last_name"));
                    user.setPhoneNumber(rs.getString("staff_phone_number"));
                    user.setEmail(rs.getString("staff_email"));
                    user.setAddress(rs.getString("staff_address"));
                    return user;
                }
            }
        }
        return null;
    }
}
