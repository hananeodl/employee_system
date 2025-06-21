package ucd.fs.gateway.security;

//@Configuration
//@EnableWebFluxSecurity
//public class GatewayConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/api/auth/**").permitAll()
//                        .pathMatchers("**").permitAll()
////                        .anyExchange().authenticated()
//                )
//                .build();
//    }
//}

//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("payroll-service", r -> r.path("/payroll/**")
//                        .uri("lb://PAYROLL-SERVICE"))
//                .route("absence-service", r -> r.path("/absence/**")
//                        .uri("lb://ABSENCE-MANAGEMENT"))
//                .build();
//    }
//}

