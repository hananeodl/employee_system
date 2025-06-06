//package ucd.fs.absencemanagement.webSecurity;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//
//    @Configuration
//    @EnableWebSecurity
//    public class SecurityConfig {
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            return http
//                    .csrf(csrf -> csrf.disable())
//                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .authorizeHttpRequests(auth -> auth
//                            .requestMatchers("/auth/**").permitAll()
//                            .anyRequest().authenticated()
//                    )
//                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                    .build();
//        }
//
//        @Bean
//        public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//            return http.getSharedObject(AuthenticationManagerBuilder.class)
//                    .build();
//        }
//
//        @Bean
//        public JwtAuthenticationFilter jwtAuthenticationFilter() {
//            return new JwtAuthenticationFilter(); // À implémenter
//        }
//    }
//
//
