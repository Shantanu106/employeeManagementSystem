package com.employeeManagement.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class authController {
    @GetMapping("/")
    public String selectRolePage(){
        return "roleSelection";
    }
    @PostMapping("/select")
    @ResponseBody
    public String selectRole(@RequestParam String role){
        if (role.equalsIgnoreCase("ADMIN")){
            return "redirect:/adminDashboard";
        }else if(role.equalsIgnoreCase("EMPLOYEE")) {
            return "redirect:/employeeDashboard";
        }
        return "Invalid Role";
    }
    // Dashboard pages mapping
    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminLogin"; // resolves to adminDashboard.html (Thymeleaf or static)
    }

    @GetMapping("/employeeDashboard")
    public String employeeDashboard() {
        return "employee-login"; // resolves to employeeDashboard.html
    }
    }


