package com.employeeManagement.demo.Service;

import com.employeeManagement.demo.Model.Department;
import com.employeeManagement.demo.Repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepo;

    public DepartmentService(DepartmentRepository departmentRepo) {
        this.departmentRepo = departmentRepo;
    }
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }
    public void saveDepartment(Department department) {


        departmentRepo.save(department);
    }
    public void deleteDepartment(Long id) {
        departmentRepo.deleteById(id);
    }
}
