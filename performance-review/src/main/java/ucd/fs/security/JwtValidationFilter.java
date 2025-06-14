package ucd.fs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class JwtValidationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            try {
                jwt = jwt.substring(7);
                SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                if (claims.getExpiration() != null && claims.getExpiration().before(new Date())) {
                    throw new BadCredentialsException("JWT token expired");
                }

                String username = claims.get("username", String.class);
                String role = "ROLE_" + claims.get("role", String.class);

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(new SimpleGrantedAuthority(role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid JWT token");
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/graphql") ||
                request.getServletPath().equals("/graphiql");
    }
}