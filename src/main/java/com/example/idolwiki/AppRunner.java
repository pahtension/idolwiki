package com.example.idolwiki;

import com.example.idolwiki.model.auth.Admin;
import com.example.idolwiki.model.auth.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String adminId = "admin";
        Admin admin = authMapper.findById(adminId);

        if (admin == null) {
            LocalDateTime now = LocalDateTime.now();
            authMapper.createAdmin(adminId, passwordEncoder.encode("1234"), now);
        }
    }
}
