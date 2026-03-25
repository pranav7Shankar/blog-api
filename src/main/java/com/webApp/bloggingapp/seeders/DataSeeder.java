package com.webApp.bloggingapp.seeders;

import com.webApp.bloggingapp.entities.Role;
import com.webApp.bloggingapp.entities.User;
import com.webApp.bloggingapp.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepo.count() == 0) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAbout("System Administrator");
            admin.setRole(Role.ROLE_ADMIN);
            userRepo.save(admin);
            System.out.println("✅ Admin user created: admin@gmail.com / admin123");
        }
    }
}