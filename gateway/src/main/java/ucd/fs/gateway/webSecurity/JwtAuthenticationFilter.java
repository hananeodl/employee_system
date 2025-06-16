package ucd.fs.gateway.webSecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private final Algorithm algorithm;

    public JwtAuthenticationFilter(Algorithm algorithm) {
        super(Config.class);
        this.algorithm = algorithm;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith("Bearer ")) {
                return unauthorizedResponse(exchange, "Missing authorization token");
            }

            try {
                DecodedJWT jwt = JWT.require(algorithm)
                        .build()
                        .verify(token.substring(7));

                // Ajoutez les informations utilisateur aux headers
                exchange = exchange.mutate()
                        .request(exchange.getRequest().mutate()
                                .header("Authorization", token)
                                .header("X-User-Id", jwt.getSubject())
                                .header("X-User-Roles", jwt.getClaim("roles").asString())
                                .build())
                        .build();

                return chain.filter(exchange);
            } catch (JWTVerificationException e) {
                return unauthorizedResponse(exchange, "Invalid token: " + e.getMessage());
            }
        };
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(("{\"error\":\"" + message + "\"}").getBytes())));
    }

    public static class Config {}
}