package com.employeeManagement.demo.Controller;

import com.employeeManagement.demo.Model.Department;
import com.employeeManagement.demo.Service.DepartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/departments-management")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    // Show all departments
    @GetMapping
    public String viewDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments"; // departments.html
    }
    @GetMapping("/api")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    // Show Add Department form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("department", new Department());
        return "addDepartment"; // addDepartment.html
    }
    // Process Add Department form
    @PostMapping("/add")
    public String addDepartment(@ModelAttribute("department") Department department) {
        // Save the department with all fields (name, manager, action)
        departmentService.saveDepartment(department);

        // Redirect back to the department list page
        return "redirect:/admin/departments-management";
    }
    // Optional: Delete department
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "redirect:/admin/departments-management";
    }

}
