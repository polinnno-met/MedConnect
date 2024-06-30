package met.medconnect.service;

import met.medconnect.model.User;
import met.medconnect.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String getRoleFromAuthentication(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            return authorities.iterator().next().getAuthority().replace("ROLE_", "");
        }
        return null;
    }

    public User getCurrentUser(Authentication authentication) {
        String staffId = (String) authentication.getPrincipal();

        return userRepository.findById(staffId);
    }
}
