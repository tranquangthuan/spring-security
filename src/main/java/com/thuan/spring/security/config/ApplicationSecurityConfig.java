package com.thuan.spring.security.config;

import com.thuan.spring.security.auth.ApplicationUserService;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

import static com.thuan.spring.security.config.UserRole.STUDENT;

@Configuration
@EnableWebSecurity()
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final ApplicationUserService applicationUserService;


    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((auth) -> auth.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll().requestMatchers("/", "/index", "/static/**", "/css/**", "/js/**").permitAll().requestMatchers("/api/**").hasRole(STUDENT.name()).anyRequest().authenticated()).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/courses").permitAll()).rememberMe(rember -> rember.key("abckey").tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))).logout(logout -> logout.logoutUrl("/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID", "remember-me").logoutSuccessUrl("/login"));
        //.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public ProviderManager authManagerBean(AuthenticationProvider provider) {
        return new ProviderManager(provider);
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);

        return provider;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails anna = User.builder().username("anna")
//                .password(passwordEncoder.encode("123456"))
//                .roles(STUDENT.name())
//                .authorities(STUDENT.grantedAuthorities())
//                .build();
//
//        UserDetails linda = User.builder().username("linda")
//                .password(passwordEncoder.encode("123456"))
//                .roles(ADMIN.name())
//                .authorities(ADMIN.grantedAuthorities())
//                .build();
//
//        UserDetails tom = User.builder().username("tom")
//                .password(passwordEncoder.encode("123456"))
//                .roles(ADMIN_TRAINEE.name())
//                .authorities(ADMIN_TRAINEE.grantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(anna, linda, tom);
//    }
}
