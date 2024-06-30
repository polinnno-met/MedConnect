package met.medconnect.repo;

import met.medconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    List<User> findByStaffRole(String staffRole);

    @Query("SELECT u FROM User u WHERE u.staffId = :staffId")
    User findById(@Param("staffId") String staffId);
}
