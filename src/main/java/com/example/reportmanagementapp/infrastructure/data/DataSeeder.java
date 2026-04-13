package com.example.reportmanagementapp.infrastructure.data;

import com.example.reportmanagementapp.domain.entity.Evidence;
import com.example.reportmanagementapp.domain.entity.Role;
import com.example.reportmanagementapp.domain.entity.User;
import com.example.reportmanagementapp.domain.repository.EvidenceRepository;
import com.example.reportmanagementapp.domain.repository.RoleRepository;
import com.example.reportmanagementapp.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EvidenceRepository evidenceRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Data already seeded
        }

        // 1. Create Roles
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = createRoleIfNotFound("ROLE_USER");

        // 2. Create Admin
        User admin = User.builder()
                .name("Admin User")
                .email("admin@example.com")
                .password(passwordEncoder.encode("pass123"))
                .roles(Collections.singletonList(adminRole))
                .build();
        userRepository.save(admin);

        // 3. Create Regular Users
        User user1 = createUser("User One", "user1@example.com", "pass123", userRole);
        User user2 = createUser("User Two", "user2@example.com", "pass123", userRole);

        // 4. Seed Evidences for User 1
        seedEvidences(user1, "Mobile App", "Department A");
        
        // 5. Seed Evidences for User 2
        seedEvidences(user2, "Web Portal", "Department B");
    }

    private Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role = roleRepository.save(role);
        }
        return role;
    }

    private User createUser(String name, String email, String password, Role role) {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList(role))
                .build();
        return userRepository.save(user);
    }

    private void seedEvidences(User user, String projectName, String dept) {
        for (int i = 1; i <= 3; i++) {
            Evidence evidence = Evidence.builder()
                    .taskName("Task " + i + " for " + user.getName())
                    .project(projectName)
                    .orderNum("ORD-" + user.getId() + "-" + i)
                    .department(dept)
                    .estTime(10.0)
                    .timeSpent(2.0 * i)
                    .charge(50.0)
                    .total(2.0 * i * 50.0)
                    .state(i == 3 ? "DONE" : "IN_PROGRESS")
                    .month("April")
                    .user(user)
                    .build();
            evidenceRepository.save(evidence);
        }
    }
}
