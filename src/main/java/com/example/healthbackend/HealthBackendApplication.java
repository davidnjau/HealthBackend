package com.example.healthbackend;

import com.example.healthbackend.authentication.entity.Role;
import com.example.healthbackend.authentication.service_class.impl.StaffDetailsServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HealthBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(HealthBackendApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(StaffDetailsServiceImpl staffDetailsService){
        return args -> {
            staffDetailsService.saveRoles(new Role( "ROLE_ADMIN"));
            staffDetailsService.saveRoles(new Role( "ROLE_USER"));
        };
    }


    @Override
    public void run(String... args) throws Exception {

    }

}
