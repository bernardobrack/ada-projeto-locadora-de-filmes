package com.ada.group3.locadoradefilmes.security;

import com.ada.group3.locadoradefilmes.login.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataBaseAuthConfig {
@Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, AuthService authService){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(authService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;

}
}
