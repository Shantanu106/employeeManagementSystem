package com.employeeManagement.demo.Service;

import com.employeeManagement.demo.Model.admin;
import com.employeeManagement.demo.Repository.AdminRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class adminService {
    private final AdminRepo adminRepo;

    public adminService(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }
    public boolean validateAdmin(String username, String password) {
        System.out.println("Login attempt: " + username + " / " + password);
        Optional<admin> admin = adminRepo.findByUsername(username);
        if(admin.isPresent()) {
            System.out.println("Found admin: " + admin.get().getUsername() + " / " + admin.get().getPassword());
        }
        return admin.isPresent() && admin.get().getPassword().equals(password);
    }
    // Fetch admin by username for profile
    public admin getAdminByUsername(String username) {
        return adminRepo.findByUsername(username).orElse(null);
    }

    public void saveAdmin(admin currentAdmin) {
        adminRepo.save(currentAdmin);
    }
}
