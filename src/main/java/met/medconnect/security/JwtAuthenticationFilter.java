package met.medconnect.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static String secretKey;

    @Value("${jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtAuthenticationFilter.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = extractTokenFromCookies(request);

        if (jwt != null && !jwt.isEmpty()) {
            try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt);
                String idToken = claims.getBody().get("idToken", String.class);
                String staffId = claims.getBody().get("staffId", String.class);
                String staffRole = claims.getBody().get("staffRole", String.class);

                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

                GrantedAuthority authority = (GrantedAuthority) () -> "ROLE_" + staffRole.toUpperCase();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(staffId, null, Collections.singletonList(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (FirebaseAuthException e) {
                SecurityContextHolder.clearContext();
                clearAuthTokenCookie(response);

//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase token");
                response.sendRedirect("/login");

                return;
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                clearAuthTokenCookie(response);

//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                response.sendRedirect("/login");

                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("AuthToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    private void clearAuthTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("AuthToken", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
