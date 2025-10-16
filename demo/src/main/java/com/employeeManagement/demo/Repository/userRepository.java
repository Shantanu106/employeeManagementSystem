package com.employeeManagement.demo.Repository;

import com.employeeManagement.demo.Model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface userRepository extends JpaRepository<user, Long> {
    // Find user by email (used for login)
    Optional<user> findByEmail(String Email);
    // Optional: Find user by email AND password (simpler for now)
    Optional<user>findByEmailAndPassword(String email,String password);
    // Optional: check if email exists
    boolean existsByEmail(String email);
}
