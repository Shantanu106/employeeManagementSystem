package com.employeeManagement.demo.Controller;

import com.employeeManagement.demo.Model.Leave;
import com.employeeManagement.demo.Model.employee;
import com.employeeManagement.demo.Repository.LeaveRepository;
import com.employeeManagement.demo.Service.EmployeeService;
import com.employeeManagement.demo.Service.LeaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee") // All routes prefixed with /employee
public class EmployeeController {

    private final EmployeeService employeeService;
    private final LeaveRepository leaveRepository;
    private final LeaveService leaveService;

    public EmployeeController(EmployeeService employeeService, LeaveRepository leaveRepository, LeaveService leaveService) {
        this.employeeService = employeeService;
        this.leaveService=leaveService;
        this.leaveRepository=leaveRepository;
    }

    // Show login page
    @GetMapping("/login")
    public String showLogin() {
        return "employee-login"; // employee-login.html
    }

    // Process login form
    @PostMapping("/login")
    public String processLogin(@RequestParam String email,
                               @RequestParam String phone,
                               HttpSession session,
                               Model model) {

        if(employeeService.validateEmployee(email, phone)) {
            employee emp = employeeService.getEmployeeByEmail(email);

            // Store logged-in employee in session
            session.setAttribute("loggedInEmployee", emp);

            // Redirect to dashboard
            return "redirect:/employee/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or phone");
            return "redirect:/employee/login?error=true";
        }
    }

    // Employee dashboard
    @GetMapping("/dashboard")
    public String employeeDashboard(HttpSession session, Model model) {
        employee emp = (employee) session.getAttribute("loggedInEmployee");

        if(emp == null) {
            // Not logged in or session expired
            return "redirect:/employee/login";
        }

        model.addAttribute("loggedInEmployee", emp);
        return "employeeDashboard"; // employeeDashboard.html
    }

    // Employee profile
    @GetMapping("/profile")
    public String showEmployeeProfile(HttpSession session, Model model) {
        employee emp = (employee) session.getAttribute("loggedInEmployee");

        if(emp == null) {
            // Session expired
            return "redirect:/employee/login";
        }

        model.addAttribute("loggedInEmployee", emp);
        return "employeeProfile"; // employeeProfile.html
    }
    @GetMapping("/update")
    public String updateEmployeeProfile(HttpSession session,Model model){
        Object obj = session.getAttribute("loggedInEmployee");
        if(obj != null){
            model.addAttribute("employee",obj);//prefilled form
            return "editEmployeeProfile";
        }else {
            return "redirect:/employee/login";//session expired
        }
    }
    @PostMapping("/updateProfile")
    public String updateEmployeeProfile(@ModelAttribute("employee") employee updatedEmployee,
                                        HttpSession session,
                                        Model model) {
        // Get the logged-in employee from session
        employee loggedInEmp = (employee) session.getAttribute("loggedInEmployee");

        if (loggedInEmp == null) {
            // Session expired
            return "redirect:/employee/login";
        }

        // Only allow updating fields employees can edit
        loggedInEmp.setName(updatedEmployee.getName());
        loggedInEmp.setEmail(updatedEmployee.getEmail());
        loggedInEmp.setPhone(updatedEmployee.getPhone());
        // department, id, salary remain unchanged

        // Save changes to database
        employeeService.saveEmployee(loggedInEmp);

        // Update session
        session.setAttribute("loggedInEmployee", loggedInEmp);

        // Redirect back to dashboard or profile
        return "redirect:/employee/dashboard";
    }
    @GetMapping("/leave")
    public String showLeaveFrom(Model model){
        model.addAttribute("leave",new Leave());
        return "leaveForm";
    }
    // Handle leave submission
    @PostMapping("/apply")
    public String applyLeave(@ModelAttribute Leave leave, HttpSession session) {
        employee emp = (employee) session.getAttribute("loggedInEmployee");
        if (emp == null) {
            return "redirect:/employee/login";
        }

        leave.setEmployeeId(emp.getId());
        leave.setEmployeeName(emp.getName());
        leave.setDepartment(emp.getDepartment());
        leave.setStatus("Pending");

        leaveService.applyLeave(leave);
        return "redirect:/employee/dashboard";

    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate the session
        session.invalidate();

        // Redirect to login page
        return "redirect:/employee/login";
    }




}
