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

//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    public User getUserById(String id) {
//        return userRepository.findById(id);
//    }

    public String getRoleFromAuthentication(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            return authorities.iterator().next().getAuthority().replace("ROLE_", "");
        }
        return null;
    }
}
