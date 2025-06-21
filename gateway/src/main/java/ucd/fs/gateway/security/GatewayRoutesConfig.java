package ucd.fs.gateway.security;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class GatewayRoutesConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthenticationFilter jwtFilter) {
//        return builder.routes()
//                .route("payroll-service", r -> r.path("/api/payroll/**")
//                        .filters(f -> f.filter(jwtFilter.apply(new JwtAuthenticationFilter.Config())))
//                        .uri("lb://payroll-service"))
//                .route("absence-management", r -> r.path("/api/absence/**")
//                        .filters(f -> f.filter(jwtFilter.apply(new JwtAuthenticationFilter.Config())))
//                        .uri("lb://absence-management"))
//                .route("employee-profile", r -> r.path("/api/auth/**")
//                        .filters(f -> f.rewritePath("/api/auth/(?<segment>.*)", "/app/${segment}"))
//                        .uri("lb://employee-profile"))
//                .build();
//    }
//}
