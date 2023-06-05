package com.store.management.store_management;

import com.store.management.store_management.model.User;
import com.store.management.store_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class StoreManagementApplication implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(StoreManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * create a default user
         * in order to access endpoints
         * once the application starts
         */
        var userAdmin = User.builder()
                .username("testUserAdmin")
                .password(passwordEncoder.encode("test@1234"))
                .authority("ADMIN")
                .build();

        var userClient = User.builder()
                .username("testUserClient")
                .password(passwordEncoder.encode("test@1234"))
                .authority("USER")
                .build();

//        userRepository.save(userAdmin);
//        userRepository.save(userClient);
    }
}
