//package met.medconnect.security;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.auth.FirebaseToken;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class FirebaseAuthenticationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String idToken = null;
//
//        System.out.println("we are in filter for: " + request.getRequestURI());
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("AuthToken".equals(cookie.getName())) {
//                    idToken = cookie.getValue();
//                    break;
//                }
//            }
//        }
//
//        System.out.println("idToken from the filter after cookie extraction: " + idToken);
//
//        if (idToken != null) {
//            try {
//                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//                Authentication authResult = new FirebaseAuthenticationToken(decodedToken);
//                SecurityContextHolder.getContext().setAuthentication(authResult);
//            } catch (FirebaseAuthException e) {
//                SecurityContextHolder.clearContext();
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//                return;
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//}
