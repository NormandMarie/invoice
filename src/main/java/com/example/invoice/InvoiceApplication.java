package com.example.invoice;

import com.example.invoice.entity.User;
import com.example.invoice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InvoiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceApplication.class, args);
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional

        public void run(String... args) throws Exception {

//            User user1 = new User();
//            user1.setFirstName("Jodie");
//            user1.setLastName("Comer");
//            user1.setEmail("admin@my-invoice.fr");
//            user1.setPassword(passwordEncoder.encode("qwerty"));
//            userRepository.save(user1);
//
//            User user2 = new User();
//            user2.setFirstName("Alice");
//            user2.setLastName("Smith");
//            user2.setEmail("Lili@example.com");
//            user2.setPassword(passwordEncoder.encode("azerty"));
//            userRepository.save(user2);
        }
    }