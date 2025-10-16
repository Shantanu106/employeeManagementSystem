package com.employeeManagement.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "leaves")
public class Leave {
    @Id
    private Long id;

    private Long employeeId;
    private String employeeName;
    private String department;
    private String startDate;
    private String endDate;
    private String status; // Pending, Approved, Rejected
    public  Leave(){}
}
