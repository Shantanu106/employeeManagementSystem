package com.employeeManagement.demo.Service;

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

}
