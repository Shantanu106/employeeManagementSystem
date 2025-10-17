package com.employeeManagement.demo.Controller;

import com.employeeManagement.demo.Model.Attendance;
import com.employeeManagement.demo.Model.admin;
import com.employeeManagement.demo.Model.employee;
import com.employeeManagement.demo.Service.AttendanceService;
import com.employeeManagement.demo.Service.EmployeeService;
import com.employeeManagement.demo.Service.LeaveService;
import com.employeeManagement.demo.Service.adminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.Thymeleaf;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final adminService adminService;
    private final EmployeeService employeeService;
    private LeaveService leaveService;
    private AttendanceService attendanceService;
    public AdminController(adminService adminService ,AttendanceService attendanceService, EmployeeService employeeService,LeaveService leaveService){
        this.adminService=adminService;
        this.employeeService=employeeService;
        this.leaveService=leaveService;
        this.attendanceService=attendanceService;
    }
    @GetMapping("/login")
    public String showLogin() {
        return "adminLogin"; // adminLogin.html
    }
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        if(adminService.validateAdmin(username, password)) {
            // Store admin info in session
            session.setAttribute("loggedInAdmin", adminService.getAdminByUsername(username));
            return "redirect:/admin/adminDashboard";// go to dashboard if success
        } else {
            return "redirect:/admin/login";// reload login page if failed
        }

    }
    @GetMapping("/adminDashboard")
    public String adminDashboard() {
        return "adminDashboard"; // admin dashboard page
    }
    // AdminController.java
    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEmployees", employeeService.getAllEmployees().size());
        stats.put("onLeave", leaveService.getAllRequests().stream().filter(l -> "Approved".equals(l.getStatus())).count());
        stats.put("departments", 4); // optional - static or dynamic
        stats.put("pendingRequests", leaveService.getPendingRequests().size());
        return stats;
    }

    //add employee
    @GetMapping("/addEmployee")
    public String showAddEmployeeForm(Model model){
        model.addAttribute("employee",new employee());
        return "addEmployee";
    }
    // Process Add Employee Form
    @PostMapping("/addEmployee")
    public String addEmployee(@Valid  @ModelAttribute ("employee") employee employee,
                              BindingResult result , Model model) {
        if (result.hasErrors()){
            return "addEmployee";
        }
        employeeService.saveEmployee(employee);
        model.addAttribute("successMessage", "Employee added successfully!");
        model.addAttribute("employee", new employee());
        return "addEmployee"; // back to dashboard after save
    }
    //show all employees
    @GetMapping("/employees")
    public String viewEmployees(Model model){
        model.addAttribute("employees",employeeService.getAllEmployees());
        return "employeeList";
    }
    // Show edit form
    @GetMapping("/editEmployee/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        employee emp = employeeService.getEmployeeById(id);
        model.addAttribute("employee", emp);
        return "editEmployee"; // new HTML page for editing
    }
    // Process edit form
    @PostMapping("/editEmployee")
    public String updateEmployee(@Valid @ModelAttribute("employee") employee employee,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editEmployee";
        }
        employeeService.saveEmployee(employee);
        return "redirect:/admin/employees"; // back to list
    }
    // Delete employee
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin/employees"; // back to list
    }
    @GetMapping("/search")
    public String searchEmployees(@RequestParam("keyword") String keyword, Model model) {
        List<employee> employees = employeeService.searchEmployees(keyword);
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);
        return "employeeList"; // your HTML file name
    }
    @GetMapping("/approveLeaves")
    public String viewPendingLeaves(Model model) {
        model.addAttribute("leaves", leaveService.getPendingRequests());
        return "approveLeaves";
    }
    @GetMapping("/approveLeave/{id}")
    public String approveLeave(@PathVariable Long id) {
        leaveService.approveLeave(id);
        return "redirect:/admin/approveLeaves";
    }
    @GetMapping("/rejectLeave/{id}")
    public String rejectLeave(@PathVariable Long id) {
        leaveService.rejectLeave(id);
        return "redirect:/admin/approveLeaves";
    }
    @GetMapping
    public String dashboard() {
       return "redirect:/admin/adminDashboard";// Thymeleaf template: dashboard.html
    }

//    @GetMapping("/employees")
//    public String employees() {
//        return "employees"; // employees.html
//    }

    @GetMapping("/departments")
    public String departments() {
        return "departments"; // departments.html
    }
    @GetMapping("/attendance")
    public String viewAttendance(Model model) {
        List<employee> employees = employeeService.getAllEmployees(); // get all employees
        Map<Long, Attendance> todayAttendance = new HashMap<>();

        LocalDate today = LocalDate.now();
        for (employee emp : employees) {
            Attendance att = attendanceService.getAttendanceByEmployeeAndDate(emp.getId(), today);
            todayAttendance.put(emp.getId(), att); // null if not yet marked
        }

        model.addAttribute("employees", employees);
        model.addAttribute("todayAttendance", todayAttendance);
        return "attendance";
    }

    @PostMapping("/attendance/mark")
    public String markAttendance(@RequestParam Long employeeId,
                                 @RequestParam boolean present) {
        attendanceService.markAttendance(employeeId, present);
        return "redirect:/admin/attendance";
    }

    @GetMapping("/payroll")
    public String payroll() {
        return "payroll"; // payroll.html
    }

    @GetMapping("/reports")
    public String reports() {
        return "reports"; // reports.html
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        Object obj = session.getAttribute("loggedInAdmin");
        if(obj != null) {
            model.addAttribute("admin", obj);
            return "adminProfile";
        } else {
            return "redirect:/admin/login"; // if session expired
        }
    }
    @GetMapping("/editProfile")
    public String showEditProfile(Model model , HttpSession session){
        Object obj = session.getAttribute("loggedInAdmin");
        if(obj != null){
            model.addAttribute("admin",obj);//prefilled form
            return "editProfile";
        }else {
            return "redirect:/admin/login";//session expired
        }
    }
    @PostMapping("/editProfile")
    public String updateProfile(@ModelAttribute("Admin") admin adminFrom,HttpSession session){
        admin currentAdmin =(admin) session.getAttribute("loggedInAdmin");
        if (currentAdmin != null){
            currentAdmin.setUsername(adminFrom.getUsername());
            currentAdmin.setPassword(adminFrom.getPassword());
            adminService.saveAdmin(currentAdmin);
            session.setAttribute("loggedInAdmin",currentAdmin);
            return "redirect:/admin/profile";
        }return "redirect:/admin/login"; // if session expired
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login"; // redirect to login page
    }
}
