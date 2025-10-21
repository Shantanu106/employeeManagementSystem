package com.employeeManagement.demo.Service;

import com.employeeManagement.demo.Model.admin;
import com.employeeManagement.demo.Model.employee;
import com.employeeManagement.demo.Repository.employeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private employeeRepository employeeRepository;
    public EmployeeService (employeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }
    public List<employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    public int getTotalEmployees(){
        return (int) employeeRepository.count();
    }

    public void saveEmployee(employee employee) {
        employeeRepository.save(employee);
    }

    public employee getEmployeeById(Long id) {
        Optional<employee> emp = employeeRepository.findById(id);
        if (emp.isPresent()){
            return emp.get();
        }else {
            throw new RuntimeException("employee not found by id:"+id);
        }
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }else {
            throw new RuntimeException("cannot delet. rmployee is not found by this id:"+id);
        }
    }

    public List<employee> searchEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll();
        }
        return employeeRepository.searchByKeyword(keyword);
    }

    public boolean validateEmployee(String email, String phone) {
        System.out.println("Login attempt: " + email + " / " + phone);

        // Find employee by email (used as username)
        Optional<employee> emp = employeeRepository.findByEmail(email);

        if (emp.isPresent()) {
            System.out.println("Found employee: " + emp.get().getName() + " / " + emp.get().getPhone());
        }else {
            System.out.println("Employee not found for email: " + email);
        }
        boolean valid = emp.isPresent() && emp.get().getPhone().trim().equals(phone.trim());
        System.out.println("Login valid? " + valid);
//
        // Return true if employee exists and phone matches (trimmed)
        return valid;
    }

    public employee getEmployeeByEmail(String email) {
       return employeeRepository.findByEmail(email).orElse(null);

    }
}
