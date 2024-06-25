package met.medconnect.security;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private final FirebaseToken firebaseToken;

    public FirebaseAuthenticationToken(FirebaseToken firebaseToken) {
        super(Collections.emptyList());
        this.firebaseToken = firebaseToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.firebaseToken;
    }

    public FirebaseToken getFirebaseToken() {
        return firebaseToken;
    }
}
