package com.employeeManagement.demo.Repository;

import com.employeeManagement.demo.Model.employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface employeeRepository extends JpaRepository<employee, Long> {

    // Find employee by email (for employee login)
    Optional<employee> findByEmail(String email);

    // Search employees by name (case-insensitive)
    List<employee> findByNameContainingIgnoreCase(String name);

    // Search by department
    List<employee> findByDepartment(String department);

    // ✅ Fixed: entity name must match class name exactly (here, 'employee')
    @Query("SELECT e FROM employee e " +
            "WHERE e.name LIKE %:keyword% " +
            "OR e.department LIKE %:keyword% " +
            "OR CAST(e.id AS string) LIKE %:keyword%")
    List<employee> searchByKeyword(String keyword);
}
