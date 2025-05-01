package com.pintu.rideshare.config;

import com.pintu.rideshare.model.ERole;
import com.pintu.rideshare.model.Role;
import com.pintu.rideshare.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // 初始化角色数据
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(ERole.ROLE_USER));
            roleRepository.save(new Role(ERole.ROLE_DRIVER));
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }
} 