package ucd.fs.gateway.webSecurity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ServiceJwtUtil {

    private final String secret = "maCleSecreteTrèsLonguePourJWTQueTuDoisChangerEtMettreEnSecurite123!";

    public String generateServiceToken(String serviceName) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(serviceName)
                .claim("roles", List.of("ROLE_SERVICE"))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600_000))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }
}
