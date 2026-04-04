package net.engineeringdigest.project.journalApp.Config;
import net.engineeringdigest.project.journalApp.Filter.JwtAuthFilter;
import net.engineeringdigest.project.journalApp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthFilter jwtAuthFilter; // ✅ add karo upar

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registerA/**", "/register/**", "/login/**").permitAll()
                .antMatchers("/User/**").hasAnyRole("USER")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic().disable()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter .class); // ✅ JWT on

        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(8));
        //  provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
//    @Configuration
//    public class WebConfig {
//        @Bean
//        public WebMvcConfigurer corsConfigurer() {
//            return new WebMvcConfigurer() {
//                @Override
//                public void addCorsMappings(CorsRegistry registry) {
//                    registry.addMapping("/**")
//                            .allowedOrigins("http://localhost:3000") // Frontend URL
//                            .allowedMethods("GET", "POST", "PUT", "DELETE")
//                            .allowedHeaders("*")
//                            .allowCredentials(true);
//                }
//
//            };
//        }
//    }
    @Configuration
    public class CorsConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:3000")
                            .allowedMethods("*")
                    .allowedHeaders("*");
                }
            };
        }
    }
}