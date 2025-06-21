package ucd.fs.gateway.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

import java.util.Collections;

import static ucd.fs.gateway.security.SecurityConstants.JWT_KEY;

//@Component
//public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
//
//    private final Algorithm algorithm;
//
//    public JwtAuthenticationFilter(Algorithm algorithm) {
//        super(Config.class);
//        this.algorithm = algorithm;
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            String token = exchange.getRequest()
//                    .getHeaders()
//                    .getFirst(HttpHeaders.AUTHORIZATION);
//
//            if (token == null || !token.startsWith("Bearer ")) {
//                return unauthorizedResponse(exchange, "Missing authorization token");
//            }
//
//            try {
//                DecodedJWT jwt = JWT.require(algorithm)
//                        .build()
//                        .verify(token.substring(7));
//
//                // Ajoutez les informations utilisateur aux headers
//                exchange = exchange.mutate()
//                        .request(exchange.getRequest().mutate()
//                                .header("Authorization", token)
//                                .header("X-User-Id", jwt.getSubject())
//                                .header("X-User-Roles", jwt.getClaim("roles").asString())
//                                .build())
//                        .build();
//
//                return chain.filter(exchange);
//            } catch (JWTVerificationException e) {
//                return unauthorizedResponse(exchange, "Invalid token: " + e.getMessage());
//            }
//        };
//    }
//
//    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        return exchange.getResponse().writeWith(
//                Mono.just(exchange.getResponse()
//                        .bufferFactory()
//                        .wrap(("{\"error\":\"" + message + "\"}").getBytes())));
//    }
//
//    public static class Config {}
//}

//@Component
//public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
//
//    private final Algorithm algorithm;
//
//    public JwtAuthenticationFilter(@Value("${jwt.secret}") String secret) {
//        super(Config.class);
//        this.algorithm = Algorithm.HMAC256(secret);
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            String token = exchange.getRequest()
//                    .getHeaders()
//                    .getFirst(HttpHeaders.AUTHORIZATION);
//
////            if (token == null || !token.startsWith("Bearer ")) {
////                return unauthorized(exchange, "Token manquant");
////            }
//            if (token != null) {
//                // Transmet le token original au service downstream
//                System.out.println(token);
//                exchange = exchange.mutate()
//                        .request(exchange.getRequest()
//                                .mutate()
//                                .header(HttpHeaders.AUTHORIZATION, token)
//                                .build())
//                        .build();
//            }
//
//            try {
//                DecodedJWT jwt = JWT.require(algorithm)
//                        .build()
//                        .verify(token.substring(7));
//
//                // Extraction critique du rôle
//                String role = jwt.getClaim("role").asString();
////
////                // Transmet le token aux services downstream
////                exchange.getRequest().mutate()
////                        .header("X-User-Id", jwt.getSubject())
////                        .header("X-User-Role", jwt.getClaim("role").asString());
////
////                return chain.filter(exchange);
////            } catch (Exception e) {
////                return unauthorized(exchange, "Token invalide");
////            }
//            // Reconstruction des headers avec les claims ESSENTIELLES
//            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                    .header("X-User-Id", jwt.getSubject())
//                    .header("X-User-Role", role) // <-- Transmission explicite du rôle
//                    .header("Authorization", "Bearer " + jwt.getToken()) // Token original
//                    .build();
//
//            return chain.filter(exchange.mutate().request(modifiedRequest).build());
//
//        } catch (JWTVerificationException e) {
//            return unauthorized(exchange, "Token invalide: " + e.getMessage());
//        }
//    };
//}
//
//    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        return exchange.getResponse().writeWith(
//                Mono.just(exchange.getResponse()
//                        .bufferFactory()
//                        .wrap(("{\"error\":\"" + message + "\"}").getBytes())));
//    }
//
//    public static class Config {}
//}


//@Component
//public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//    private final SecretKey secretKey;
//
//    public JwtAuthenticationFilter() {
//        super(Config.class);
//        this.secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            String authHeader = exchange.getRequest()
//                    .getHeaders()
//                    .getFirst(HttpHeaders.AUTHORIZATION);
//
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return unauthorized(exchange, "Authorization header missing");
//            }
//
//            try {
//                String token = authHeader.substring(7);
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(secretKey)
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                        .header("X-User-Id", claims.getSubject())
//                        .header("X-User-Role", claims.get("role", String.class))
//                        .header(HttpHeaders.AUTHORIZATION, authHeader) // Garde le header original
//                        .build();
//
//                logger.debug("=== HEADERS TO DOWNSTREAM ===");
//                modifiedRequest.getHeaders().forEach((k, v) ->
//                        logger.debug("{}: {}", k, v));
//
//                return chain.filter(exchange.mutate().request(modifiedRequest).build());
//
//            } catch (Exception e) {
//                logger.error("JWT validation failed", e);
//                return unauthorized(exchange, "Invalid token: " + e.getMessage());
//            }
//        };
//    }
//
//    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
//        logger.error("Unauthorized: {}", message);
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//        return exchange.getResponse().writeWith(
//                Mono.just(exchange.getResponse()
//                        .bufferFactory()
//                        .wrap(("{\"error\":\"" + message + "\"}").getBytes())));
//    }
//
//    public static class Config {}
//}

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final SecretKey secretKey;

    public JwtAuthenticationFilter() {
        super(Config.class);
        this.secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return unauthorized(exchange, "Authorization header missing or invalid");
            }

            try {
                String token = authHeader.substring(7);
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                String role = claims.get("role", String.class);

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.singletonList(new SimpleGrantedAuthority(role))
                );

                SecurityContext context = new SecurityContextImpl(auth);

                // Ajouter le SecurityContext pour que Spring Security le reconnaisse
                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));

            } catch (Exception e) {
                return unauthorized(exchange, "Invalid token: " + e.getMessage());
            }
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(("{\"error\":\"" + message + "\"}").getBytes()))
        );
    }

    public static class Config {}
}

