package com.employeeManagement.demo.Repository;

import com.employeeManagement.demo.Model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {
    List<Leave> findByStatus(String status);
}
