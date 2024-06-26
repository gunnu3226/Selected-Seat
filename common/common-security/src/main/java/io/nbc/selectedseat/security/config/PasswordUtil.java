package io.nbc.selectedseat.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordUtil {

    private final PasswordEncoder passwordEncoder;

    public PasswordUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(final String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matchPassword(
        final String password,
        final String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
