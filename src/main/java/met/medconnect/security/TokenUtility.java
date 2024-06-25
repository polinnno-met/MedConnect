package met.medconnect.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtility {

    private static String secretKey;

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        TokenUtility.secretKey = secretKey;
    }

    public static String generateToken(String idToken, String staffId, String staffRole) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claim("idToken", idToken)
                .claim("staffId", staffId)
                .claim("staffRole", staffRole)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 86400000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
