package com.employeeManagement.demo.Repository;

import com.employeeManagement.demo.Model.admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<admin, Long> {
    Optional<admin> findByUsername(String username);
}
